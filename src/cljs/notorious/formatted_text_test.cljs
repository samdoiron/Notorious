(ns notorious.formatted-text-test
  (:require [notorious.formatted-text :refer [parse]]
            [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest parse-bold
  (is (= [{:plain ["Tobi says to ", {:bold "ship"} " boldly"]}]
         (parse "Tobi says to *ship* boldly" ""))))

(run-tests)
