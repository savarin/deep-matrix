(ns main
  (:require [matrix]
            [preprocessors]
            (tesser
              [core :as t]
              [math :as m])))

(def data1
  (preprocessors/read-csv "data.csv"))

(def data2
  (let [[y X] data1]
    [y (preprocessors/scale X)]))

(def data3
  (let [[y X] data2]
    (preprocessors/split X y 0.2)))

(defn -main [& args])
