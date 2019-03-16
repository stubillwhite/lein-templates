(ns {{main-ns}}.state
  (:require
   [reagent.core :as reagent :refer [atom]]))

;; Application state
(defonce state (atom {:router {:page "coffee"}
                      :coffee {:count 0}
                      :debug true}))

(defn update-state! [f]
  (swap! state f)
  (when (:debug @state)
    (print @state)))

