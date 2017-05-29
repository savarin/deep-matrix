(ns optimizers
  (:require [matrix]))

(defn label-onehot [label]
  (->> [(vec (repeat 2 0))]
       matrix/transpose
       (#(assoc-in % [label 0] 1))))

(defn result [row-data label W B probability]
  (->> (matrix/operate + (matrix/times W row-data) B)
       matrix/relu
       (#(matrix/dropout % probability))
       (#(matrix/operate - % (label-onehot label)))))

(defn gradient [result column-data]
  (matrix/times result column-data))

(defn update [W gradient learning-rate]
  (let [alpha (matrix/diagonal (count gradient) learning-rate)]
    (matrix/operate - W (matrix/times alpha gradient))))
