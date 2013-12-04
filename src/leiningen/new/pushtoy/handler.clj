(ns {{name}}.handler
  (:use compojure.core)
  (:use org.httpkit.server
         [compojure.handler :only [site]]
         [compojure.core :only [defroutes GET POST DELETE ANY context]])
  (:require
   clojure.pprint
   [ring.util.response :as resp]
            [hiccup.core :as hiccup]
            [compojure.route :as route]
            [ring.middleware.resource :as resources]
            [clojure.java.io :as io])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defn css-link [href]
  [:link {:rel "stylesheet"
          :type "text/css"
          :href href
          }])

(defn render-app [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body
   (hiccup/html
    [:html {:xmlns "http://www.w3.org/1999/xhtml"}
     [:head
      [:title "ZAR!"]
      #_(css-link "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css")
      (css-link "/css/bootstrap.min.css")]
     [:body

      #_[:script {:src "http://www.google.com/jsapi"}]
      [:script {:src "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"
                :type "text/javascript"}]
      #_[:script {:src "/js/jquery.min.js"
                :type "text/javascript"}]
      ;; [:script "google.load(\"jquery\", \"1.3\");"]
  ;;     goog.require ('goog.dom.query');
  ;; goog.require ('goog.style');
      #_[:script {:src "http://jquery-json.googlecode.com/files/jquery.json-2.2.min.js"}]
      #_[:script {:type "text/javascript"
                :src "/js/bootstrap.min.js"}]
      [:script {:type "text/javascript"
                :src "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"}]
      [:script {:type "text/javascript"
                :src "js/cljs.js"}]]])})

(defn websocket [req]
  
  (clojure.pprint/pprint req)
  (with-channel req channel              ; get the channel
    ;; communicate with client using method defined above

    (on-close channel (fn [status]
                        (println "channel closed")))
    (if (websocket? channel)
      (println "WebSocket channel")
      (println "HTTP channel"))
    (on-receive channel (fn [data]       ; data received from client
           ;; An optional param can pass to send!: close-after-send?
           ;; When unspecified, `close-after-send?` defaults to true for HTTP channels
           ;; and false for WebSocket.  (send! channel data close-after-send?)

              ;(send! channel data)
              ))))
(defroutes all-routes
  ;(GET "/" [] handler)
  (GET "/" [] (resp/resource-response "index.html" {:root "public"}
                                  ))
  (GET "/websocket" [] websocket)     ;; websocket
  (route/resources "/") ;; static file url prefix /static, in `public` folder
  (route/not-found "Not Found"))



(def app
  (handler/site all-routes))

(defn -main []
  (run-server (site #'all-routes) ;{:port 3000}
              ))
