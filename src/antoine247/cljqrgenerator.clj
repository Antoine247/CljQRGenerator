(ns antoine247.cljqrgenerator
  (:use [ring.adapter.jetty]
        [antoine247.handler :as handler]) 
  (:gen-class))

(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty app-handler {:port 3000}))

(defonce server (run-jetty handler/app {:port 3000 :join? false}))