(ns redux.core-test
  (:refer-clojure :exclude [juxt])
  (:require [clojure.test :refer :all]
            [redux.core :refer :all]))

(deftest pre-step-test
  (is (= (transduce identity (pre-step + :a) [{:a 1} {:a 2}])
         3)))

(deftest post-complete-test
  (is (= (transduce identity (post-complete + str) [1 2])
         "3")))

(deftest juxt-test
  (is (= (transduce identity (juxt + conj) [1 2])
         [3 [1 2]])))

(deftest facet-test
  (is (= (transduce identity (facet + [dec inc]) [1 2])
         [1 5])))

(deftest fuse-test
  (is (= (transduce identity (fuse {:a + :b conj}) [1 2])
         {:a 3 :b [1 2]})))
