(ns aoc.d05
  (:require [clojure.string :as s]))

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
                           (<= source v (+ source r))))
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

(defn seed-range->min-step
  [acc [start remaining] conversion-map remaining-steps]
  (if (< remaining-steps 0)
    [acc [start remaining]]
    (if (< remaining 0)
      acc
      (recur
       (let [location (seed->location conversion-map (+ start remaining))]
         (if (< location acc)
           location
           acc))
       [start (dec remaining)]
       conversion-map
       (dec remaining-steps)))))

(defn seed-range->min [seed-range conversion-map]
  (let [depth 10000]
    (loop [acc java.lang.Integer/MAX_VALUE
           seed-range seed-range]
      (let [curr (seed-range->min-step acc seed-range conversion-map depth)]
        #_(println "Current iteration value: " curr) ;; DEBUG
        (if (int? curr)
          curr
          (recur (first curr) (second curr)))))))

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
         (map #(seed-range->min % conversion-map))
         (apply min))))
