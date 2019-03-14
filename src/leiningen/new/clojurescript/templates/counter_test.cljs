(ns {{main-ns}}.pages.counter-test
    (:require
     [cljs.test :refer-macros [deftest is testing]]
     [{{main-ns}}.state :refer [state]]
     [reagent.core :as reagent :refer [atom]]
     [{{main-ns}}.pages.counter :refer [decrement-count increment-count]]))

(deftest increment-then-increments-count
  (with-redefs [state (atom {:counter {:count 0}})]
    (do (increment-count))
    (is (= 1 (get-in @state [:counter :count])))))

(deftest decrement-then-decrements-count
  (with-redefs [state (atom {:counter {:count 0}})]
    (do (decrement-count))
    (is (= -1 (get-in @state [:counter :count])))))

