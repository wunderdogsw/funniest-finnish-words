(ns funny-words.core
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as math]))

(defn vowel-groups [word]
  (re-seq #"[aeiouyåäöAEIOUYÅÄÖ]+" word))

(defn funniness [word]
  {:word word :funniness
         (reduce + (map #(* (count %) (math/expt 2 (count %)))
                        (vowel-groups word)))})

(defn funniest-words [words]
  (let [sorted-words (sort-by :funniness words)
        max-funniness (:funniness (last sorted-words))]
    (filter #(= (:funniness %) max-funniness) sorted-words)))

(defn read-words [filename]
  (remove str/blank? (str/split (slurp filename) #"\P{L}")))

(defn score-words [filename]
  (map #(funniness %) (read-words filename)))

(defn -main [filename]
  (doseq [word (funniest-words (score-words filename))] (println word)))
