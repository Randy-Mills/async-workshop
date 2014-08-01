;
; Copyright © 2014 Daniel Solano Gómez.
;
; This program is provided under the terms of the Eclipse Public License 1.0
; <http://www.eclipse.org/org/documents/epl-v10.html>.  Any use, reproduction,
; or distribution of this program constitutes recipient's acceptance of this
; license.
;

(ns async-workshop.chat-demo.client
  (:require [async-workshop.chat-demo.client.input :refer [chat-input-widget]]
            [async-workshop.chat-demo.client.web-socket :refer [ws-widget]]
            [clojure.browser.repl]
            [cljs.core.async :as async]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(def app-state (atom {:chat-history []
                      :transmit-channel (async/chan)}))

(defn chat-history-widget
  [app-state owner]
  (reify
    om/IRender
    (render [_]
      (apply dom/ul nil
             (map #(dom/li nil %)
                  (:chat-history app-state))))
    om/IDisplayName
    (display-name [_] "Chat history renderer")))

(om/root chat-history-widget app-state
  {:target (.getElementById js/document "chatHistoryWidget")})

(om/root chat-input-widget app-state
  {:target (.getElementById js/document "chatInputWidget")})

(om/root ws-widget app-state
  {:target (.getElementById js/document "socketWidget")})
