(ns aoc.d06
  (:require [clojure.string :as s]))

(defn parse-input [input]
  (->> (s/split input #"\n")
       (map #(s/split % #":"))
       (map second)
       (map s/trim)
       (map #(re-seq #"\d+" %))))

(defn combos [[t d]]
  (loop [s 0
         ds []]
    (let [rt (- t s)
          td (* rt s)]
      (if (neg? rt)
        ds
        (recur
         (inc s)
         (if (> td d)
           (conj ds td)
           ds))))))

(defn part1 [input]
  (->> input
       parse-input
       (map #(map parse-long %))
       (apply map list)
       (map combos)
       (map count)
       (reduce *)))

(defn part2 [input]
  (->> input
       parse-input
       (map #(apply str %))
       (map parse-long)
       combos
       count))
