(ns leiningen.new.pushtoy
  (:use [leiningen.new.templates :only [renderer sanitize year ->files]]
        
        [leinjacker.utils :only [lein-generation]])
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def project-file
  (if (= (lein-generation) 2)
    "project_lein2.clj"
    "project_lein1.clj"))


(defn binary [file]
  (io/input-stream (io/resource (str/join "/" ["leiningen" "new" "pushtoy" file]))))



(defn pushtoy
  "Create a new Compojure project"
  [name]
  (let [data {:name name
              :sanitized (sanitize name)
              :year (year)}
        render #((renderer "pushtoy") % data)]
    
    (->files data
             [".gitignore"  (render "gitignore")]
             ["project.clj" (render project-file)]
             ["README.md"   (render "README.md")]
             ["src/{{sanitized}}/handler.clj"       (render "handler.clj")]
             ["test/{{sanitized}}/test/handler.clj" (render "handler_test.clj")]

             ["resources/public/css/bootstrap.min.css" (binary "resources/public/css/bootstrap.min.css")]
             ["resources/public/js/bootstrap.min.js" (binary "resources/public/js/bootstrap.min.js")]
             ["resources/public/index.html" (binary "resources/public/index.html")])
    ))


