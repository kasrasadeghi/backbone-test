; CMD: bbfmt ./tmp/hello-until-typeinfer.texp
; FILENAME: "./tmp/hello-until-typeinfer.texp"

(decl @puts (types i8*) i32)

(def @main params i32(do 
  (call @puts (types i8*) i32 (args (str-get 0)))
  (return 0 i32)
))

(str-table (0 "Hello World!\00"))
