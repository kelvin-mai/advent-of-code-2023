(ns aoc.d10-test
  (:require [aoc.d10 :refer [part1 part2]]
            [clojure.test :refer :all]))

(def testcase-1
  ".....
.S-7.
.|.|.
.L-J.
.....")

(def testcase-2
  "..F7.
.FJ|.
SJ.L7
|F--J
LJ...")

(def testcase-3
  "...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
...........")

(def testcase-4
  ".F----7F7F7F7F-7....
.|F--7||||||||FJ....
.||.FJ||||||||L7....
FJL7L7LJLJ||LJ.L-7..
L--J.L7...LJS7F-7L7.
....F-J..F7FJ|L7L7L7
....L7.F7||L7|.L7L7|
.....|FJLJ|FJ|F7|.LJ
....FJL-7.||.||||...
....L---J.LJ.LJLJ...")

(def input
  (slurp "resources/d10.txt"))

(deftest day10-test
  (testing "day 10 part1"
    (is (= 4 (part1 testcase-1)))
    (is (= 8 (part1 testcase-2)))
    (is (= 7107 (part1 input))))
  #_(testing "day 10 part2"
      (is (= 4 (part2 testcase-3)))
      (is (= 10 (part2 testcase-4)))
      #_(is (= 1041 (part2 input)))))
