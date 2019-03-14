(ns {{main-ns}}.state
  (:require
   [reagent.core :as reagent :refer [atom]]))

;; Application state
(defonce state (atom {:debug true
                      :count 0}))

(defn update-state! [f]
  (swap! state f)
  (when (:debug @state)
    (print @state)))

