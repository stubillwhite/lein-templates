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
               [".gitignore"                     (render ".gitignore"        config)]
               ["dev.cljs.edn"                   (render "dev.cljs.edn"      config)]
               ["figwheel-main.edn"              (render "figwheel-main.edn" config)]
               ["project.clj"                    (render "project.clj"       config)]
               ["README.md"                      (render "README.md"         config)]
               ["resources/public/css/style.css" (render "style.css"         config)]
               ["resources/public/index.html"    (render "index.html"        config)]
               ["resources/public/test.html"     (render "test.html"         config)]
               ["src/{{path}}/state.cljs"        (render "state.cljs"         config)]
               ["src/{{path}}/core.cljs"         (render "core.cljs"         config)]
               ["test/{{path}}/core_test.cljs"   (render "core_test.cljs"    config)]
               ["test/{{path}}/test_runner.cljs" (render "test_runner.cljs"  config)]
               ["test.cljs.edn"                  (render "test.cljs.edn"     config)]
               ))))
