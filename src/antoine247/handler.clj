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
             [reitit.ring.middleware.muuntaja :refer [format-middleware]]))

 (def regex-no-number #"^([^0-9]*)$")
 (s/def ::nombre (s/and spec/string? #(re-matches regex-no-number %)))
 (s/def ::apellido (s/and spec/string? #(re-matches regex-no-number %)))
 (s/def ::hc spec/int?)
 (s/def ::path-params (s/keys :req-un [::nombre ::apellido ::hc]))

 (defn default-200-request
   [body]
   {:status 200
    :body body
    :headers {"Content-Type" "text/plain"}})

 #_(defn handler
   [{params :params}]
 (if (contains? params :ip)
   (let [printer (zebra/imprimir-zebra (zebra/generar-qr params) (:ip params))]
     (if (nil? printer)
       (default-200-request "impresion completada")
       {:status 500
        :body printer
        :headers {"Content-Type" "text/plain"}}))
    (default-200-request (zebra/generar-qr params))))
 
 (defn handler
   [{params :params}]
   (cond
     (contains? params :ip)
     (if-let [printer (zebra/imprimir-zebra (zebra/generar-qr params) (:ip params))]
                              {:status 500
                               :body printer
                               :headers {"Content-Type" "text/plain"}}
                              (default-200-request "impresion completada"))
     :else (default-200-request (zebra/generar-qr params))))

 (defn default-qr-handler
   [_]
   (default-200-request "usar esta url en un post con los parametros nombre, apellido y hc"))

 (defn default-home-handler
   [_]
   (default-200-request "para usar este programa, enviar un post a la url 'qr' con los parametros nombre, apellido y hc"))

 (def exception-middleware
   (exception/create-exception-middleware 
    {::exception/default  (fn [trhowable _] {:status 400
                                             :body (str "error de validacion, revisar la variable " (-> trhowable
                                                                                                        ex-data
                                                                                                        :problems
                                                                                                        :clojure.spec.alpha/problems
                                                                                                        first
                                                                                                        :path
                                                                                                        first))
                                             :headers {"Content-Type" "text/plain"}})}))

 (def rutas
   (ring/router
    [["/" {:get default-home-handler}]
     ["/qr" {:post {:summary "respuesta"
                    :parameters {:query ::path-params}
                    :handler handler}
             :get default-qr-handler}]]
    {:exception pretty/exception
     :data {:middleware [exception-middleware
                         wrap-params
                         wrap-keyword-params
                         format-middleware
                         coercion/coerce-request-middleware
                         coercion/coerce-response-middleware]
            :coercion reitit.coercion.spec/coercion
            :muuntaja   muuntaja/instance}}))

(def app (ring/ring-handler rutas (ring/create-default-handler)))

(comment
  (app {:request-method :post, :uri "/zebra"})
  (tap> (app {:request-method :get :uri "/"})) 
  (tap> (app {:request-method :post :uri "/qr" :query-string  "nombre=asd&apellido=asdas&hc=123"}))
  (tap> (app {:request-method :post :uri "/qr" :query-string  "nombre=asd&apellido=asdas&hc=123&ip=123.1"}))
    (tap> (app {:request-method :post :uri "/qr" :query-string  "nombre=123&apellido=asdas&hc=123"}))
  (tap> (app {:request-method :get :uri "/qr"}))
  (handler {:request-method :get :uri "/qr"})
  (s/valid? ::apellido "123"))
                     

