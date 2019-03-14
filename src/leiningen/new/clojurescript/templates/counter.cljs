(ns {{main-ns}}.pages.counter
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.state :refer [state update-state!]]
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

(defn increment-count []
  (update-state! #(update-in % [:counter :count] inc)))

(defn decrement-count []
  (update-state! #(update-in % [:counter :count] dec)))

(defn counter-view []
  [ant/form {:layout "inline"}
   [ant/form-item {}
    [ant/button {:type "primary" :on-click decrement-count} "Drink less"]]
   [ant/form-item {}
    [:h1 {} (get-in @state [:counter :count])]]
   [ant/form-item {}
    [ant/button {:type "primary" :on-click increment-count} "Drink more"]]])
