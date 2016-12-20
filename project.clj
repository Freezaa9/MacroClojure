;(defproject macro "1.0.0-SNAPSHOT"
 ; :description "FIXME: write"
  ;:dependencies [[org.clojure/clojure "1.3.0"]])


(import java.io.FileReader java.io.File)

(fn safe [& var]
  (if (= (count var) 1)
    `(try
       ~@var
       (catch Exception e# (str e#)))
    `(try
       (let ~(first var)
         (try
           ~@(rest var)
           (catch Exception e# (throw e#))
           (finally
             (let [i# (first ~(first var))]
               (if (instance? java.io.Closeable i#)
                 (.close i#))))))))))


(safe (/ 15 3))
(safe (/ 10 0))
(safe [s (FileReader. (File. "file.txt"))] (.read s))
(safe [s (new FileReader (new File "C:\\Users\\Geoffrey-Port\\Desktop\\a.txt"))] (println (.read s) (.close s) (.read s)))
(safe [s (FileReader. (File. "missing-file"))] (. s read))
