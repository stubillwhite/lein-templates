(ns {{main-ns}}.pages.cookies
  (:require
   [antizer.reagent :as ant]
   [{{main-ns}}.state :refer [state update-state!]]
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]))

(defn cookies-view []
  [:h1 "Coooookies"])
