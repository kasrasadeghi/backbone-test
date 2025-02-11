; CMD: bbfmt ./tmp/if-expr-until-typeinfer.texp
; FILENAME: "./tmp/if-expr-until-typeinfer.texp"

(decl @puts (types i8*) i32)

(def @main (params (%argc i32) (%argv i8**)) i32(do 
  (let %$0 (< i32 %argc 3))
  (if %$0 (do 
    (call @puts (types i8*) i32 (args (str-get 0)))
  ))
  (let %$1 (>= i32 %argc 3))
  (if %$1 (do 
    (call @puts (types i8*) i32 (args (str-get 1)))
  ))
  (return (i32 0))
))

(str-table (0 "Hello World!\00") (1 "Goodbye World!\00"))
