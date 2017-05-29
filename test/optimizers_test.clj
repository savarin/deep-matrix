(ns optimizers-test
  (:require [clojure.test :refer :all]
            [optimizers :refer :all]))

(deftest label-onehot-test
  (testing "onehot vector representing label"
    (is (= [[1] [0]]
           (label-onehot 0)))))
