(ns ^:figwheel-hooks {{main-ns}}.core
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.state :refer [state update-state!]]
   [{{main-ns}}.navigation :as navigation]
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

;; Main application view

(defn- app-view
  "The root view of the application."
  []
  [navigation/navigation-view {}])

(defn- get-app-element []
  (gdom/getElement "app"))

(defn- mount [el]
  (reagent/render-component [app-view] el))

(defn mount-app-element [is-reload]
  (when-let [el (get-app-element)]
    (do
      (navigation/configure-navigation! is-reload)
      (mount el))))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element false)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element true)
  ;; optionally touch your state to force rerendering depending on
  ;; your application
  ;; (swap! state update-in [:__figwheel_counter] inc)
)
