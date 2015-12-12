(ns redux.core
  (:refer-clojure :exclude [juxt]))

(defn pre-step [rf f]
  (fn
    ([]      (rf))
    ([acc]   (rf acc))
    ([acc x] (rf acc (f x)))))

(defn post-complete [rf f]
  (fn
    ([]      (rf))
    ([acc]   (f (rf acc)))
    ([acc x] (rf acc x))))

(defn juxt [& rfns]
  (fn
    ([] (mapv (fn [f] (f)) rfns))
    ([acc] (mapv (fn [f a] (f (unreduced a))) rfns acc))
    ([acc x]
     (let [all-reduced? (volatile! true)
           results (mapv (fn [f a]
                           (if-not (reduced? a)
                             (do (vreset! all-reduced? false)
                                 (f a x))
                             a))
                         rfns acc)]
       (if @all-reduced? (reduced results) results)))))

(defn facet [rf fns]
  (->> (map (fn [f] (pre-step rf f)) fns)
       (apply juxt)))

(defn fuse [kvs]
  (let [rfns (vals kvs)]
    (post-complete (apply juxt rfns)
                   (fn [acc]
                     (zipmap (keys kvs) acc)))))
