(ns kernel-time.components.header
  (:require 
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]))

(defn header-close [app owner opts]
  "Widget to render buttons to min/max/close application"
  (reify
    om/IRender
    (render [_]
      (dom/nav #js {:className "btn-set linux"}
        (dom/button #js {:className "btn-os min"})
        (dom/button #js {:className "btn-os max"})
        (dom/button #js {:className "btn-os close"})))))

(defn header-fs [app owner opts]
  "Widget to render button for making app full screen"
  (reify
    om/IRender
    (render [_]
      (dom/nav #js {:className "btn-set fs-linux"}
               (dom/button #js {:className "btn-os fullscreen"}
                           (dom/i #js {:className "fa fa-expand"
                                       :style #js {:color "white"
                                                   :font-size "2em"}}))))))

(defn header [app owner opts]
  "Renders menu bar with close buttons"
  (reify
    om/IRender
    (render [_]
      (dom/header #js {:id "header"}
        (dom/h1 nil "Kernel Time header")
        (om/build header-close app)
        (om/build header-fs app)))))

