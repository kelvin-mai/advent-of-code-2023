(ns aoc.d06-test
  (:require [aoc.d06 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def testcase
  "Time:      7  15   30
Distance:  9  40  200")

(def input
  (slurp "resources/d06.txt"))

(deftest day6-test
  (testing "day 06 part1"
    (is (= 288 (part1 testcase)))
    (is (= 4403592 (part1 input))))
  (testing "day 06 part2"
    (is (= 71503 (part2 testcase)))
    (is (= 38017587 (part2 input)))))
