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

(defn generate-request-uri [baseuri quality rating]
  (str baseuri "?quality=" quality "&rating" rating "&")
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
    (println (str "Firing request to " url " via " method " and data - : " data-string))
                                        ; Fire request
    (.send request url method data-string headers)
    request))

(def built-uri
  (generate-request-uri yify-uri "1080p" "8"))

(defn test-request [call-back]
  (ajax built-uri "GET" "" #_println call-back))

#_(ajax request-uri "GET" "" println)

(defn store-response [response]
    (println "running parser fn" response)
    (let [x (.parse window/JSON response)]
      (println  "Parsed JSON!" x)))

(println "Test 1 how to parse")
(println "Testing how to parse2" (.parse window/JSON "{}"))
#_(println "Test3" (.))
(test-request store-response)
#_(ajax (generate-request-uri yify-uri "1080p" "8") "GET" "" println)
;; data.json
