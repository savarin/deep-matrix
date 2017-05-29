(ns optimizers
  (:require [matrix]))

(defn label-onehot [label]
  (->> [(vec (repeat 2 0))]
       matrix/transpose
       (#(assoc-in % [label 0] 1))))

(defn result [row-data label W b dropout-rate]
  (->> (matrix/operate + (matrix/times W row-data) b)
       ; matrix/relu
       ; (#(matrix/dropout % dropout-rate))
       (#(matrix/operate - % (label-onehot label)))))

(defn gradient [result column-data]
  (matrix/times result column-data))

(defn weights-update [W gradient learning-rate]
  (let [alpha (matrix/diagonal (count gradient) learning-rate)]
    (matrix/operate - W (matrix/times alpha gradient))))

(defn bias-update [b result learning-rate]
  (let [alpha (matrix/diagonal 2 learning-rate)]
    (matrix/operate - b (matrix/times alpha result))))

(defn run [X y learning-rate dropout-rate]
  (let [row-count (count X)
        column-count (count (first X))
        weights (matrix/times (matrix/random 2 column-count)
                        (matrix/diagonal column-count 0.001))
        bias (matrix/times (matrix/random 2 1)
                        (matrix/diagonal 1 0.001))]
    (loop [counter 0
           W weights
           b bias]
      (if (= row-count counter)
        [W b]
        (let [label (nth y counter)
              column-data [(nth X counter)]
              row-data (matrix/transpose column-data)
              result-nth (result row-data label W b dropout-rate)
              gradient-nth (gradient result-nth column-data)]
          (recur (inc counter)
                 (weights-update W gradient-nth learning-rate)
                 b))))))