(ns notorious.core
    (:require [reagent.core :as reagent]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [notorious.views :as views]))

;; -------------------------
;; Routes

(def page (reagent/atom #'views/home-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'views/home-page))

;; -------------------------
;; Initialize app

;; Ensure correct route is rendered after a figwheel reload
(accountant/dispatch-current!)

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))

