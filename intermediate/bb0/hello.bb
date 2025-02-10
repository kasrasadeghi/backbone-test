; CMD: bbfmt ./tmp/hello-until-typeinfer.texp
; FILENAME: "./tmp/hello-until-typeinfer.texp"

(decl @puts (types i8*) i32)

(def @main params i32(do 
  (call @puts (types i8*) i32 (args (str-get 0)))
  (return (i32 0))
))

(str-table (0 "Hello World!\00"))
