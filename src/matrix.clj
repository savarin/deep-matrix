(ns matrix
  (:import (java.lang Math)
           (java.util Random)))

(defn transpose [A]
  (apply mapv vector A))

(defn row [A i]
  (nth A i))

(defn column [A i]
  (transpose (vector (nth (transpose A) i))))

(defn random [m n]
  (loop [counter 0
         A []]
    (if (= m counter)
      A
      (recur (inc counter) 
             (conj A (vec (repeatedly n #(.nextGaussian (Random.)))))))))

(defn scalar-row [n i scalar]
  (assoc (vec (repeat n 0)) i scalar))

(defn diagonal [n scalar]
  (loop [counter 0
         A []]
    (if (= n counter)
      A
      (recur (inc counter)
             (conj A (scalar-row n counter scalar))))))

(defn operate [operator A B]
  (loop [counter 0
         C []]
    (if (= (count A) counter)
      C
      (recur (inc counter) 
             (conj C (mapv operator (nth A counter) (nth B counter)))))))

(defn dot-product [x y]
  (reduce + (map * x y)))

(defn times [A B]
  (let [row-mult (fn [C x] (mapv (partial dot-product x) (transpose C)))]
    (mapv (partial row-mult B) A)))

(defn exp [x]
  (Math/exp x))

(defn exp-matrix [A]
  (mapv (partial mapv exp) A))

(defn row-sum [x]
  (apply + x))

(defn matrix-sum [A]
  (row-sum (map row-sum A)))

(defn softmax [A]
  (let [B (exp-matrix A)
        column-sum (matrix-sum B)]
    (loop [counter 0
           C []]
      (if (= counter (count A))
        C
        (recur (inc counter)
               (conj C (mapv #(/ % column-sum) (nth B counter))))))))

(defn pos-only [x]
  (if (pos? x) x 0))

(defn relu [A]
  (mapv (partial mapv pos-only) A))

(defn nextfloat-row [n]
  (repeatedly n #(.nextFloat (Random.))))

(defn dropout
  [A probability]
  (let [m (count A)
        n (count (transpose A))
        over-probability (fn [x] (if (< probability x) 1 0))]
    (loop [counter 0
           B []]
      (if (= m counter)
        B
        (recur (inc counter)
               (conj B (mapv * (nth A counter)
                               (mapv over-probability (nextfloat-row n)))))))))

(defn shape [A]
  [(count A) (count (transpose A))])
