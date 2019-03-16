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

(def pages
  [{:id          "counter"
    :path        "/#counter"
    :icon        "coffee"
    :label       "Drink coffee"
    :view-fn     counter/counter-view
    :on-entry-fn #(println "Entering the counter page")
    :on-exit-fn  #(println "Leaving the counter page")}

   {:id          "cookies"
    :path        "/#cookies"
    :icon        "desktop"
    :label       "Write code"
    :view-fn     cookies/cookies-view
    :on-entry-fn #(print "Entering the cookies page")
    :on-exit-fn  #(println "Leaving the cookies page")}])

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

(defn- hook-browser-navigation!
  "Add hooks to browser navigation. This must be called after routes are defined."
  []
  (doto (History.)
    (events/listen EventType.NAVIGATE #(secretary/dispatch! (.-token %)))
    (.setEnabled true)))

(defn- configure-accountant! []
  (accountant/configure-navigation! {:nav-handler       (fn [path] #(secretary/dispatch! path))
                                     :path-exists?      (fn [path] #(secretary/locate-route path))
                                     :reload-same-path? true}))

(defn configure-navigation! []
  (configure-routes!)
  (hook-browser-navigation!)
  (configure-accountant!))

(defn open-current-page! []
  (accountant/dispatch-current!))

;; TODO Change println to js/console.log

;; Navigation

(def pages-by-id (index-by :id pages))

(defn- no-action [])

(defn navigate-to [target-id]
  (let [source-id (get-in @state [:router :page])
        source    (get pages-by-id source-id)
        target    (get pages-by-id target-id)
        exit-fn   (get source :on-exit-fn no-action)
        entry-fn  (get target :on-entry-fn no-action)
        target-path (get target :path)]
    (exit-fn)
    (entry-fn)
    (println "Navigating to" target-path)
    (println "sec path" (secretary/locate-route target-path))
    (update-state! #(assoc-in % [:router :page] target-id))
    (accountant/navigate! target-path)))

(defn- navigate-to-clicked-page [event]
  (let [page (:key (js->clj event :keywordize-keys true))] 
    (navigate-to page)))

(defn- current-page-view []
  (let [page-id (get-in @state [:router :page])
        view-fn (get-in pages-by-id [page-id :view-fn])]
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
