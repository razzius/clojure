(ns user)

(use '[clojure.tools.nrepl.server :only (start-server stop-server)])
(defonce server (start-server :port 7888))

(doc clojure.main/repl)

(clojure.main/repl)

(defn get-clipboard []
  (.getSystemClipboard (java.awt.Toolkit/getDefaultToolkit)))

(defn slurp-clipboard []
  (try
    (.getTransferData (.getContents (get-clipboard) nil) (java.awt.datatransfer.DataFlavor/stringFlavor))
    (catch java.lang.NullPointerException e nil)))

(defn spit-clipboard [text]
  (.setContents (get-clipboard) (java.awt.datatransfer.StringSelection. text) nil))

;; (defn razzi-repl-read [a b]
;;   (clojure.main/repl-read))


(doc clojure.main/repl-read)
(source clojure.main/repl-read)

(or ({:line-start request-prompt :stream-end request-exit}
     (skip-whitespace *in*))
  (let [input (read {:read-cond :allow} *in*)]
    (skip-if-eol *in*)
    input))


(require '[clojure.main :refer [skip-whitespace skip-if-eol]])

(defn razzi-repl-read
  "Default :read hook for repl. Reads from *in* which must either be an
  instance of LineNumberingPushbackReader or duplicate its behavior of both
  supporting .unread and collapsing all of CR, LF, and CRLF into a single
  \\newline. repl-read:
    - skips whitespace, then
      - returns request-prompt on start of line, or
      - returns request-exit on end of stream, or
      - reads an object from the input stream, then
        - skips the next input character if it's end of line, then
        - returns the object.

  My version inserts outer parentheses!
  "
  [request-prompt request-exit]
  (or ({:line-start request-prompt :stream-end request-exit}
       (skip-whitespace *in*))
    (let [input (read {:read-cond :allow} *in*)]
      (skip-if-eol *in*)
      (println input)
      (format "(%s)" input))))

({:line-start :request-prompt :stream-end :request-exit}
 (clojure.main/skip-whitespace " hiii "))

(apropos "skip-whitespace")
(clojure.main/skip-whitespace " hiii ")

(require '[clojure.java.io :refer [reader]])
(reader "a")

(apropos "StringR")

(import '[java.io StringReader PushbackReader])

(clojure.main/skip-whitespace (StringReader. " hiii "))

(StringReader. " hiii ")

(-> "hii" StringReader. .read)
(doc clojure.main/skip-whitespace)

"The stream must either be an
  instance of LineNumberingPushbackReader or duplicate its behavior"

(apropos "LineNumberingPushbackReader")

(find-doc "LineNumberingPushbackReader")

; not found
(source *in*)
(doc source)

; is there a way to do this?
(source clojure.core)
clojure.lang.LineNumberingPushbackReader

(clojure.main/skip-whitespace (clojure.lang.LineNumberingPushbackReader. (StringReader. " hiii ")))

(doc clojure.main/skip-whitespace)

(import '[clojure.lang LineNumberingPushbackReader])
(clojure.main/skip-whitespace (LineNumberingPushbackReader. (StringReader. "hiii")))
; :body

(clojure.main/skip-whitespace (LineNumberingPushbackReader. (StringReader. "")))
; :stream-end

({:a 1 :b 2} :a)

(defn razzi-repl-read
  "My version inserts outer parentheses!"
  [request-prompt request-exit]
  (println "HI"))

; (clojure.main/repl :repl razzi-repl-read) ; could have warned me I was passing a bogus keyword
(defn razzi-repl-read
  "Default :read hook for repl. Reads from *in* which must either be an
  instance of LineNumberingPushbackReader or duplicate its behavior of both
  supporting .unread and collapsing all of CR, LF, and CRLF into a single
  \\newline. repl-read:
    - skips whitespace, then
      - returns request-prompt on start of line, or
      - returns request-exit on end of stream, or
      - reads an object from the input stream, then
        - skips the next input character if it's end of line, then
        - returns the object.

  My version inserts outer parentheses!
  "
  [request-prompt request-exit]
  (or ({:line-start request-prompt :stream-end request-exit}
       (skip-whitespace *in*))
    (let [input (read-line)]
      ; todo if input has spaces and no () then add them
      (read (PushbackReader. (StringReader. (format "(%s)" input)))))))


(use '[clojure.repl :refer [doc source]])

(source clojure.main/repl-prompt)

;; don't eval this in emacs - actually copy to repl
(clojure.main/repl :read razzi-repl-read :prompt #(print "raz> "))

;; looks like read is only taking 1 form. Gotta change to take whole line
(doc read)

;; todo some shortcut to doc symbol at point
(doc read-line)

(source read)

;hacky but works!
(read (PushbackReader. (StringReader. (format "(%s)" "+ 1 2"))))
