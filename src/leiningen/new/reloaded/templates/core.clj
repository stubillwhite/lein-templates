(ns {{name}}.core
  (:require
    [taoensso.timbre :as timbre]))

(timbre/refer-timbre)

(defn add-one
  ([x] 
   (+ x 1)))
