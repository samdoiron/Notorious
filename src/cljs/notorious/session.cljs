(ns notorious.session
  (:require [reagent.core :as reagent]
            [alandipert.storage-atom :refer [local-storage]]
            [cljs.core.async :as async :refer [chan >! <!]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(def notes (reagent/atom []))

(def note-actions (chan))

(def remote-db (new js/PouchDB "http://localhost:5984/notorious"))
(def local-db (new js/PouchDB "notorious-local"))

(defn replicate-from [local-db remote-db]
  (-> local-db (.-replicate) (.from remote-db)))

(defn create-field-index [db name]
  (.createIndex db #js {
    :index #js {:fields #js [name]}
  }))

(defn promise->chan [prom]
  (let [pchan (chan)]
    (-> prom (.then #(go (>! pchan %))))
    pchan))

(defn- find-by [prop value]
  (-> local-db
    (.find #js {
      :selector (js-obj
        prop #js {:$eq value}
      )})
    (.then #(js->clj (.-docs %) :keywordize-keys true))
    (.then (fn [result]
      (js/console.log "%cQUERY" "background-color: cyan" "find-by" prop "=" value (clj->js result))
      result))
    (promise->chan)
  ))

(defn >promise [chan prom]
  (.then prom #(>! chan %)))

(defn all-notes []
  (find-by "type" "note"))

(defn- refresh-notes []
  (go
    (reset! notes (<! (all-notes)))))

(defn- find-id [db id]
  (-> db
    (.get id)
    (.then #(js->clj % :keywordize-keys true))
    (promise->chan)))

(defn- latest-rev [db record]
  (find-id db (record :_id)))

;; Keep notes up to date
(go-loop []
  (let [[note action] (<! note-actions)
        current-note (<! (latest-rev local-db note))]
    (.put local-db (clj->js (action note)))
    (refresh-notes))
    (recur))

(defn update-note [note action]
  (go (>! note-actions [note action])))

;; Setup db
(go
  (refresh-notes)
  (<! (promise->chan (replicate-from local-db remote-db)))
  (create-field-index local-db "type")
  (refresh-notes))
