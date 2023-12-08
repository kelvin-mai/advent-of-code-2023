(ns aoc.d03
  (:require [clojure.string :as s]))

(defn token [c]
  (cond
    (java.lang.Character/isDigit c) :digit
    (= \. c) :empty
    :else :symbol))

(defn parse-line [y line]
  (map-indexed
   (fn [i c]
     {:y y
      :x i
      :value c
      :type (token c)})
   line))

(defn at-coords [x y coll]
  (first
   (filter #(and (= x (:x %))
                 (= y (:y %)))
           coll)))

(defn get-neighbors [kw x y coll]
  (->> [[-1 -1] [0 -1] [1 -1]
        [-1 0]         [1 0]
        [-1 1] [0 1] [1 1]]
       (map (fn [[dx dy]]
              (at-coords (+ x dx) (+ y dy) coll)))
       (filter (fn [m]
                 (= (:type m) kw)))))

(defn process-num [v]
  (when (not-empty (filter #(not-empty (:neighbors %)) v))
    (parse-long (apply str (map :value v)))))

(defn create-schematics [input neighbor]
  (let [schematics (->> input
                        (map-indexed parse-line)
                        flatten
                        (filter #(not= (:type %) :empty)))]
    (->> schematics
         (map (fn [m]
                (assoc m
                       :neighbors
                       (get-neighbors neighbor (:x m) (:y m) schematics)))))))

(defn schematics-digits->nums
  [[x-size y-size] schematics]
  (loop [[x y] [0 0]
         in-process []
         result []]
    (let [curr (at-coords x y schematics)
          [_ next-y :as next-coords] (if (> (inc x) x-size)
                                       [0 (inc y)]
                                       [(inc x) y])
          is-digit? (= (:type curr) :digit)]
      (if (> next-y y-size)
        result
        (recur
         next-coords
         (if is-digit?
           (conj in-process curr)
           [])
         (if is-digit?
           result
           (conj result (process-num in-process))))))))

(defn remove-consecutive-neighbors-step [n]
  (loop [result []
         coll n]
    (let [curr-n (first coll)
          next-n (second coll)
          consecutive? (when next-n
                         (= (abs (- (:x curr-n) (:x next-n))) 1))]
      (if (empty? coll)
        result
        (recur
         (if consecutive?
           result
           (conj result curr-n))
         (next coll))))))

(defn remove-consecutive-neighbors [n]
  (let [lines (partition-by :y n)
        singles (filter #(= (count %) 1) lines)
        process (filter #(not= (count %) 1) lines)]
    (->> process
         (map remove-consecutive-neighbors-step)
         (conj singles)
         flatten)))

(defn get-adjacent-digits [[x y] schematics step-fn]
  (loop [diff 1
         in-process []]
    (let [adjacent (at-coords (step-fn x diff) y schematics)
          is-digit? #(= (:type %) :digit)]
      (if (is-digit? adjacent)
        (recur
         (inc diff)
         (conj in-process adjacent))
        in-process))))

(defn num-at-coords [[x y] schematics]
  (let [curr-digit (at-coords x y schematics)
        prev-digits (get-adjacent-digits [x y] schematics -)
        next-digits (get-adjacent-digits [x y] schematics +)
        process-num #(parse-long (apply str (map :value %)))]
    (->> [(reverse prev-digits)
          curr-digit
          next-digits]
         flatten
         process-num)))

(defn schematics-gear->num
  [schematics gear]
  (let [neighbors (->> (:neighbors gear)
                       remove-consecutive-neighbors
                       (map (fn [n] [(:x n) (:y n)]))
                       (map #(num-at-coords % schematics)))]

    (when (= (count neighbors) 2)
      (reduce * neighbors))))

(defn part1 [input]
  (let [input (s/split input #"\n")
        x-size (count (first input))
        y-size (count input)]
    (->> (create-schematics input :symbol)
         (schematics-digits->nums [x-size y-size])
         (filter identity)
         (reduce +))))

(defn part2 [input]
  (let [input (s/split input #"\n")
        schematics (create-schematics input :digit)]
    (->> schematics
         (filter #(= (:value %) \*))
         (filter #(> (count (:neighbors %)) 1))
         (map #(schematics-gear->num schematics %))
         (filter identity)
         (reduce +))))
