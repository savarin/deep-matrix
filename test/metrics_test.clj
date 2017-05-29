(ns metrics-test
  (:require [clojure.test :refer :all]
            [metrics :refer :all]))

(deftest counts-test
  (testing "prediction counts"
    (is (= [1 1 1 1]
           (counts [1 0 1 0] [1 1 0 0])))))

(deftest accuracy-test
  (testing "accuracy prediction"
    (is (= 0.5
           (accuracy [1 0 1 0] [1 1 0 0])))))

(deftest precision-test
  (testing "precision prediction"
    (is (= 0.5
           (precision [1 0 1 0] [1 1 0 0])))))

(deftest recall-test
  (testing "recall prediction"
    (is (= 0.5
           (recall [1 0 1 0] [1 1 0 0])))))