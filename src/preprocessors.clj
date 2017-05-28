(ns preprocessors)

(defn read-csv [file-path]
  (->> (slurp file-path)
       (#(clojure.string/split % #"\n"))
       (mapv #(clojure.string/split % #","))
       (mapv (partial mapv read-string))))