(ns ^:figwheel-no-load notorious.dev
  (:require
    [notorious.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
