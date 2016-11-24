(ns redux.core-test
  (:refer-clojure :exclude [juxt])
  (:require [redux.core :as r]
            #?(:cljs [cljs.test :refer-macros [is deftest]]
               :clj [clojure.test :refer :all])))

(deftest pre-step-test
  (is (= (transduce identity (r/pre-step + :a) [{:a 1} {:a 2}])
         3)))

(deftest post-complete-test
  (is (= (transduce identity (r/post-complete + str) [1 2])
         "3")))

(deftest with-xform-test
  (is (= (transduce identity (r/with-xform + (filter odd?)) [1 2 3])
         4)))

(deftest with-stateful-xform-test
  (let [rf (r/with-xform conj (take 2))]
    (is (= (transduce identity rf [1 2 3])
           [1 2]))
    (is (= (transduce identity rf [1 2 3])
           [1 2]))))

(deftest juxt-test
  (is (= (transduce identity (r/juxt + conj) [1 2])
         [3 [1 2]])))

(deftest facet-test
  (is (= (transduce identity (r/facet + [dec inc]) [1 2])
         [1 5])))

(deftest fuse-test
  (is (= (transduce identity (r/fuse {:a + :b conj}) [1 2])
         {:a 3 :b [1 2]})))

(deftest fuse-matrix-test
  (let [rf (fn [xf yf]
             (fn
               ([] 0)
               ([acc] acc)
               ([acc x] (+ acc (xf x) (yf x)))))]
    (is (= (transduce identity (r/fuse-matrix rf {:a inc :b identity :c dec}) (range 10))
           {[:a :b] 100, [:a :c] 90, [:b :c] 80,
            [:b :a] 100, [:c :a] 90, [:c :b] 80}))))
