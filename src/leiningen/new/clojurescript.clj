(ns leiningen.new.clojurescript
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize-ns project-name year]]
            [clojure.string :as string]))

(def render (renderer "clojurescript/templates"))

(defn common-file [fnam]
  (str "../../common/templates/" fnam))

(defn clojurescript
  "Base starter project for ClojureScript."
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
               ["{{path}}/.gitignore"                     (render ".gitignore"        config)]
               ["{{path}}/dev.cljs.edn"                   (render "dev.cljs.edn"      config)]
               ["{{path}}/figwheel-main.edn"              (render "figwheel-main.edn" config)]
               ["{{path}}/project.clj"                    (render "project.clj"       config)]
               ["{{path}}/README.md"                      (render "README.md"         config)]
               ["{{path}}/resources/public/css/style.css" (render "style.css"         config)]
               ["{{path}}/resources/public/index.html"    (render "index.html"        config)]
               ["{{path}}/resources/public/test.html"     (render "test.html"         config)]
               ["{{path}}/src/{{path}}/core.cljs"         (render "core.cljs"         config)]
               ["{{path}}/test/{{path}}/core_test.cljs"   (render "core_test.cljs"    config)]
               ["{{path}}/test/{{path}}/test_runner.cljs" (render "test_runner.cljs"  config)]
               ["{{path}}/test.cljs.edn"                  (render "test.cljs.edn"     config)]
               ))))
