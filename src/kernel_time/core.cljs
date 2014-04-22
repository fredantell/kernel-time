(ns kernel-time.core
  (:require-macros [cljs.core.async.macros :refer [alt! go]])
  (:require #_[kernel-time.search :refer [search]]
            [kernel-time.server :as server]
            [kernel-time.components.header :refer [header]]
            [kernel-time.components.search :as search-comp]
            [kernel-time.components.movielist :as movie-list]
            [cljs.core.async :refer [<! chan put! sliding-buffer timeout]]
            [cljs.nodejs :as n]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)
;;(Redo UI to match omive type selection?)
;; Header
;; Catalog-Select
;; ..Search Input
;; ..Genre Select
;; ..Advanced Filter
;; ..Sort Results
;; Movie List
;; ..Individual Movie
;; Detailed Movie Info
;;
(def gui (n/require "nw.gui"))  ;; kernel_time.core.gui.Window.get().zoomLevel = 2
(def app-state (atom {:debug "testing..."}))

(defn debug-component [app owner opts]
  (reify
    om/IRender
    (render [_]
      (dom/div nil "hello"))))


(defn notification [app owner opts]
  "Renders any messages to user on startup")
(defn catalog-select [app owner opts]
  "Render container for all search elements on left sidebar")
(defn movie-list [app owner opts]
  "Render Container for list of search results")
(defn movie-info [app owner opts]
  "Render container for detailed movie info")

(defn page [app owner opts]
  "Render container for entire page"
  (reify
    om/IRender
    (render [_]
      (dom/div nil
        (om/build header app)
        (om/build search-comp/sidebar app)
        (om/build movie-list/movie-container app)))))

(om/root
 page
 app-state
 {:target (. js/document getElementById "app")})

