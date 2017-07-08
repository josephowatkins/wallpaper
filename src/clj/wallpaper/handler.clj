(ns wallpaper.handler
  (:require [wallpaper.middleware :refer [wrap-middleware]]
            [compojure.core :refer [defroutes]]
            [compojure.route :refer [not-found resources]]))


(defroutes routes
  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'routes))
