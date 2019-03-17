(ns {{main-ns}}.navigation
  (:require [accountant.core :as accountant]
            [antizer.reagent :as ant]
            [{{main-ns}}.pages.code :as code]
            [{{main-ns}}.pages.coffee :as coffee]
            [{{main-ns}}.state :refer [state update-state!]]
            [{{main-ns}}.utils :refer [index-by]]
            [goog.events :as events]
            [reagent.core :as reagent]
            [secretary.core :as secretary])
  (:import goog.History
           goog.history.EventType))

(declare set-page!)

;; Routing configuration
;; --------------------------------------------------

;; Pages in the application in the order they should appear in the navigation menu
(def pages
  [{:id          "coffee"
    :icon        "coffee"
    :label       "Drink coffee"
    :view-fn     coffee/view
    :on-entry-fn coffee/on-entry
    :on-exit-fn  coffee/on-exit}

   {:id          "code"
    :icon        "desktop"
    :label       "Write code"
    :view-fn     code/view
    :on-entry-fn code/on-entry
    :on-exit-fn  code/on-exit}])

;; Routing handlers for when the application jumps directly to a URL.
(defn configure-routes! []
  (secretary/set-config! :prefix "#")

  (secretary/defroute home-path "/" []
    (set-page! "coffee" {}))

  (secretary/defroute coffee-path "/coffee" []
    (set-page! "coffee" {}))

  (secretary/defroute code-path "/code" []
    (set-page! "code" {}))

  (secretary/defroute code-language-path "/code/:language" [language]
    (set-page! "code" {:language language})))

;; Infrastructure
;; --------------------------------------------------

(def pages-by-id (index-by :id pages))

(defn- set-page! [page params]
  (let [{:keys [on-entry-fn]} (pages-by-id page)]
    (js/console.log "secretary: Entering page" page "with params" params)
    (update-state! #(-> %
                      (assoc-in [:router :page] page)
                      (assoc-in [:router :params] params)))
    (on-entry-fn)))

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

(defn navigate-to
  "Navigate to the target page and provide it with the specified params."
  [target-id params]
  (let [source-id (get-in @state [:router :page])
        source    (get pages-by-id source-id)
        target    (get pages-by-id target-id)]
    (when-let [exit-fn (:on-exit-fn source)] (exit-fn))
    (when-let [entry-fn (:on-entry-fn target)] (entry-fn))
    (update-state! #(-> %
                        (assoc-in [:router :page] target-id)
                        (assoc-in [:router :params] params)))
    (accountant/navigate! (str "/#" target-id))))

;; View
;; --------------------------------------------------

(defn- navigate-to-clicked-page [event]
  (let [page (:key (js->clj event :keywordize-keys true))] 
    (navigate-to page {})))

(defn- not-found-view [page-id params]
  [:div
   [:h3 "Page not found"]
   [:p (str "Unable to locate page '" page-id "'")]])

(defn- current-page-view []
  (let [{:keys [page params]} (get-in @state [:router])
        not-found (partial not-found-view page)
        view-fn   (get-in pages-by-id [page :view-fn] not-found)]
    (view-fn params)))

(defn- menu-item [id icon label]
  [ant/menu-item {:key id} (reagent/as-element [:span [ant/icon {:type icon}] label])])

(defn view [] 
  [ant/layout
   [ant/affix
    [ant/menu {:mode "horizontal" :theme "dark" :selected-keys [(:page @state)]
               :on-click navigate-to-clicked-page}
     (for [{:keys [id icon label]} pages] (menu-item id icon label))]]
   [ant/layout-content {:style {:padding "10px"}}
    [:div
     (current-page-view)]]])
