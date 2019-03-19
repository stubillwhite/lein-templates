(ns {{main-ns}}.integration.server
  (:require [figwheel-sidecar.repl-api :as ra]))

(defn- start-server []
  (ra/start-figwheel! {
                       :figwheel-options {:server-port 9500
                                          :server-ip "localhost"}
                       :build-ids ["dev"]
                       :all-builds 
                       [{:id "dev"
                         :figwheel true
                         :source-paths ["src"]
                         :compiler {:main "repler.core"
                                    :asset-path "js/out"
                                    :output-to "resources/public/js/repler.js"
                                    :output-dir "resources/public/js/out" }}]}))

(defn stop-server []
  (ra/stop-figwheel!))

(defn with-server [f]
  (start-server)
  (f)
  (stop-server))

;; (start-server)
