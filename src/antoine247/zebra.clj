(ns antoine247.zebra
  (:require  [selmer.parser :as parser]))


(defn generar-qr
  "genera el codigo ZPL"
  [{:keys [nombre apellido historia] :as map}]
  (parser/render "^XA ^FO 10, 20^A0, 10^FD {{nombre}}^FS ^FO 10, 35^A0, 10^FD {{apellido}}^FS ^FO 10, 50^A0, 10^FD HC: {{historia}}^FS ^FO 160, 8^BQN,2,4,Q,7^FDH {{historia}}^FS ^XZ" map))

(print (generar-qr {:nombre "Antoine" :apellido "Chamoun" :historia 3242123}))







