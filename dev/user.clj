(ns user
  (:require [portal.api :as portal]))

(portal/open)

(add-tap #'portal/submit)


