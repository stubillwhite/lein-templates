(ns ^:figwheel-hooks {{main-ns}}.core
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.app-state :refer [app-state update-app-state!]]
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

(defn increment-count []
  (update-app-state! #(update-in % [:count] inc)))

(defn decrement-count []
  (update-app-state! #(update-in % [:count] dec)))

;; TODO: Think about navigation

(defn main-component []
  [ant/form {:layout "inline"}
   [ant/form-item {}
    [ant/button {:type "primary" :on-click decrement-count} "Decrement"]]
   [ant/form-item {}
    [:h1 {} (:count @app-state)]]
   [ant/form-item {}
    [ant/button {:type "primary" :on-click increment-count} "Increment"]]])

;; Infrastructure

(defn get-app-element []
  (gdom/getElement "app"))

(defn mount [el]
  (reagent/render-component [main-component] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
