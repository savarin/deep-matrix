(ns deep-matrix-clojure.core-test
  (:require [clojure.test :refer :all]
            [deep-matrix-clojure.core :refer :all]))

(deftest transpose-test
  (testing "matrix transpose operation"
    (is (= [[1 1] 
            [0 1]]
           (transpose [[1 0]
                       [1 1]])))))

(deftest diagonal-test
  (testing "diagonal matrix constructor"
    (is (= [[1 0] 
            [0 1]]
           (diagonal 2 1)))))

(deftest plus-test
  (testing "matrix plus operation"
    (is (= [[3 3] 
            [3 3]]
           (operate + [[1 1]
                       [1 1]]
                      [[2 2]
                       [2 2]])))))

(deftest minus-test
  (testing "matrix minus operation"
    (is (= [[1 1] 
            [1 1]]
           (operate - [[3 3]
                       [3 3]]
                      [[2 2]
                       [2 2]])))))

(deftest times-test
  (testing "matrix minus operation"
    (is (= [[4 0] 
            [0 4]]
           (times [[2 0]
                   [0 2]]
                  [[2 0]
                   [0 2]])))))

(deftest softmax-test
  (testing "matrix softmax operation"
    (is (= [[0.25 0.25] 
            [0.25 0.25]]
           (softmax [[1 1]
                     [1 1]])))))

(deftest relu-test
  (testing "matrix relu operation"
    (is (= [[1 0] 
            [0 1]]
           (relu [[1 -1]
                  [-1 1]])))))
