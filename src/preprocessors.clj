(ns preprocessors
  (:require [matrix]
            (tesser
              [core :as t]
              [math :as m])))

(defn label-feature-split [matrix]
  [(mapv first matrix)
   (mapv #(vec (rest %)) matrix)])

(defn read-csv [file-path]
  (->> (slurp file-path)
       (#(clojure.string/split % #"\n"))
       (mapv #(clojure.string/split % #","))
       (mapv (partial mapv read-string))
       rest
       label-feature-split))

(defn calculate-stats [collection]
  ; returns map {:mean mean-value :std std-value}
  (let [summary (->> (t/fuse {:mean (m/mean)
                              :std (m/standard-deviation)})
                     (t/tesser [collection]))]
    summary))

(defn scale-row [x]
  (let [{:keys [mean std]} (calculate-stats x)
        scale-entry #(/ (- % mean) std)]
    (mapv scale-entry x)))

(defn scale [A]
  (mapv scale-row A))

(defn subvec-matrix [X start end]
  (matrix/transpose (subvec (matrix/transpose X) start end)))

(defn split [X y test-fraction]
  (let [row-size (count (first X))
        train-size (* row-size (- 1 test-fraction))]
    [(subvec-matrix X 0 train-size) (subvec y 0 train-size)
     (subvec-matrix X train-size row-size) (subvec y train-size row-size)]))
