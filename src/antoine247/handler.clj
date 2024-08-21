(ns antoine247.handler
  (:require [antoine247.zebra :as zebra]
            [reitit.dev.pretty :as pretty]
            [reitit.ring :as ring]
            [reitit.core :as r]
            [spec-tools.spec :as spec]
            [reitit.coercion.spec]
            [clojure.spec.alpha :as s]
            [ring.adapter.jetty]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [reitit.ring.coercion :as coercion]
            [muuntaja.core :as muuntaja] 
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.muuntaja :refer [format-middleware]]
            [ring.util.response :as response]))

(s/def ::nombre spec/string?)
(s/def ::apellido spec/string?)
(s/def ::hc spec/int?)
(s/def ::path-params (s/keys :req-un [::nombre ::apellido ::hc]))

(defn handler
  [{params :params}]
  (response/response (zebra/generar-qr params)))

(defn default-qr-handler
  [request]
  (response/response (str request)))

(defn default-home-handler
  [request]
  (response/content-type
   (response/response (str (dissoc request :headers)))
   "text/plain"))

(def rutas
  (ring/router
   [[ "/" {:get default-home-handler}]
    ["/qr" {:post {:summary "respuesta"
                   :parameters {:query ::path-params}
                   :handler handler}
            :get default-qr-handler}]]
   {:exception pretty/exception
    :data {:middleware [wrap-params
                        wrap-keyword-params
                        format-middleware
                        coercion/coerce-exceptions-middleware
                        coercion/coerce-request-middleware
                        coercion/coerce-response-middleware
                        exception/create-exception-middleware]
           :coercion reitit.coercion.spec/coercion
           :muuntaja   muuntaja/instance}}))

 (def app (ring/ring-handler rutas (ring/create-default-handler)))

(comment
  (app {:request-method :post, :uri "/zebra"})
  (tap> (app {:request-method :get :uri "/"}))
  (tap> (app {:request-method :post :uri "/qr" :query-string  "nombre=antoine&apellido=chamoun&hc=sd"}))
  (tap> (app {:request-method :get :uri "/qr"}))
  (handler {:request-method :get :uri "/qr"})
  )
                     

