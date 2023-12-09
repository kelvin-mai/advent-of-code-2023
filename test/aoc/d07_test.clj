(ns aoc.d07-test
  (:require [aoc.d07 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def testcase
  "32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483")

(def input
  (slurp "resources/d07.txt"))

(deftest day7-test
  (testing "day 07 part1"
    (is (= 6440 (part1 testcase)))
    (is (= 249390788 (part1 input))))
  (testing "day 07 part2"
    (is (= 5905 (part2 testcase)))
    (is (= 248750248 (part2 input)))))
