(ns stuff.item.handler
  (:require [stuff.item.model :refer [create-item
                                      read-items
                                      update-item
                                      delete-item]]
            [stuff.item.view :refer [items-page]]))

(defn handle-index-items [req]
  (let [db (:stuff/db req)
        items (read-items db)]
    {:status 200
     :headers {}
     :body (items-page items)}))

(defn handle-create-item [req]
  (let [name (get-in req [:params "name"])
        description (get-in req [:params "description"])
        db (:stuff/db req)
        item-id (create-item db name description)]
    {:status 302
     :headers {"Location" "/items"}
     :body ""}))
