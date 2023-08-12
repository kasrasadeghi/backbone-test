; TESTCASE: include-example/main.bb

(include "header.bb")

(def @main params i32 (do
  (call @hello-world args)
  (return 0)
))