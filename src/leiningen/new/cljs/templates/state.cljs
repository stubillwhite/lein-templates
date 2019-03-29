(ns {{main-ns}}.state
  (:require
   [reagent.core :as reagent]))

;; Application state
(defonce state (reagent/atom {:router {:page "coffee"}
                              :coffee {:count 0}}))

(defn update-state! [f & args]
  (apply swap! state f args)
  (js/console.log @state))

