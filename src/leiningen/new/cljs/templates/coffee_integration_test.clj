(ns {{main-ns}}.integration.coffee-integration-test
  (:require [clojure.test :refer :all]
            [etaoin.api :refer :all]
            [{{main-ns}}.integration.server :as server]))

(def ^:dynamic *driver*)

(defn fixture-driver [f]
  (with-chrome {} driver
    (binding [*driver* driver]
      (f))))

;; Run the server for all tests in the namespace
(use-fixtures :once server/with-server)

;; Start and stop the driver for each test
(use-fixtures :each fixture-driver)

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

(defn- get-drop-down-value [driver id]
  (get-element-attr driver [{:tag :div :id id}
                            {:class "ant-select-selection-selected-value"}] :title))

(deftest ^:integration
  url-sets-page-state
  (doto *driver*
    (go "http://localhost:9500/#code/Clojure")
    (wait-visible {:id "app"}))
  (is (= (get-drop-down-value *driver* "lang-select") "Clojure")))

;; (deftest ^:integration
;;   set-page-state-sets-url
;;   (doto *driver*
;;     (go "http://localhost:9500/#code/")
;;     (wait-visible {:id "app"})
;;     (fill {:id "lang-select"} "Kotlin"))
;;   (is (= (get-drop-down-value *driver* "lang-select") "Clojure")))

;; (defn- set-drop-down-value [driver id value]
;;   (click driver [{:tag :div :id id}
;;                  {:class "ant-select-selection__rendered"}])
;;   (click driver [{:tag :div :id id}
;;                  {:class "ant-select-dropdown-menu-item"}
;;                  {:text value}]))
