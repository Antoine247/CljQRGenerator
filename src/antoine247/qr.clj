(ns antoine247.qr
  (:import net.glxn.qrgen.QRCode
           net.glxn.qrgen.vcard.VCard))


(QRCode/from "string")




(comment
  (def card (-> (VCard/new  "antoine")
           (.setAddress "chamoun863")))
  
  (-> (QRCode/from card)
      .file)
  )