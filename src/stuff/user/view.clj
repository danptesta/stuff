(ns stuff.user.view
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [h]]))

(defn new-user []
  (html5
   [:form.form-horizontal
    {:method "POST" :action "/users"}
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :first-name-input}
      "First Name"]
     [:div.col-sm-10
      [:input#first-name-input.form-control
       {:name :first-name
        :placeholder "First Name"}]]]
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :last-name-input}
      "Last Name"]
     [:div.col-sm-10
      [:input#last-name-input.form-control
       {:name :last-name
        :placeholder "Last Name"}]]]
    [:div.form-group
     [:label.control-label.col-sm-2 {:for :email-input}
      "Email"]
     [:div.col-sm-10
      [:input#email-input.form-control
       {:name :email
        :placeholder "Email"}]]]
    [:div.form-group
     [:div.col-sm-offset-2.col-sm-10
      [:input.btn.btn-primary
       {:type :submit
        :value "New User"}]]]]))

(defn users-page [users]
  (html5 {:lang :en}
         [:head
          [:title "Stuffmaster"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:link {:href "/bootstrap/css/bootstrap.min.css"
                  :rel :stylesheet}]]
         [:body
          [:div.container
           [:div.row
            [:h1 "Users"]
            (if (seq users)
              [:table.table.table-striped
               [:thead
                [:tr
                 [:th "First Name"]
                 [:th "Last Name"]
                 [:th "Email"]]]
               [:tbody
                (for [u users]
                  [:tr
                   [:td (h (:first_name u))]
                   [:td (h (:last_name u))]
                   [:td (h (:email u))]])]]
              [:div.col-sm-offset-1 "There are no users."])
            [:div.col-sm-6
             [:h2 "Create a new user"]
             (new-user)]]]
          [:script {:src "https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"}]
          [:script {:src "/bootstrap/js/bootstrap.min.js"}]]))
