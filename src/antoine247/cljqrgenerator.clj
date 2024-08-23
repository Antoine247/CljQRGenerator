(ns antoine247.cljqrgenerator
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [antoine247.handler :refer [app]]
            [mount.core :refer [defstate] :as mount])
  (:gen-class))
(defstate server :start (run-jetty app {:port (Integer/parseInt (System/getenv "cljqrport")) :join? false})
  :stop(.stop server))
(defn -main 
  [& args]
  (mount/start)
  (.addShutdownHook (Runtime/getRuntime) (Thread. mount/stop)))

