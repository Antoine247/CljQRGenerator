(ns antoine247.cljqrgenerator
  (:use [ring.adapter.jetty]
        [antoine247.handler :as handler]) 
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  #_(run-jetty handler/app-handler {:port 3000}))



(comment
  (.doStop server)
  (.start server)
  (defonce server (run-jetty handler/app {:port 3000 :join? false})))