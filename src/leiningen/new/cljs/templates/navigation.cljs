(ns {{main-ns}}.navigation
  (:require [accountant.core :as accountant]
            [antizer.reagent :as ant]
            [{{main-ns}}.pages.code :as cookies]
            [{{main-ns}}.pages.coffee :as counter]
            [{{main-ns}}.state :refer [state update-state!]]
            [{{main-ns}}.utils :refer [index-by]]
            [goog.events :as events]
            [reagent.core :as reagent]
            [secretary.core :as secretary])
  (:import goog.History
           goog.history.EventType))

;; Application pages

;; TODO: Merge these

(def pages
  [{:id          "coffee"
    :path        "/#coffee"
    :icon        "coffee"
    :label       "Drink coffee"
    :view-fn     counter/view
    :on-entry-fn counter/on-entry
    :on-exit-fn  counter/on-exit}

   {:id          "code"
    :path        "/#code"
    :icon        "desktop"
    :label       "Write code"
    :view-fn     cookies/view
    :on-entry-fn cookies/on-entry
    :on-exit-fn  cookies/on-exit}])

;; Routing

(defn set-page! [page]
  (println "Selecting page" page)
  (update-state! #(assoc-in % [:router :page] page)))

;; TODO: These should all use navigate to
(defn configure-routes! []
  (secretary/set-config! :prefix "#")

  (secretary/defroute home-path "/" []
    (set-page! "code"))

  (secretary/defroute coffee-path "/coffee" []
    (set-page! "coffee"))

  (secretary/defroute code-path "/code" []
    (set-page! "code"))

  (secretary/defroute code-language-path "/code/:id" [id]
    (set-page! "code")))

(defn- hook-browser-navigation!
  "Add hooks to browser navigation. This must be called after routes are
  defined, and must not be called on a reload to avoid breaking
  Figwheel's hot-reload functionality."
  []
  (doto (History.)
    (events/listen EventType.NAVIGATE #(secretary/dispatch! (.-token %)))
    (.setEnabled true)))

(defn- configure-accountant! []
  (accountant/configure-navigation! {:nav-handler       (fn [path] #(secretary/dispatch! path))
                                     :path-exists?      (fn [path] #(secretary/locate-route path))
                                     :reload-same-path? true}))

(defn configure-navigation!
  "Configure browser navigation appropriately depending on whether this
  is a new load or a hot-reload of the page."
  [is-reload]
  (configure-routes!)
  (when (not is-reload) (hook-browser-navigation!))
  (configure-accountant!))

;; Navigation

(def pages-by-id (index-by :id pages))

(defn- no-action [])

;; TODO: Pass in pages and search, remove pages-by-id
(defn navigate-to [target-id]
  (let [source-id (get-in @state [:router :page])
        source    (get pages-by-id source-id)
        target    (get pages-by-id target-id)
        exit-fn   (get source :on-exit-fn no-action)
        entry-fn  (get target :on-entry-fn no-action)]
    (exit-fn)
    (entry-fn)
    (update-state! #(assoc-in % [:router :page] target-id))
    (accountant/navigate! (:path target))))

(defn- navigate-to-clicked-page [event]
  (let [page (:key (js->clj event :keywordize-keys true))] 
    (navigate-to page)))

(defn- not-found-view [page-id]
  [:div
   [:h3 "Page not found"]
   [:p (str "Unable to locate page '" page-id "'")]])

(defn- current-page-view []
  (let [page-id   (get-in @state [:router :page])
        not-found (partial not-found-view page-id)
        view-fn   (get-in pages-by-id [page-id :view-fn] not-found)]
    (view-fn)))

(defn- menu-item [id icon label]
  [ant/menu-item {:key id} (reagent/as-element [:span [ant/icon {:type icon}] label])])

(defn navigation-view [] 
  [ant/layout
   [ant/affix
    [ant/menu {:mode "horizontal" :theme "dark" :selected-keys [(:page @state)]
               :on-click navigate-to-clicked-page}
     (for [{:keys [id icon label]} pages] (menu-item id icon label))]]
   [ant/layout-content {:style {:padding "10px"}}
    [:div
     (current-page-view)]]])
