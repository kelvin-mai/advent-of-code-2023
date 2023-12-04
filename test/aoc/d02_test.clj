(ns aoc.d02-test
  (:require [aoc.d02 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def testcase
  "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

(def input
  (slurp "resources/d02.txt"))

(deftest day2-test
  (testing "day 02 part1"
    (let [available-game-map {"red" 12
                              "green" 13
                              "blue" 14}]
      (is (= 8 (part1 testcase available-game-map)))
      (is (= 2476 (part1 input available-game-map)))))
  (testing "day 02 part2"
    (is (= 2286 (part2 testcase)))
    (is (= 54911 (part2 input)))))
