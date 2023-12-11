(ns aoc.d09-test
  (:require [aoc.d09 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def testcase
  "0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45")

(def input
  (slurp "resources/d09.txt"))

(deftest day8-test
  (testing "day 09 part1"
    (is (= 114 (part1 testcase)))
    (is (= 1939607039 (part1 input))))
  (testing "day 09 part2"
    (is (= 2 (part2 testcase)))
    (is (= 1041 (part2 input)))))
