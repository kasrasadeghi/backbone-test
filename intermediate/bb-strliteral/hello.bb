; CMD: bbfmt ./tmp/hello-until-include.texp
; FILENAME: "./tmp/hello-until-include.texp"

(decl @puts (types i8*) i32)

(def @main params i32 (do
  (call @puts (args "Hello World!\00"))
  (return 0)
))
