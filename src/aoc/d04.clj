(ns aoc.d04
  (:require [clojure.string :as s]))

(defn get-nums [s]
  (->> (s/split s #" ")
       (filter not-empty)
       (map parse-long)))

(defn check-winners [card]
  (let [x (s/split card #":|\|")
        winning-n (-> (second x)
                      get-nums
                      set)
        scratched (-> (last x)
                      get-nums)]
    (filter #(contains? winning-n %) scratched)))

(defn calculate-instances
  ([cards]
   (calculate-instances 0 cards))
  ([i cards]
   (if (< i (count cards))
     (let [curr (nth cards i)
           update-id (+ i (:copies-next curr))
           new-cards (cond-> (update-in cards [i :copies-next] dec)
                       (get cards update-id)
                       (update-in [update-id :instances]
                                  #(+ % (:instances curr))))]
       (if (pos? (:copies-next curr))
         (calculate-instances i new-cards)
         (calculate-instances (inc i) cards)))
     cards)))

(defn part1 [input]
  (->> (s/split input #"\n")
       (map check-winners)
       (map count)
       (filter pos?)
       (map dec)
       (map #(Math/pow 2 %))
       (map int)
       (reduce +)))

(defn part2 [input]
  (->> (s/split input #"\n")
       (map check-winners)
       (map count)
       (map-indexed (fn [i x]
                      {:indexed i
                       :win? (pos? x)
                       :copies-next x
                       :instances 1}))
       vec
       calculate-instances
       (map :instances)
       (reduce +)))
