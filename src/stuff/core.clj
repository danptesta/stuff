(ns stuff.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.handler.dump :refer [handle-dump]]
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

(defn about [req]
  {:status 200
   :body "Welcome to the stuffmaster app, created by Dan Testa!  I'm using this app to explore the world of clojure."})

(defn yo [req]
  {:status 200
   :body (str "Yo " (get-in req [:params :name]) "!")})

(def ops
  {"+" +
   "-" -
   "*" *
   "divide" /})

(defn calc [req]
  (let [a (Integer. (get-in req [:params :a]))
        op (get-in req [:params :op])
        f (get ops op)
        b (Integer. (get-in req [:params :b]))]
    (if f
      {:status 200
       :body (str a " " op " " b " = " (f a b))
       :headers {}}
      {:status 404
       :body (str "Unknown operator: " op)
       :headers {}})))

(defroutes app
  (GET "/" [] greet)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request" [] handle-dump)
  (GET "/yo/:name" [] yo)
  (GET "/calc/:a/:op/:b" [] calc)
  (not-found "Sorry, page not found."))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))
