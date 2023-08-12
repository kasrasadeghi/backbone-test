; CMD: bbfmt ./tmp/if-expr-until-str.texp
; FILENAME: "./tmp/if-expr-until-str.texp"

(decl @puts (types i8*) i32)

(def @main (params (%argc i32) (%argv i8**)) i32(do 
  (if (< %argc 3) (do 
    (call @puts (args (str-get 0)))
  ))
  (if (>= %argc 3) (do 
    (call @puts (args (str-get 1)))
  ))
  (return 0)
))

(str-table (0 "Hello World!\00") (1 "Goodbye World!\00"))
