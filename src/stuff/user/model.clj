(ns stuff.user.model
  (:require [clojure.java.jdbc :as db]))

(defn create-table [db]
  (db/execute!
   db
   ["CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\""])
  (db/execute!
   db
   ["CREATE TABLE IF NOT EXISTS users
       (id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
        first_name TEXT NOT NULL,
        last_name TEXT NOT NULL,
        email TEXT NOT NULL,
        active BOOLEAN NOT NULL DEFAULT TRUE,
        date_created TIMESTAMPTZ NOT NULL DEFAULT now())"]))

(defn create-user [db first-name last-name email]
  (:id (first (db/query
               db
               ["INSERT INTO users (first_name, last_name, email)
                 VALUES (?, ?, ?)
                 RETURNING id"
                first-name
                last-name
                email]))))

(defn update-user [db id active]
  (= [1] (db/execute!
          db
          ["UPDATE users
            SET active = ?
            WHERE id = ?"
           active
           id])))

(defn delete-user
  "Delete an existing user."
  [db id]
  (= [1] (db/execute!
          db
          ["DELETE FROM users
            WHERE id = ?"
           id])))

(defn read-users
  "Read in all users in the database."
  [db]
  (db/query
   db
   ["SELECT id, first_name, last_name, email, active, date_created
     FROM users
     ORDER BY date_created"]))

(defn read-user
  "Read in the user with the given id."
  [db id]
  (first
   (db/query
     db
     ["SELECT id, first_name, last_name, email, active, date_created
       FROM users
       WHERE id = ?"
      (java.util.UUID/fromString id)])))
