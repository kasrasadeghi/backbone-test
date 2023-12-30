(include "stdc.bb")

(def @main params i32 (do
  (call @puts (args "Hello World!\00"))
  (return 0)
))
