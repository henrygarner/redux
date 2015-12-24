(ns redux.utils-test
  (:require [redux.utils :refer :all]
            [clojure.test :refer :all]))

(deftest project-test
  (let [f (project {:a inc :b dec})]
    (is (= (f 10) {:a 11 :b 9}))))

(deftest complete-triangular-matrix-test
  (let [m {[:a :b] 1 [:a :c] 2 [:b :c] 3}]
    (is (= (complete-triangular-matrix m)
           (merge m {[:b :a] 1 [:c :a] 2 [:c :b] 3})))))
