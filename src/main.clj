(ns main
  (:require [matrix]
            [preprocessors]
            [optimizers]
            [model]
            [metrics]))

(defn -main [& args]
  (let [[X_train y_train X_test y_test] (preprocessors/process-data "data.csv")
        [W b] (optimizers/run X_train y_train 0.001 0.2)
        y_prediction (model/predict X_test W b)]
    (do (println (format "accuracy:  %.3f" (metrics/accuracy y_test y_prediction)))
        (println (format "precision: %.3f" (metrics/precision y_test y_prediction)))
        (println (format "recall:    %.3f" (metrics/recall y_test y_prediction))))
    ))
    
