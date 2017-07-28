(ns notorious.views
    (:require [notorious.session :as session]
              [notorious.actions :as actions]))

(defn event->key [event]
  (-> event (.-key)))

(defn section
  [note]
  [:section {:key (note :_id)}
    [:input.noto-input.section__title
      {:type "text"
      :placeholder "Title"
      :value (:title note)
      :on-key-down #(session/update-note note (actions/append-key :title (event->key %)))
      }]
    [:textarea.section__body
      {
        :on-key-down #(session/update-note note (actions/append-key :body (event->key %)))
        :value (note :body)
      }
      ]])

(defn sections
  [notes]
  [:div.sections (map section notes)])

(defn home-page []
  (js/console.log "%cDRAW" "background:purple; color:white" "home-page" (clj->js @session/notes))
  [sections @session/notes])
