(ns kernel-time.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.events :as ge]
            [goog.Uri]
            [goog.net.EventType :as gevt])
  (:import [goog.net XhrIo]
           [goog.async Deferred]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"}))

(om/root
  (fn [app owner]
    (dom/h1 nil (:text app)))
  app-state
  {:target (. js/document (getElementById "app"))})

(def request-uri "http://yts.re/api/list.json?quality=3D&rating=8")

(defn make-a-request [uri]
  )

(defn ajax [url method data-string success & [error headers]]
  (let [request (XhrIo.)
        d (goog.async.Deferred.)
        listener-id (ge/listen request gevt/COMPLETE (fn [response]
                                                       (let [target (.-target response)
                                                             data (if (= method "DELETE")
                                                                    nil
                                                                    (.getResponseJson target ))]
                                                         (.callback d data))))]
                                        ; Setup deferred callbacks
    (.addErrback d  (fn [error] (.log js/console "Error on ajax: " error)))
    (when success (.addCallback d #(success (js->clj % :keywordize-keys true))))
    (when error (.addErrback d error))
    (.addBoth d (fn [value] (ge/unlistenByKey listener-id) (.dispose request)))
    (mprint (str "Firing request to " url " via " method " and data - : " data-string))
                                        ; Fire request
    (.send request url method data-string headers)
    request))
