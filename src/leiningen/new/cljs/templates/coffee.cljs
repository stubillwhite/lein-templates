(ns {{main-ns}}.pages.coffee
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.state :refer [state update-state!]]
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

(defn increment-count []
  (update-state! #(update-in % [:coffee :count] inc)))

(defn decrement-count []
  (update-state! #(update-in % [:coffee :count] dec)))

(defn on-entry []
  (js/console.log "Entering coffee page"))

(defn on-exit []
  (js/console.log "Exiting coffee page"))

(defn view []
  [:div
   [:h3 "Drink some coffee!"]
   [:p "How many cups?"]
   [ant/form {:layout "inline"}
    [ant/form-item {}
     [ant/button {:type "primary" :on-click decrement-count} "Less"]]
    [ant/form-item {}
     [:h1 {} (get-in @state [:coffee :count])]]
    [ant/form-item {}
     [ant/button {:type "primary" :on-click increment-count} "More"]]]])