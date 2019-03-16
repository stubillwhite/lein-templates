(ns {{main-ns}}.navigation
  (:require [accountant.core :as accountant]
            [antizer.reagent :as ant]
            [{{main-ns}}.pages.cookies :as cookies]
            [{{main-ns}}.pages.counter :as counter]
            [{{main-ns}}.state :refer [state update-state!]]
            [{{main-ns}}.utils :refer [index-by]]
            [goog.events :as events]
            [reagent.core :as reagent]
            [secretary.core :as secretary :refer [dispatch!] :refer-macros [defroute]])
  (:import goog.History
           goog.history.EventType))

;; Application pages

;; TODO: Merge these

(def pages
  [{:id          "counter"
    :path        "/#counter"
    :icon        "coffee"
    :label       "Drink coffee"
    :view-fn     counter/view
    :on-entry-fn counter/on-entry
    :on-exit-fn  counter/on-exit}

   {:id          "cookies"
    :path        "/#cookies"
    :icon        "desktop"
    :label       "Write code"
    :view-fn     cookies/view
    :on-entry-fn cookies/on-entry
    :on-exit-fn  cookies/on-exit}])

;; Routing

(defn set-page! [page]
  (println "Selecting page" page)
  (update-state! #(assoc-in % [:router :page] page)))

(defn configure-routes! []
  (secretary/set-config! :prefix "#")

  (secretary/defroute home-path "/" []
    (set-page! "counter"))

  (secretary/defroute counter-path "/counter" []
    (set-page! "counter"))

  (secretary/defroute cookies-path "/cookies" []
    (set-page! "cookies"))

  (secretary/defroute cookie-path "/cookies/:id" [id]
    (set-page! "cookies")))

(defn hook-browser-navigation!
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

(defn configure-navigation! [is-reload]
  (configure-routes!)
  (when (not is-reload) (hook-browser-navigation!))
  (configure-accountant!))

(defn open-current-page! []
  (js/console.log "opening furrent page" (get-in @state [:router :page]))
  (accountant/dispatch-current!))

;; TODO Change println to js/console.log

;; Navigation

(def pages-by-id (index-by :id pages))

(defn- no-action [])

;; TODO: Pass in pages and search, remove pages-by-id
(defn navigate-to [target-id params]
  (let [source-id (get-in @state [:router :page])
        source    (get pages-by-id source-id)
        target    (get pages-by-id target-id)
        exit-fn   (get source :on-exit-fn no-action)
        entry-fn  (get target :on-entry-fn no-action)]
    (exit-fn)
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

(defn- menu-item-view [id icon label]
  [ant/menu-item {:key id} (reagent/as-element [:span [ant/icon {:type icon}] label])])

(defn navigation-view [] 
  [ant/layout
   [ant/affix
    [ant/menu {:mode "horizontal" :theme "dark" :selected-keys [(:page @state)]
               :on-click navigate-to-clicked-page}
     (for [{:keys [id icon label]} pages] (menu-item-view id icon label))]]
   [ant/layout-content {:style {:padding "10px"}}
    [:div
     (current-page-view)]]])
