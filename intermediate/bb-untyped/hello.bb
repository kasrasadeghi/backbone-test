; CMD: bbfmt ./tmp/hello-until-normalize.texp
; FILENAME: "./tmp/hello-until-normalize.texp"

(decl @puts (types i8*) i32)

(def @main params i32(do 
  (call @puts (args (str-get 0)))
  (return 0)
))

(str-table (0 "Hello World!\00"))
