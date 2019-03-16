(ns {{main-ns}}.pages.code
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.state :refer [state update-state!]]
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

(defn on-entry []
  (js/console.log "Entering code page"))

(defn on-exit []
  (js/console.log "Exiting code page"))

(defn- set-language! [lang]
  (update-state! #(assoc-in @state [:code :language] lang)))

(defn- get-language []
  (get-in @state [:code :language]))

(defn view []
  [:div 
   [:h3 "Write some code!"]
   [:p "What shall we write stuff in today?"]
   [:div 
    [ant/select {:default-value (get-language) :on-change set-language! :style {:width "120px"}}
     [ant/select-option {:value "Clojure"} "Clojure"]
     [ant/select-option {:value "Scala"}   "Scala"]
     [ant/select-option {:value "Kotlin"}  "Kotlin"]]]])
