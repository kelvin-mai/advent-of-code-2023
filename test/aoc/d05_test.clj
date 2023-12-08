(ns aoc.d05-test
  (:require [aoc.d05 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def testcase
  "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4")

(def input
  (slurp "resources/d05.txt"))

(deftest day5-test
  (testing "day 05 part1"
    (is (= 35 (part1 testcase)))
    (is (= 379811651 (part1 input))))

  (testing "day 02 part2"
    (is (= 46 (part2 testcase)))
    ;; * Note * puzzle input takes 4 hours to complete, don't run in test runner 
    #_(is (= 27992443 (part2 input)))))
