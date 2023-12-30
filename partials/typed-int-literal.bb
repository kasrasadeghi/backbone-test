(decl @write (types i32 i8* u64) i64)

(def @main params void (do
  (let %FD_STDOUT (+ 1 (0 i32)))
  (let %n (+ 5 (0 i64)))
  (call @write (args %FD_STDOUT "test\00" %n))
  (return-void)
))