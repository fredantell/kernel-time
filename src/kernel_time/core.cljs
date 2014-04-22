(ns kernel-time.core
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require [kernel-time.search :refer [search]]
            [kernel-time.server :as server]
            [cljs.core.async :refer [<! chan put! sliding-buffer timeout]]
            [cljs.nodejs :as n]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

