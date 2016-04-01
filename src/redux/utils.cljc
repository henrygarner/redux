(ns redux.utils)

(defn project [fmap]
  (fn [x]
    (->> (reduce-kv (fn [m k v] (assoc! m k (v x)))
                    (transient {})
                    fmap)
         (persistent!))))

(defn complete-triangular-matrix
  [m]
  (->> (map (fn [[[x y] v]]
              [[y x] v]) m)
       (into m)))

(defn pairs [coll]
  (letfn [(pair [x xs]
            (if (seq xs)
              (concat (map #(vector x %) xs)
                      (pair (first xs) (rest xs)))
              []))]
    (pair (first coll) (rest coll))))
