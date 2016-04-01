(ns redux.utils-test
  (:require [redux.utils :as r]
            #?(:cljs [cljs.test :refer-macros [is deftest]]
               :clj [clojure.test :refer :all])))

(deftest project-test
  (let [f (r/project {:a inc :b dec})]
    (is (= (f 10) {:a 11 :b 9}))))

(deftest complete-triangular-matrix-test
  (let [m {[:a :b] 1 [:a :c] 2 [:b :c] 3}]
    (is (= (r/complete-triangular-matrix m)
           (merge m {[:b :a] 1 [:c :a] 2 [:c :b] 3})))))

(deftest pairs-test
  (let [xs [1 2 3 4]]
    (is (= (set (r/pairs xs))
           (set [[1 2] [1 3] [1 4] [2 3] [2 4] [3 4]])))))
