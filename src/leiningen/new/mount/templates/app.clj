(ns {{main-ns}}.app
  (:gen-class)
  (:require [clojure.tools.nrepl.server :refer [start-server stop-server]]
            [mount.core :as mount :refer [defstate]]
            [{{main-ns}}.config :refer [config]]))

(defn- start-nrepl [{:keys [host port]}]
  (start-server :bind host :port port))

(defn- stop-nrepl [x]
  (stop-server x))

(defstate nrepl
  :start (start-nrepl (:nrepl config))
  :stop  (stop-nrepl nrepl))

(defn -main [& args]
  (mount/start))
