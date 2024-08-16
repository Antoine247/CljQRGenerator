(ns hotreload
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [antoine247.handler :refer [app-handler]]))

(def dev-handler
  (wrap-reload #'app-handler))

(defn correr-serv-dev []
  (run-jetty dev-handler {:port 13000}))

(comment 

  (correr-serv-dev)
  
  )

