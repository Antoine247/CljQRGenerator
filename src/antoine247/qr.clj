(ns antoine247.qr
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs])
  (:import net.glxn.qrgen.QRCode
           net.glxn.qrgen.vcard.VCard))


(QRCode/from "string")




(comment
  (def card (-> (VCard/new  "antoine")
           (.setAddress "chamoun863")))
  
  (-> (QRCode/from card)
      .file)
  )