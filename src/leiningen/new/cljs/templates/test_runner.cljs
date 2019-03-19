(ns {{main-ns}}.test-runner
  (:require {{main-ns}}.pages.coffee-test
            [figwheel.main.testing :refer [run-tests-async]]))

(defn -main [& args]
  (run-tests-async 5000))
