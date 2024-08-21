(ns user
  (:require [portal.api :as portal]))

(portal/open {:launcher :vs-code})

(add-tap #'portal/submit)


