(ns aoc.d10
  (:require [clojure.string :as s]))

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

(def dir->coord
  {:north '(0 -1)
   :east '(1 0)
   :west '(-1 0)
   :south '(0 1)})

(def coord->dir
  (reduce (fn [m [k v]] (assoc m v k))
          {} dir->coord))

(def tiles
  {\| [:north :south]
   \- [:east :west]
   \L [:north :east]
   \J [:north :west]
   \7 [:south :west]
   \F [:south :east]})

(defn parse-line [y line]
  (map-indexed
   (fn [x c]
     {:loc (list x y)
      :tile c})
   line))

(defn parse-input [input]
  (let [input (s/split input #"\n")]
    (->> input
         (map-indexed parse-line)
         flatten)))

(defn get-next-dir [from to]
  (let [from-dir (get coord->dir (map - from (:loc to)))
        connections (get tiles (:tile to))
        next-dir (first (filter #(not= from-dir %) connections))]
    (when (some (set [from-dir]) connections)
      (assoc to :next next-dir))))

(defn get-tile-in-grid [grid loc]
  (first (filter #(= (:loc %) loc) grid)))

(defn get-first-tiles [grid loc]
  (let [dirs (keys coord->dir)
        next-coords (map #(map + % loc) dirs)
        next-tiles (->> next-coords
                        (map (partial get-tile-in-grid grid))
                        (map (partial get-next-dir loc))
                        (filter identity))]
    next-tiles))

(defn get-next-tile [grid {:keys [loc next]}]
  (let [dir (get dir->coord next)
        next-coord (map + loc dir)
        next-tile (get-tile-in-grid grid next-coord)]
    (get-next-dir loc next-tile)))

(defn furthest-step [grid]
  (let [{:keys [loc]} (first (filter #(= (:tile %) \S) grid))
        first-tiles (get-first-tiles grid loc)]
    (loop [step 1
           next-tiles first-tiles]
      (if (apply = (map :loc next-tiles))
        step
        (recur
         (inc step)
         (map (partial get-next-tile grid) next-tiles))))))

(defn part1 [input]
  (->> (parse-input input)
       furthest-step))

(defn part2 [input]
  (->> (parse-input input)))

(comment
  (part1 testcase-3)
  (part2 (slurp "resources/d10.txt"))
  ;
  )
