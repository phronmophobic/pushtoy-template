(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]

                 [crate "0.2.4"]
                 [hiccup "1.0.3"]
                 [org.clojure/core.async "0.1.0-SNAPSHOT"]
                 [http-kit "2.1.11"]
                 [com.cemerick/piggieback "0.1.0"]
                 [com.datomic/datomic-free "0.8.4020.26"]

                 [compojure "1.1.6"]]
  :plugins [[lein-ring "0.8.8"]
            [lein-cljsbuild "0.3.2"]
            [lein-pushtoy "0.1.3-SNAPSHOT"]]
  :cljsbuild { 
    :builds {
      :main {
        :source-paths ["src"]
        :compiler {:output-to "resources/public/js/cljs.js"
                   :optimizations :simple
                   :pretty-print true}
        :jar true}}}
  :repl-options {
                 :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]
               :init (do
                       (use '[clojure.pprint :only [pprint]])
                       (use 'clojure.repl))}
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}
  :ring {:handler {{name}}.handler/app}
  :pushtoy {:ips ["192.241.212.75"]
;;            :port "fixme"
;;            :server-name "fixme
            }
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
