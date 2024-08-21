(ns antoine247.zebra
  (:require  [selmer.parser :as parser])
  (:import (com.zebra.sdk.comm TcpConnection)
           (com.zebra.sdk.printer ZebraPrinterFactory)
           (com.zebra.sdk.comm ConnectionException)
           (com.zebra.sdk.printer ZebraPrinterLanguageUnknownException)
           (java.io IOException)))


(defn generar-qr
  "genera el codigo ZPL"
  [{:keys [nombre apellido hc] :as map}]
  (parser/render "^XA ^FO 10, 20^A0, 10^FD {{nombre}}^FS ^FO 10, 35^A0, 10^FD {{apellido}}^FS ^FO 10, 50^A0, 10^FD HC: {{hc}}^FS ^FO 160, 8^BQN,2,4,Q,7^FDH {{hc}}^FS ^XZ" map))

(defn imprimir-zebra
  "recibe codigo ZPL, conecta a una impresora zebra y envia comando de impresion"
  [zpl]
  (with-open [conn (TcpConnection/new
                    "192.168.1.32"
                    TcpConnection/DEFAULT_ZPL_TCP_PORT)]
    (try
      (.open conn)
      (-> (ZebraPrinterFactory/getInstance conn)
          (.printStoredFormat zpl nil))
      (catch ConnectionException e (ex-message e))
      (catch ZebraPrinterLanguageUnknownException e (ex-message e))
      (catch IOException e (ex-message e)))))


#_(comment
  (print (generar-qr {:nombre "Antoine" :apellido "Chamoun" :hc 3242123}))
  
  (TcpConnection/new
   "192.168.1.32"
   TcpConnection/DEFAULT_ZPL_TCP_PORT)
  
  (com.zebra.sdk.comm.Connection (com.zebra.sdk.comm.TcpConnection/new 
                                  "192.168.1.32" 
                                  com.zebra.sdk.comm.TcpConnection/DEFAULT_ZPL_TCP_PORT))
  
  (macroexpand '(defn imprimir-zebra
                  "recibe codigo ZPL, conecta a una impresora zebra y envia comando de impresion"
                  [zpl]
                  (with-open [conn (TcpConnection/new
                                    "192.168.1.32"
                                    TcpConnection/DEFAULT_ZPL_TCP_PORT)]
                    (try
                      (.open conn)
                      (-> (ZebraPrinterFactory/getInstance conn)
                          (.printStoredFormat zpl nil))
                      zpl
                      (catch ConnectionException e (.printStackTrace e))
                      (catch ZebraPrinterLanguageUnknownException e (.printStackTrace e))
                      (catch IOException e (.printStackTrace e))))))
  
  #_(imprimir-zebra (generar-qr {:nombre "Antoine" :apellido "Chamoun" :hc 3242123}))
  )









