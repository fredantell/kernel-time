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

(def yify-uri "http://yts.re/api/list.json")
(def uri-1 "http://yts.re/api/list.json?limit=1")
(def uri-2 "http://yts.re/api/list.json")
(def uri-3 "http://yts.re/api/list.json")
(def uri-4 "http://yts.re/api/list.json")
(def uri-5 "http://yts.re/api/list.json")

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
    (println (str "Firing request to " url " via " method " and data - : " data-string))
                                        ; Fire request
    (.send request url method data-string headers)
    request))

(println "Test 1")
(println "Testing how to parse2" (type  ( js->clj (.parse js/JSON "{}"))))
(println "Test3")
(ajax uri-1 "GET" "" #(.dir js/console (.parse js/JSON %)))
;; (test-request store-response)
;; #_(ajax (generate-request-uri yify-uri "1080p" "8") "GET" "" println)


;; #_(ajax request-uri "GET" "" println)

;; (defn store-response [response]
;;     (println "running parser fn" response)
;;     (let [x (.parse window/JSON response)]
;;       (println  "Parsed JSON!" x)))

;; data.json





;; (defn generate-request-uri [baseuri quality rating]
;;   (str baseuri "?quality=" quality "&rating" rating "&")
;;     )
;; (def built-uri
;;   (generate-request-uri yify-uri "1080p" "8"))
;; (defn test-request [call-back]
;; (ajax built-uri "GET" "" #_println call-back))
