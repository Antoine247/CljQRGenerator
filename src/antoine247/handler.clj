(ns antoine247.handler
  (:use [ring.adapter.jetty]
        [ring.util.response :as response] 
        [ring.middleware.keyword-params]
        [ring.middleware.params]
        [antoine247.zebra :as zebra]
        [reitit.ring :as ring]))

(defn handler
  [{params :params :as request}]
  (tap> request) 
  (response/content-type
   (response/response (zebra/generar-qr params))
   "text/plain"))


(comment
  
  (defn handler2
    [{params :params}]
    (assoc (response/created "/" (zebra/generar-qr params)) :headers {"content-type" "text/plain"}))
  )

(def app-handler
  (-> handler
      (wrap-params {:encoding "UTF-8"})))

(def rutas
  (ring/router
   ["/qr" {:get app-handler}]))


(def app (ring/ring-handler rutas (ring/create-default-handler)))

(comment
  (app {:request-method :post, :uri "/zebra"})
  (tap> (app {:request-method :get :uri "/qr?nombre=Antoine&apellido=Chamoun&hc=1234231" :host "http://127.0.0.1"}))
  )
                     

