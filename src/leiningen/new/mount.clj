(ns leiningen.new.mount
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize-ns project-name year]]
            [clojure.string :as string]))

(def render (renderer "mount/templates"))

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
               [".gitignore"                    (render ".gitignore"     config)]
               [".projectile"                   (render ".projectile"    config)]
               ["README.md"                     (render "README.md"      config)]
               ["epl-v10.html"                  (render "epl-v10.html"   config)]
               ["project.clj"                   (render "project.clj"    config)]
               ["resources/config.edn"          (render "config.edn"     config)]
               ["src/{{path}}/app.clj"          (render "app.clj"        config)]
               ["src/{{path}}/config.clj"       (render "config.clj"     config)]
               ["src/{{path}}/core.clj"         (render "core.clj"       config)]
               ["src/{{path}}/utils.clj"        (render "utils.clj"      config)]
               ["test/{{path}}/core_test.clj"   (render "core_test.clj"  config)]
               ["dev/user.clj"                  (render "user.clj"       config)]))))
