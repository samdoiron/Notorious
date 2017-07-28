(ns notorious.actions
  (:require [notorious.session :as session]
            [clojure.string :as string]))

(defn append-key [prop key]
  (fn [note]
    (js/console.log key)
    (update-in note [prop] #(str % key))))
