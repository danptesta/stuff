(ns stuff.user.handler
  (:require [stuff.user.model :refer [create-user
                                      read-users
                                      update-user
                                      delete-user]]
            [stuff.user.view :refer [users-page]]))

(defn handle-index-users [req]
  (let [db (:stuff/db req)
        users (read-users db)]
    {:status 200
     :headers {}
     :body (users-page users)}))

(defn handle-create-user [req]
  (let [first-name (get-in req [:params "first-name"])
        last-name (get-in req [:params "last-name"])
        email (get-in req [:params "email"])
        db (:stuff/db req)
        user-id (create-user db first-name last-name email)]
    {:status 302
     :headers {"Location" "/users"}
     :body ""}))
