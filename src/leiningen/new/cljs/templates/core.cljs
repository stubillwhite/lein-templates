(ns ^:figwheel-hooks {{main-ns}}.core
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.state :refer [state update-state!]]
   [{{main-ns}}.navigation :as navigation]
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

;; Application view
;; --------------------------------------------------

(defn- app-view
  "The root view of the application."
  []
  [navigation/view {}])

;; Entry point
;; --------------------------------------------------

(defn- get-app-element []
  (gdom/getElement "app"))

(defn- mount [el]
  (reagent/render-component [app-view] el))

(defn mount-app-element
  "Conditionally load the application if an 'app' element is present,
  configuring hooks appropriately depending on whether this is a
  hot-reload or a full reload."
  [is-reload]
  (when-let [el (get-app-element)]
    (do
      (navigation/configure-navigation! is-reload)
      (mount el))))

(defn ^:after-load on-reload
  "Hook called on a hot-reload."
  []
  (mount-app-element true)
  ;; Optionally touch your state to force rerendering depending on your application
  ;; (swap! state update-in [:__figwheel_counter] inc)
)

(mount-app-element false)
