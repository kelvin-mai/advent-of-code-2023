(ns aoc.d05
  (:require [clojure.string :as s]))

(def testinput
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

(defn nums->vec [s]
  (->> (-> s
           s/trim
           (s/split #" "))
       (map parse-long)
       vec))

(defn format-conversion-map [[k & vs]]
  [(first (s/split k #":"))
   (map nums->vec vs)])

(defn input->map [input]
  (let [[_ & maps] (->> input
                        (partition-by s/blank?)
                        (filter #(not (s/blank? (first %)))))
        maps (map format-conversion-map maps)]
    (reduce (fn [m [k v]]
              (assoc m k (vec v)))
            {}
            maps)))

(defn from-map [m k v]
  (let [conversion (->> (get m k)
                        (filter
                         (fn [[_ source r]]
                           (< source v (+ source r))))
                        first)
        [dest source _] conversion]
    (if conversion
      (+ (- v source)
         dest)
      v)))

(defn seed->location [m v]
  (->> v
       (from-map m "seed-to-soil map")
       (from-map m "soil-to-fertilizer map")
       (from-map m "fertilizer-to-water map")
       (from-map m "water-to-light map")
       (from-map m "light-to-temperature map")
       (from-map m "temperature-to-humidity map")
       (from-map m "humidity-to-location map")))

(defn part1 [input]
  (let [input (s/split input #"\n")
        conversion-map (input->map input)
        seeds (-> input
                  first
                  (s/split #":")
                  second
                  nums->vec)]
    (->> seeds
         (map (partial seed->location conversion-map))
         (apply min))))

(defn seed-range->min
  ([seed-range conversion-map]
   (seed-range->min java.lang.Integer/MAX_VALUE seed-range conversion-map))
  ([acc [start remaining] conversion-map]
   (if (= remaining 0)
     acc
     (seed-range->min
      (let [location (seed->location conversion-map (+ start remaining))]
        (if (< location acc)
          location
          acc))
      [start (dec remaining)]
      conversion-map))))

(defn part2 [input]
  (let [input (s/split input #"\n")
        conversion-map (input->map input)
        seeds (-> input
                  first
                  (s/split #":")
                  second
                  nums->vec)]
    (->> seeds
         (partition 2)
         ; works with test input
         ; stack overflows with puzzle input
         (map #(seed-range->min % conversion-map))
         (apply min)
         ;
         )))
