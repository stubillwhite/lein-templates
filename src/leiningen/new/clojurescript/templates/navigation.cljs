(ns {{main-ns}}.navigation
  (:require [antizer.reagent :as ant]
            [{{main-ns}}.pages.cookies :as cookies]
            [{{main-ns}}.pages.counter :as counter]
            [{{main-ns}}.state :refer [state update-state!]]
            [reagent.core :as reagent]))

;; The pages in the application
(def pages
  [{:id          "counter"
    :path        "/counter"
    :icon        "coffee"
    :label       "Drink coffee"
    :view-fn     counter/counter-view
    :on-entry-fn #(println "Entering the counter page")
    :on-exit-fn  #(println "Leaving the counter page")}

   {:id          "cookies"
    :path        "/cookies"
    :icon        "desktop"
    :label       "Write code"
    :view-fn     cookies/cookies-view
    :on-entry-fn #(print "Entering the cookies page")
    :on-exit-fn  #(println "Leaving the cookies page")}])

;; TODO: Move to utils
(defn index-by
  "Returns xs in the form of a map, indexed by (f x)."
  [f xs]
  (into {} (for [x xs] [(f x) x])))

(def pages-by-id (index-by :id pages))

(defn- no-action [])

(defn navigate-to [target-id]
  (let [source-id (get-in @state [:router :page])
        source    (get pages-by-id source-id)
        target    (get pages-by-id target-id)
        exit-fn   (get source :on-exit-fn no-action)
        entry-fn  (get target :on-entry-fn no-action)]
    (exit-fn)
    (entry-fn)
    (update-state! #(assoc-in % [:router :page] target-id))))

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
