(ns rest-demo.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.data.json :as json])
  (:gen-class))


; Simple Body page
(defn simple-body-page [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

; request-example
(defn request-example [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->>
          (pp/pprint req)
          (str "Request Object: " req))})

(defn hello-name [req] ;(3)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->
          (pp/pprint req)
          (str "Hello " (:name (:params req))))})



(defroutes app-routes
  (GET "/" [] simple-body-page)
  (GET "/request" [] request-example)
  (GET "/hello" [] hello-name)
  (route/not-found "Error, page not found!"))


(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.default middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults)
                       {:port port}
                       ; Run the server without ring defaults
                       ; (server/run-server #'app-routes {:port port})
                       (println(str "Running webserver at http://127.0.0.1:" port
                        "/")))))

