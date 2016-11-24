(ns redux.core
  (:require [redux.utils :refer [project complete-triangular-matrix pairs]])
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

(defn with-xform [rf xform]
  (let [rfv (volatile! nil)]
    (fn
      ([]
       (vreset! rfv (xform rf))
       (@rfv))
      ([acc]
       (@rfv acc))
      ([acc x]
       (@rfv acc x)))))

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
  (post-complete (apply juxt (vals kvs))
                 (fn [acc]
                   (zipmap (keys kvs) acc))))

(defn fuse-matrix [rf kvs]
  (-> (fuse (->> (pairs (keys kvs))
                 (map (fn [[k1 k2]]
                        [[k1 k2] (rf #(get % k1) #(get % k2))]))
                 (into {})))
      (pre-step (project kvs))
      (post-complete complete-triangular-matrix)))
