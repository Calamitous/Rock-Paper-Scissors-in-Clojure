(ns rps_clojure.core
  (:use clojure.contrib.combinatorics))

(defn play [name1 name2 completed-play]
  ({
    [:paper :rock] name1
    [:scissors :paper] name1
    [:rock :scissors] name1

    [:rock :paper] name2
    [:paper :scissors] name2
    [:scissors :rock] name2 } completed-play 'draw))

(defn winner [previous-runs run-count player1 player2]
  (let [player1-selection (eval (list player1 0 previous-runs))
        player2-selection (eval (list player2 1 previous-runs))
        result [player1-selection player2-selection]]
  (play player1 player2 result)))

(defn play-all [players]
  (let [player1 (first players)
        player2 (last players)]
  (reduce #(conj %1 (winner %1 %2 player1 player2)) [] (range 99))))

(defn calculate-wins [plays]
  (reduce #(assoc %1 %2 (inc (%1 %2 0))) {} plays))

(defn the-randomator ^{:nym "The Randomator"} [pos prev] (rand-nth [:rock :paper :scissors]))
(defn mr-scissors ^{:nym "Mr. Scissors"} [pos prev] :scissors)
(defn round-robin ^{:nym "Round Robin"} [pos prev] ({:rock :paper, :paper :scissors, :scissors :rock} (last prev) :rock))

(def players `(the-randomator mr-scissors round-robin))

(play-all (list (first combinations players 2)))
; (map #(play-all (list %)) (combinations players 2))

; (reduce #(calculate-wins (play-all %)) (combinations players 2))
; (reduce #(assoc %1 (calculate-wins (play-all %2))) {} (combinations players 2))
