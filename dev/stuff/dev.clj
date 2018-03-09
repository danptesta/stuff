(ns stuff.dev
  (:require [stuff.item.model :as items]
            [stuff.user.model :as users])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [stuff.core :as core]))

(defn -main [port]
  (items/create-table core/db)
  (users/create-table core/db)
  (jetty/run-jetty (wrap-reload #'core/app) {:port (Integer. port)}))
