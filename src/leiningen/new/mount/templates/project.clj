(defproject {{full-name}} "0.1.0-SNAPSHOT"

  :description "TODO"

  :url "TODO"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :repl-options {:port 4555}

  :plugins []
  
  :dependencies [[org.clojure/tools.nrepl "0.2.12"]
                 [org.clojure/clojure "1.9.0-alpha11"]
                 [com.taoensso/timbre "4.7.4"]
                 [org.clojure/tools.trace "0.7.9"]
                 [com.rpl/specter "0.12.0"]
                 [mount "0.1.11-SNAPSHOT"]]
  
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]]
                   :source-paths ["dev"]}})
