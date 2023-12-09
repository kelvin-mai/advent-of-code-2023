(ns aoc.d07
  (:require [clojure.string :as s]))

(def cards
  {\2 2, \3 3, \4 4, \5 5, \6 6, \7 7, \8 8, \9 9
   \T 10, \J 11, \Q 12, \K 13, \A 14})

(def cards-with-jokers
  (assoc cards \J 1))

(def hand-types
  {'(5) 7 ;; 5 kind
   '(4 1) 6 ;; 4 kind
   '(3 2) 5 ;; full house
   '(3 1 1) 4 ;; 3 kind
   '(2 2 1) 3 ;; 2 pair
   '(2 1 1 1) 2 ;; 1 pair
   '(1 1 1 1 1) 1}) ;; high card

(defn hand-value [[h _]]
  (get hand-types (sort > (vals (frequencies h)))))

(defn hand-value-with-jokers [[h _]]
  (let [hand (frequencies h)
        jokers (get hand \J 0)
        hand (sort > (vals (dissoc hand \J)))
        hand (if jokers
               (cons ((fnil + 0) (first hand) jokers) (rest hand))
               hand)]
    (get hand-types hand)))

(defn get-tie-breaker [cards [h1 _] [h2 _]]
  (loop [i 0]
    (if (= (nth h1 i) (nth h2 i))
      (recur (inc i))
      (compare (get cards (nth h1 i)) (get cards (nth h2 i))))))

(defn sort-hands [h1 h2]
  (if (= (hand-value h1) (hand-value h2))
    (get-tie-breaker cards h1 h2)
    (compare (hand-value h1) (hand-value h2))))

(defn sort-hands-with-jokers [h1 h2]
  (let [hand-value hand-value-with-jokers
        cards cards-with-jokers]
    (if (= (hand-value h1) (hand-value h2))
      (get-tie-breaker cards h1 h2)
      (compare (hand-value h1) (hand-value h2)))))

(defn parse-input [input]
  (->> (s/split input #"\n")
       (map #(s/split % #" "))))

(defn part1 [input]
  (->> (parse-input input)
       (sort sort-hands)
       (map #(parse-long (second %)))
       (map-indexed (fn [i v]
                      (* (inc i) v)))
       (reduce +)))

(defn part2 [input]
  (->> (parse-input input)
       (sort sort-hands-with-jokers)
       (map #(parse-long (second %)))
       (map-indexed (fn [i v]
                      (* (inc i) v)))
       (reduce +)))
