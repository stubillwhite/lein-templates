(ns {{main-ns}}.pages.code
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.state :refer [state update-state!]]
   [goog.dom :as gdom]
   [secretary.core :as secretary]
   [accountant.core :as accountant]
   [reagent.core :as reagent :refer [atom]]))

(defn on-entry []
  (js/console.log "on-entry hook fired for code page"))

(defn on-exit []
  (js/console.log "on-exit hook fired for code page"))

;; TODO: Needs refactoring into navigation
(defn- set-language! [language]
  (accountant/navigate! (str "#code/" language)))

;; View
;; --------------------------------------------------

(defn view [params]
  [:div 
   [:h3 "Write some code!"]
   [:p {:style {:font-style "italic"}} "This page stores state in the URL rather than in the application state atom. This isn't something we should do often, but it can occasionlly be useful because it means URLs can be copied-and-pasted to others to reproduce the state of this page."]
   [:p "What shall we write stuff in today?"]
   [:div
    [ant/select {:default-value (get-in @state [:code :language]) :on-change set-language! :style {:width "120px"}}
     [ant/select-option {:value "Clojure"} "Clojure"]
     [ant/select-option {:value "Scala"}   "Scala"]
     [ant/select-option {:value "Kotlin"}  "Kotlin"]]]])
