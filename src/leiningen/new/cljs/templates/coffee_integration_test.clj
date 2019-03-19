(ns {{main-ns}}.integration.coffee-integration-test
  (:require [clojure.test :refer :all]
            [etaoin.api :refer :all]
            [{{main-ns}}.integration.server :as server]))

(def ^:dynamic *driver*)

(defn fixture-driver [f]
  (with-chrome {} driver
    (binding [*driver* driver]
      (f))))

;; Start and stop the driver for each test
(use-fixtures :each server/with-server fixture-driver)

;; Run the server for each test
;; (use-fixtures :each server/with-server)

(deftest ^:integration
  drinking-more-coffee-increments-drink-count
  (doto *driver*
    (go "http://localhost:9500")
    (wait-visible {:id "app"})
    (click :inc)
    (click :inc))
  (is (has-text? *driver* {:tag :h1 :id "count"} "2")))

(deftest ^:integration
  drinking-less-coffee-decrements-drink-count
  (doto *driver*
    (go "http://localhost:9500")
    (wait-visible {:id "app"})
    (click :dec)
    (click :dec))
  (is (has-text? *driver* {:tag :h1 :id "count"} "-2")))
