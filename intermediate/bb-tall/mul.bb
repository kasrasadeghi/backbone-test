(str-table
  (0 "test multiply: 7 * argc = %d\0A\00"))

(decl @printf (types i8* ...) i32)

(def @main (params (%argc i32) (%argv i8**)) i32 (do
  (call-vargs @printf (args (str-get 0) (* 7 %argc)))

  (return 0)
))
