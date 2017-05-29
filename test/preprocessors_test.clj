(ns preprocessors-test
  (:require [clojure.test :refer :all]
            [preprocessors :refer :all]))

(deftest calculate-stats-test
  (testing "mean and standard deviation calculation"
    (is (= {:mean 1.0, :std 1.0}
           (calculate-stats [0 1 2])))))

(deftest scale-test
  (testing "matrix scaling operation"
    (is (= [[-1.0 0.0 1.0] [-1.0 0.0 1.0]]
           (preprocessors/scale [[0 1 2] [0 1 2]])))))

(deftest split-test
  (testing "train-test split operation"
    (is (= [[[0 1 2 3] [0 1 2 3]] [5 6 7 8] [[4] [4]] [9]]
           (preprocessors/split [[0 1 2 3 4] [0 1 2 3 4]] [5 6 7 8 9] 0.2)))))
