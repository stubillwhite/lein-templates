(ns leiningen.new.mount
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize-ns project-name year]]
            [clojure.string :as string]))

(def render (renderer "mount/templates"))

(defn common-file [fnam]
  (str "../../common/templates/" fnam))

(defn mount
  "Project using mount for component management."
  [name]
  (let [[groupId artifactId] (string/split name #"/" 2)
        artifactId           (or artifactId groupId)]
    (println "Generating a project in directory" artifactId)
    (let [config {:full-name  name
                  :groupId    groupId
                  :artifactId artifactId
                  :name       artifactId
                  :year       (year)
                  :main-ns    (sanitize-ns name)
                  :path       (name-to-path name)}]
      (->files config
               [".gitignore"                    (render (common-file ".gitignore")     config)]
               [".projectile"                   (render (common-file ".projectile")    config)]
               ["README.md"                     (render (common-file "README.md")      config)]
               ["epl-v10.html"                  (render (common-file "epl-v10.html")   config)]
               ["project.clj"                   (render "project.clj"                  config)]
               ["resources/config.edn"          (render "config.edn"                   config)]
               ["src/{{path}}/app.clj"          (render "app.clj"                      config)]
               ["src/{{path}}/config.clj"       (render "config.clj"                   config)]
               ["src/{{path}}/core.clj"         (render "core.clj"                     config)]
               ["src/{{path}}/utils.clj"        (render (common-file "utils.clj")      config)]
               ["test/{{path}}/core_test.clj"   (render "core_test.clj"                config)]
               ["dev/user.clj"                  (render "user.clj"                     config)]))))
