(ns stuff.item.handler
  (:require [stuff.item.model :refer [create-item
                                      read-items
                                      update-item
                                      delete-item]]
            [stuff.user.model :refer [read-user]]
            [stuff.item.view :refer [items-page]]))

(defn handle-index-items [req]
  (let [db (:stuff/db req)
        userid (get-in req [:params :userid])
        user (read-user db userid)
        items (read-items db userid)]
    (println "===== userid: " userid)
    (println "===== user: " user)
    {:status 200
     :headers {}
     :body (items-page user items)}))

(defn handle-create-item [req]
  (let [userid (get-in req [:params :userid])
        name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        db (:stuff/db req)
        item-id (create-item db userid name description)]
    {:status 302
     :headers {"Location" (str "/users/" userid "/items")}
     :body ""}))
