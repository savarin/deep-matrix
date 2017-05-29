(ns metrics)

(defn counts [y_test y_prediction]
  (let [row-count (count y_test)]
    (loop [counter 0
           itemizer [0 0 0 0]]
      (cond
        (= row-count counter)
          itemizer
        (and (= (nth y_test counter) 1)
             (= (nth y_prediction counter) 1))
          (recur (inc counter) (update itemizer 0 inc))
        (and (= (nth y_test counter) 0)
             (= (nth y_prediction counter) 1))
          (recur (inc counter) (update itemizer 1 inc))
        (and (= (nth y_test counter) 1)
             (= (nth y_prediction counter) 0))
          (recur (inc counter) (update itemizer 2 inc))
        :else (recur (inc counter) (update itemizer 3 inc))))))

(defn accuracy [y_test y_prediction]
  (let [row-count (count y_test)
        [true-pos _ _ true-neg] (counts y_test y_prediction)]
    (/ (+ true-pos true-neg) (double row-count))))

(defn precision [y_test y_prediction]
  (let [[true-pos false-pos _ _] (counts y_test y_prediction)]
    (/ true-pos (double (+ true-pos false-pos)))))

(defn recall [y_test y_prediction]
  (let [[true-pos _ false-neg _] (counts y_test y_prediction)]
    (/ true-pos (double (+ true-pos false-neg)))))