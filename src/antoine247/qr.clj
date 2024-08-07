(ns antoine247.qr
  (:require [clojure.java.io :as io]
            [babashka.fs :as fs])
  (:import net.glxn.qrgen.QRCode))

(QRCode/from "string")
(doto (^[java.lang.String] QRCode/from "string") 
  .file)