(ns aoc.d02
  (:require [clojure.string :as s]))

(def testcase
  "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(defn build-game-map [acc curr]
  (let [[n color] (s/split curr #" ")
        n (parse-long n)
        max-n (get acc color)]
    (if (or (nil? max-n)
            (< max-n n))
      (assoc acc color n)
      acc)))

(defn build-game [x]
  (let [input (s/split x #":|,|;")
        game-id (-> (first input)
                    (s/split #" ")
                    last
                    parse-long)
        game (->> (rest input)
                  (map s/trim)
                  (reduce build-game-map {}))]
    (assoc game :id game-id)))

(defn part1 [input m]
  (->> (s/split input #"\n")
       (map build-game)
       (filter #(and
                 (>= (get m "red") (get % "red"))
                 (>= (get m "green") (get % "green"))
                 (>= (get m "blue") (get % "blue"))))
       (map :id)
       (reduce +)))

(defn part2 [input]
  (->> (s/split input #"\n")
       (map build-game)
       (map #(* (get % "red")
                (get % "green")
                (get % "blue")))
       (reduce +)))

(comment
  (part1 testcase {"red" 12
                   "green" 13
                   "blue" 14}) ;; => 8
  (part2 testcase) ;; => 2286
  (part1 (slurp "resources/day2.txt") {"red" 12
                                       "green" 13
                                       "blue" 14}) ;; => 2576
  (part2 (slurp "resources/day2.txt")) ;; => 54911
  ;
  )
