(ns model
  (:require [matrix]))

(defn probability-row [row W b]
  (->> (matrix/operate + (matrix/times W (matrix/transpose [row])) b)
       matrix/relu
       matrix/softmax
       matrix/transpose
       first))

(defn probability [X W b]
  (mapv #(probability-row % W b) X))

(defn argmax-row [row]
  (if (< (first row) (last row))
    1
    0))

(defn predict [X W b]
  (mapv argmax-row (probability X W b)))
