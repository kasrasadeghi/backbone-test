(decl @puts (types i8*) i32)

(def @hello-world params void (do
  (call @puts (args "Hello World!\00"))
  (return-void)
))
