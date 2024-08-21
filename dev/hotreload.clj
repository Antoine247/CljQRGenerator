(ns hotreload
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [antoine247.handler :refer [app]]))

(def dev-handler
  (wrap-reload #'app))

(defn correr-serv-dev []
   (run-jetty dev-handler {:port 13000 :join? false}))


(comment 

  (correr-serv-dev)
  )

