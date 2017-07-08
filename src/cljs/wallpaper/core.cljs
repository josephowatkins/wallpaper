(ns wallpaper.core
  (:require [wallpaper.wallpaper :as wallpaper]
            [reagent.core :as reagent :refer [atom]]))


(defn home-page []
  [:div.container
   [:div.mt-3.mb-3
    [:h3.display-3.mb-3 "Wallpaper"]
    [:p.text-muted.small "from " [:a {:href "https://www.amazon.co.uk/d/cka/New-Turing-Omnibus-K-Dewdney/0805071660" :target "_blank"} "The New Turing Omnibus"]]]
   [wallpaper/wallpaper-container "wallpaper"]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
