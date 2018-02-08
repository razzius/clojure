(require '[clojure.tools.nrepl.server :refer [start-server stop-server]])
(require '[clojure.repl :refer [doc source]])

(defonce server (start-server :port 7888))

(clojure.main/repl)
