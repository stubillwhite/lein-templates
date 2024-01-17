(defproject {{full-name}} "0.1.0-SNAPSHOT"

  :description "TODO"

  :url "TODO"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :repl-options {:port 4555}

  :plugins []
  
  :dependencies [[org.clojure/tools.nrepl "0.2.13"]
                 [org.clojure/clojure "1.11.1"]
                 [com.taoensso/timbre "6.3.1"]
                 [org.clojure/tools.trace "0.7.11"]
                 [com.rpl/specter "1.1.4"]]
  
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]]
                   :source-paths ["dev"]}})
