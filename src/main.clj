(ns main
  (:require [matrix]
            [preprocessors]
            [optimizers]
            (tesser
              [core :as t]
              [math :as m])))

(def processed-data
  (let [raw-data (preprocessors/read-csv "data.csv")
        [y X] raw-data]
    (preprocessors/split (preprocessors/scale X) y 0.2)))

(defn probability-row [row W b]
  (->> (matrix/operate + (matrix/times W (matrix/transpose [row])) b)
       matrix/softmax
       matrix/transpose
       first))

(defn probability [X W b]
  (mapv #(probability-row % W b) X))

(defn argmax-row [row]
  (if (< (first row) (last row))
    1
    0))

(defn predict [p]
  (mapv argmax-row p))

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
      

(defn -main [& args]
  (let [[X_train y_train X_test y_test] processed-data
        [W b] (optimizers/run X_train y_train 0.001 0.2)]
    (println (matrix/shape W))
    (println (matrix/shape b))
    (println (matrix/shape X_test))
    (println (matrix/shape (probability X_test W b)))
    (println (counts y_test (predict (probability X_test W b))))
    ))
    
