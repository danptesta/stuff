(ns stuff.dev
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [stuff.core :as core]))

(defn -main [port]
  (jetty/run-jetty (wrap-reload #'core/app) {:port (Integer. port)}))
