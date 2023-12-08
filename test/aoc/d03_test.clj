(ns aoc.d03-test
  (:require [aoc.d03 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def testcase
  "467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..")

(def input
  (slurp "resources/d03.txt"))

(deftest day3-test
  (testing "day 03 part1"
    (is (= 4361 (part1 testcase)))
    (is (= 535235 (part1 input))))
  (testing "day 03 part2"
    (is (= 467835 (part2 testcase)))
    (is (= 79844424 (part2 input)))))
