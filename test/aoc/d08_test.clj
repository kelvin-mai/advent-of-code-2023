(ns aoc.d08-test
  (:require [aoc.d08 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def part1-testcase-1
  "RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)")

(def part1-testcase-2
  "LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)")

(def part2-testcase
  "LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)")

(def input
  (slurp "resources/d08.txt"))

(deftest day8-test
  (testing "day 08 part1"
    (is (= 2 (part1 part1-testcase-1)))
    (is (= 6 (part1 part1-testcase-2)))
    (is (= 17287 (part1 input))))
  (testing "day 08 part2"
    (is (= 6 (part2 part2-testcase)))
    (is (= 18625484023687 (part2 input)))))
