; TESTCASE: if-expr.bb

(decl @puts (types i8*) i32)

(def @main (params (%argc i32) (%argv i8**)) i32 (do
  (if (< %argc 3) (do
    (call @puts (args "Hello World!\00"))
  ))

  (if (>= %argc 3) (do
    (call @puts (args "Goodbye World!\00"))
  ))

  (return 0)
))
