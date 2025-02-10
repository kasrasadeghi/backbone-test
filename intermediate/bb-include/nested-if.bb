; codegen test: make sure nested ifs have proper individual IDs
;(include "core.bb")

;(def @main (params (%argc i32) (argv i8**)) i32 (do
;  (if (> %argc 3) (do
;    (if (< %argc 5) (do
;      (call @i8$ptr.unsafe-println (args "4 arguments to main!\00"))
;    ))
;  ))
;  (return 0)
;))