(ns wallpaper.wallpaper
  (:require [reagent.core :as reagent :refer [atom]]))

(defn value [e] (-> e .-target .-value))

(defn fill-style [ctx colour]
  (set! (.-fillStyle ctx) colour)
  ctx)

(defn fill-rect [ctx x y width height]
  (.fillRect ctx x y width height)
  ctx)

(defn reset-canvas [ctx]
  (-> ctx
      (fill-style "rgb(256,256,256)")
      (fill-rect 0 0 300 300)))

(defn draw-wallpaper [id size]
  (let [canvas (.getElementById js/document id)
        ctx    (.getContext canvas "2d")
        a 1
        b 1
        step 2]
    (reset-canvas ctx)
    (doseq [i (range 1 300)
            j (range 1 300)
            :let [x (+ a (* i (/ size 150)))
                  y (+ b (* j (/ size 150)))
                  c (int (+ (* x x) (* y y)))]]
      (when (zero? (mod c 3))
        (-> ctx
            (fill-style "rgb(100,25,25")
            (fill-rect (* step  i) (* step j) 2 2)))
      (when (zero? (mod c 2))
        (-> ctx
            (fill-style "rgb(200,50,50")
            (fill-rect (* step  i) (* step j) 2 2))))))

(defn canvas [id width height]
  (reagent/create-class
   {:component-did-mount #(draw-wallpaper id 0)
    :render (fn []
              [:canvas
               {:id "wallpaper"
                :width (str width)
                :height (str height)}])}))

(defn wallpaper-container [id]
  [:div.wallpaper-container
   [:div.wallpaper-container__canvas
    [canvas id 300 300]]
   [:div.wallpaper-container__slider
    [:input
     {:type "range"
      :min "0"
      :max "100"
      :default-value "0"
      :step "1"
      :on-change #(draw-wallpaper id (value %))}]]])
