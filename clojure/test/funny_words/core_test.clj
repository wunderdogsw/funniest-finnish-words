(ns funny-words.core-test
  (:require [midje.sweet :refer :all]
            [funny-words.core :refer :all]))
(tabular
  (fact "funniness of words"
        (:funniness (funniness ?word)) => ?expected)
  ?word       ?expected
  "hääyöaie"  896
  "koira"     10
  "leeaakaan" 72
  "wdrdwg"    0
  "00"        0
  "jep"       2)
