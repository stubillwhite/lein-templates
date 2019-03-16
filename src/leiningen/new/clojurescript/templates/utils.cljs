(ns {{main-ns}}.utils)

(defn index-by
  "Returns xs as a map indexed by (f x)."
  [f xs]
  (into {} (for [x xs] [(f x) x])))
