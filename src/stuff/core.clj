(ns stuff.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]))

(defn greet [req]
  {:status 200
   :body "Hola, World!"
   :headers {}})

(defn goodbye [req]
  {:status 200
   :body "Hasta la vista, baby!"
   :headers{}})

(defroutes app
  (GET "/" [] greet)
  (GET "/goodbye" [] goodbye)
  (not-found "Sorry, page not found."))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))
