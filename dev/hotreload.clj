(ns hotreload
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [antoine247.handler :refer [app-handler]])
  (:gen-class))
#_(def dev-handler
  (wrap-reload #'handler))
#_(defn -main [& args]
  (run-jetty dev-handler {:port 13000}))