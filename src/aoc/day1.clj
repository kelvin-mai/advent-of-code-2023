(ns aoc.day1
  (:require [clojure.string :as s]))

(def part1-testcase
  "1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet") ;; => 142

(def part2-testcase
  "two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen") ;; => 281

(def digit-word
  {"one" "1"
   "two" "2"
   "three" "3"
   "four" "4"
   "five" "5"
   "six" "6"
   "seven" "7"
   "eight" "8"
   "nine" "9"})

(defn find-digit-words [s]
  (let [re (str "(?=(\\d|" (s/join "|" (keys digit-word)) "))")]
    (->> (re-seq (re-pattern re) s)
         (map second)
         (map #(get digit-word % %))
         (filter identity))))

(defn find-digits [s]
  (re-seq #"\d" s))

(defn parse-first-last [n]
  (parse-long
   (str (first n) (last n))))

(defn calibrate [x find-fn]
  (->> (s/split x #"\n")
       (map find-fn)
       (map parse-first-last)
       (reduce +)))

(defn part1 []
  (calibrate (slurp "resources/day1.txt") find-digits))

(defn part2 []
  (calibrate (slurp "resources/day1.txt") find-digit-words))

(comment
  (calibrate part1-testcase find-digits)
  (calibrate part2-testcase find-digit-words)
  (part1) ;; => 54877
  (part2) ;; => 54100
  )
