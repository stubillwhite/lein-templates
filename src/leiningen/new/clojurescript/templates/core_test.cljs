(ns {{main-ns}}.core-test
    (:require
     [cljs.test :refer-macros [deftest is testing]]
     [{{main-ns}}.state :refer [state]]
     [reagent.core :as reagent :refer [atom]]
     [{{main-ns}}.core :refer [decrement-count increment-count]]))

(deftest increment-then-increments-count
  (with-redefs [state (atom {:count 0})]
    (do (increment-count))
    (is (= 1 (:count @state)))))

(deftest decrement-then-decrements-count
  (with-redefs [state (atom {:count 0})]
    (do (decrement-count))
    (is (= -1 (:count @state)))))

