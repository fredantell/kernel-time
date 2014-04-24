(ns kernel-time.components.search
  (:require 
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]))

;; search input

;; film categories (query hash-map)

;; Advanced Filters
  ;; Rotten tomato Rating
  ;; Torrent Health
  ;; Year Released
;; Sort Results by
  ;; Rating
  ;; Health
  ;; Year

(def list-of-genres
  (atom {:genres
         [{:all "Popular"}
          {:action "Action"}
          {:adventure "Adventure"}
          {:animation "Animation"}
          {:biography "Biography"}
          {:comedy "Comedy"}
          {:crime "Crime"}
          {:documentary "Documentary"}
          {:drama "Drama"}
          {:family "Family"}
          {:fantasy "Fantasy"}
          {:film-noir "Film-Noir"}
          {:history "History"}
          {:horror "Horror"}
          {:music "Music"}
          {:musical "Musical"}
          {:mystery "Mystery"}
          {:romance "Romance"}
          {:sci-fi "Sci-Fi"}
          {:short "Short"}
          {:sport "Sport"}
          {:thriller "Thriller"}
          {:war "War"}
          {:western "Western"}]}))

(defn genre-item [app owner opts]
  (reify
    om/IRender
    (render [_]
      (dom/li nil
              (dom/a nil (first (vals app)))))))

(defn genre-list [app owner opts]
  "Render list of movie genres"
  (reify
    om/IInitState
    (init-state [_]
      @list-of-genres)
    om/IRenderState
    (render-state [_ state]
      (dom/div nil
        (dom/h4 nil "Movies")
        (apply dom/ul #js {:className "categories"}
               (om/build-all genre-item (:genres state)))))))

(defn search-input [app owner opts]
  "Render the search field and icon"
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:className "search"}
          (dom/input #js {:type "text"
                          :placeholder "Search"})
          (dom/i #js {:className "fa fa-search"})))))

(defn sidebar [app owner opts]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:id "catalog-select"}
        (om/build search-input app)
        (om/build genre-list app)))))

;; <div id="catalog-select">
;;       <div class="search">
;;         <input type="text" placeholder="Search" data-translate="search">
;;         <i class="fa fa-search"></i>
;;       </div>

;;       <h4 data-translate="movies">Movies</h4>
;;       <ul class="categories"><li class="active"><a href="#" data-genre="all">Popular</a></li><li><a href="#" data-genre="action">Action</a></li><li><a href="#" data-genre="adventure">Adventure</a></li><li><a href="#" data-genre="animation">Animation</a></li><li><a href="#" data-genre="biography">Biography</a></li><li><a href="#" data-genre="comedy">Comedy</a></li><li><a href="#" data-genre="crime">Crime</a></li><li><a href="#" data-genre="documentary">Documentary</a></li><li><a href="#" data-genre="drama">Drama</a></li><li><a href="#" data-genre="family">Family</a></li><li><a href="#" data-genre="fantasy">Fantasy</a></li><li><a href="#" data-genre="film-noir">Film-Noir</a></li><li><a href="#" data-genre="history">History</a></li><li><a href="#" data-genre="horror">Horror</a></li><li><a href="#" data-genre="music">Music</a></li><li><a href="#" data-genre="musical">Musical</a></li><li><a href="#" data-genre="mystery">Mystery</a></li><li><a href="#" data-genre="romance">Romance</a></li><li><a href="#" data-genre="sci-fi">Sci-Fi</a></li><li><a href="#" data-genre="short">Short</a></li><li><a href="#" data-genre="sport">Sport</a></li><li><a href="#" data-genre="thriller">Thriller</a></li><li><a href="#" data-genre="war">War</a></li><li><a href="#" data-genre="western">Western</a></li></ul>
;;     </div>
