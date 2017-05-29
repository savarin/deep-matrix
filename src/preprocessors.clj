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
  (matrix/transpose (mapv scale-row (matrix/transpose A))))

(defn subvec-matrix [X start end]
  (matrix/transpose (subvec (matrix/transpose X) start end)))

(defn split [X y test-fraction]
  (let [row-count (count X)
        train-count (* row-count (- 1 test-fraction))]
    [(subvec X 0 train-count) (subvec y 0 train-count)
     (subvec X train-count row-count) (subvec y train-count row-count)]))

(defn process-data [file-path]
  (let [raw-data (read-csv file-path)
        [y X] raw-data]
    (split (scale X) y 0.2)))
