(ns kernel-time.components.movielist
  (:require 
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]))
;;om/build-all for a movie-thumb component

(enable-console-print!)
(. js/console log "Printing works")
(println "Println works too")


(def mock-movie-list
  (atom {:movies 
         [{:movie "Shawshank" :src "http://zapp.trakt.us/images/posters_movies/223223-138.jpg?1"}
          {:movie "AmBeauty" :src "http://zapp.trakt.us/images/posters_movies/223223-138.jpg?1"}]}))

(defn movie-thumb [app owner opts]
  (reify
    om/IRender
    (render [_]
      (dom/li nil (:movie app)))))

(defn movie-thumb [app owner opts]
  (reify
    om/IRender
    (render [_]
      (dom/li #js {:id (str "movie-" "imdbcode")
                   :className "page"}
              (dom/a #js {:onClick #(. js/console log "child div clicked")}
                (dom/i #js {:className "fa fa-eye fa-3"})
                (dom/span #js {:className "cover"}
                  (dom/img #js {:className "real"
                                :src (:src app)}))
                (dom/strong (:movie app))
                (dom/small "2014"))))))

(defn movie-list [app owner opts]
  (reify
    om/IInitState
    (init-state [_]
      @mock-movie-list)
    om/IRenderState
    (render-state [_ state]
      (dom/div #js {:id "movie-list"
                    :className "page"}
        (. js/console log state)       
        (apply dom/ul #js {:className "movie-list"}
               (om/build-all movie-thumb (:movies state)))))))

(defn movie-container [app owner opts]
  (reify
    om/IRender
    (render [_]
      (dom/section #js {:className "container"}
        (dom/aside #js {:className "hidden"})
        (om/build movie-list app)))))



;; <section class="container">
;;   <sidebar class="hidden"></sidebar>
;;   <div id="movie-list" class="page" style="display: block;">
;;     <ul class="movie-list">

;;       <li id="movie-1800246" class="movie fullyLoaded loaded">
;;         <a href="javascript:;">
;;           <i class="fa fa-eye fa-3"></i>
;;           <span class="cover">
;;             <img src="http://zapp.trakt.us/images/posters_movies/223223-138.jpg?1"
;;                  class="real" alt="That Awkward Moment">
;;           </span>
;;           <strong>That Awkward Moment</strong>
;;           <small>2014</small>
;;         </a>
;;       </li>

;;       </ul>
;;     </div>
;; </section>
