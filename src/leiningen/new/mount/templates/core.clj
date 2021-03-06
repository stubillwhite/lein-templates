(ns {{main-ns}}.core
  (:require
   [taoensso.timbre :as timbre]
   [taoensso.timbre.appenders.core :as appenders]))

(timbre/refer-timbre)

(timbre/merge-config! {:appenders {:println {:enabled? false}}})   

(timbre/merge-config!
  {:appenders {:spit (appenders/spit-appender {:fname "{{main-ns}}.log"})}})

(defn add-one
  ([x] 
   (+ x 1)))
