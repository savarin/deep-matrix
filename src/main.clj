(ns main
  (:require [matrix]
            [preprocessors]))

(defn -main [& args]
  (println (preprocessors/read-csv "head.csv")))