(ns stuff.core
  (:require [stuff.item.model :as items])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.handler.dump :refer [handle-dump]]
            [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]))

(def db "jdbc:postgresql://localhost/stuff")

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

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "Server"] "Stuffmaster 9000")))

(defn wrap-db [hdlr]
  (fn [req]
    (hdlr (assoc req :stuff/db db))))

(defroutes routes
  (GET "/" [] greet)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request" [] handle-dump)
  (GET "/yo/:name" [] yo)
  (GET "/calc/:a/:op/:b" [] calc)
  (not-found "Sorry, page not found."))

(def app
  (wrap-server
   (wrap-db
    (wrap-params
     routes))))

(defn -main [port]
  (items/create-table db)
  (jetty/run-jetty app
                   {:port (Integer. port)}))
