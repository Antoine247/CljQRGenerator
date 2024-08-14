(ns antoine247.handler
  (:use [ring.adapter.jetty]
        [ring.util.response]
        [reitit.interceptor.sieppari]
        [ring.middleware.keyword-params]
        [ring.middleware.params]
        [antoine247.zebra :as zebra]
        [reitit.ring :as ring]
        [reitit.core :as r]
        [muuntaja.interceptor]
        [reitit.http :as http]))

(r/match-by-path router "/ping")
(r/match-by-path router "/zebra")

(defn handler
  [{params :params}]
  (ring.util.response/content-type
   (ring.util.response/response (zebra/generar-qr params))
                                   "text/plain"))

(def router
  (ring/router
   ["/ping" {:get app-handler}]
   ["/zebra" {:post newhandler}]))


(defn newhandler [_]
  {:status 200, :body "ok"})



(def app (ring/ring-handler router (ring/create-default-handler)))




(def app-handler
  (-> handler
      (wrap-params {:encoding "UTF-8"})))

(comment
  (app {:request-method :post, :uri "/zebra"})
  (app {:request-method :get :uri "/ping" :params{:n 2311}})
  )
                     

