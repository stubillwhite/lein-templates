(ns {{main-ns}}.app-state
  (:require
   [reagent.core :as reagent :refer [atom]]))

;; Application state
(defonce app-state (atom {:debug true
                          :count 0}))

(defn update-app-state! [f]
  (swap! app-state f)
  (when (:debug @app-state)
    (print @app-state)))

