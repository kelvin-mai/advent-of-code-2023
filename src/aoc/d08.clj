(ns aoc.d08
  (:require [clojure.string :as s]))

(def testcase
  "LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)")

(defn ->lookup-map [m lookup]
  (let [[k v] (s/split lookup #" = ")
        v (re-seq #"\w+" v)]
    (assoc m k v)))

(defn parse-input [input]
  (let [[dirs lookups] (s/split input #"\n\n")
        lookup-map (->> (s/split lookups #"\n")
                        (reduce ->lookup-map {}))]
    [dirs lookup-map]))

(defn convert [m k dir]
  (let [[l r] (get m k)]
    (if (= dir \R)
      r
      l)))

(defn steps-to-target [start target [dirs m]]
  (loop [i 0
         k start]
    (let [dir (nth (cycle dirs) i)
          v (convert m k dir)]
      (if (= v target)
        (inc i)
        (recur (inc i) v)))))

#_(defn steps-to-char [start c [dirs m]]
    ;; brute force too slow
    (let [starts (->> (keys m)
                      (filter #(s/ends-with? % start)))]
      (loop [i 0
             ks starts]
        (let [dir (nth (cycle dirs) i)
              v (map #(convert m % dir) ks)]
          (if (every? #(s/ends-with? % c) v)
            (inc i)
            (recur
             (inc i) v))))))

(defn steps-to-char [start c [dirs m]]
  (loop [i 0
         k start]
    (let [dir (nth (cycle dirs) i)
          v (convert m k dir)]
      (if (s/ends-with? v c)
        (inc i)
        (recur (inc i) v)))))

(defn gcd [a b]
  (if (zero? b) a (recur b (mod a b))))

(defn lcm [a b]
  (* (abs a) (/ (abs b) (gcd a b))))

(defn lcm-multiple [v]
  (reduce lcm (first v) (rest v)))

(defn steps-to-char-lcm [start c [dirs m]]
  (let [starts (->> (keys m)
                    (filter #(s/ends-with? % start)))]
    (->> starts
         (map #(steps-to-char % c [dirs m]))
         (lcm-multiple))))

(defn part1 [input]
  (->> (parse-input input)
       (steps-to-target "AAA" "ZZZ")))

(defn part2 [input]
  (->> (parse-input input)
       (steps-to-char-lcm "A" "Z")))

(comment
  (parse-input testcase)
  (steps-to-char "11A" "Z"
                 ["LR"
                  {"11A" ["11B" "XXX"],
                   "11B" ["XXX" "11Z"],
                   "11Z" ["11B" "XXX"],
                   "22A" ["22B" "XXX"],
                   "22B" ["22C" "22C"],
                   "22C" ["22Z" "22Z"],
                   "22Z" ["22B" "22B"],
                   "XXX" ["XXX" "XXX"]}])

  (reduce lcm
          (first [19631 20803 23147 17287 13771 17873])
          (rest [19631 20803 23147 17287 13771 17873]))

  (part2 testcase)
  (part2 (slurp "resources/d08.txt"))
  ;
  )
