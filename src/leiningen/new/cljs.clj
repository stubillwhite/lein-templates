(ns leiningen.new.cljs
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize-ns project-name year]]
            [clojure.string :as string]))

(def render (renderer "cljs/templates"))

(defn common-file [fnam]
  (str "../../common/templates/" fnam))

(defn cljs
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
               [".gitignore"                            (render ".gitignore"        config)]
               ["README.md"                             (render "README.md"         config)]
               ["dev.cljs.edn"                          (render "dev.cljs.edn"      config)]
               ["test.cljs.edn"                         (render "test.cljs.edn"     config)]
               ["figwheel-main.edn"                     (render "figwheel-main.edn" config)]
               ["project.clj"                           (render "project.clj"       config)]
               ["resources/public/css/style.css"        (render "style.css"         config)]
               ["resources/public/index.html"           (render "index.html"        config)]
               ["resources/public/test.html"            (render "test.html"         config)]
               ["src/{{path}}/state.cljs"               (render "state.cljs"        config)]
               ["src/{{path}}/core.cljs"                (render "core.cljs"         config)]
               ["src/{{path}}/navigation.cljs"          (render "navigation.cljs"   config)]
               ["src/{{path}}/utils.cljs"               (render "utils.cljs"        config)]
               ["src/{{path}}/pages/cookies.cljs"       (render "cookies.cljs"      config)]
               ["src/{{path}}/pages/counter.cljs"       (render "counter.cljs"      config)]
               ["test/{{path}}/pages/counter_test.cljs" (render "counter_test.cljs" config)]
               ["test/{{path}}/test_runner.cljs"        (render "test_runner.cljs"  config)]
               ))))
