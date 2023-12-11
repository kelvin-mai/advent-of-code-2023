(ns aoc.d09
  (:require [clojure.string :as s]))

(defn parse-input [input]
  (->> (s/split input #"\n")
       (map #(s/split % #" "))
       (map #(map parse-long %))))

(defn diffs [v]
  (->> (partition 2 1 v)
       (map (fn [[a b]] (- b a)))))

(defn get-history [v]
  (loop [h '()
         v v]
    (let [history (if (empty? v)
                    h
                    (conj h v))]
      (if (every? zero? v)
        history
        (recur history (diffs v))))))

(defn extrapolate [v]
  (apply (fnil + 0) (map last v)))

(defn part1 [input]
  (->> (parse-input input)
       (map get-history)
       (map extrapolate)
       (reduce +)))

(defn part2 [input]
  (->> (parse-input input)
       (map reverse)
       (map get-history)
       (map extrapolate)
       (reduce +)))
