(ns antoine247.cljqrgenerator
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [antoine247.handler :as handler])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  #_(run-jetty handler/app-handler {:port 3000}))



(comment
  (defonce server (run-jetty handler/app {:port 3000 :join? false}))
  (.doStop server)
  (.start server)
  )