(ns aoc.d01-test
  (:require [aoc.d01 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def part1-testcase
  "1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet")

(def part2-testcase
  "two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen")

(def input
  (slurp "resources/d01.txt"))

(deftest day1-test
  (testing "day 01 part1"
    (is (= 142 (part1 part1-testcase)))
    (is (= 54877 (part1 input))))
  (testing "day 01 part2"
    (is (= 281 (part2 part2-testcase)))
    (is (= 54100 (part2 input)))))
