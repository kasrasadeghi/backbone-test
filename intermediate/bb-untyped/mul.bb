(decl @printf (types i8* ...) i32)

(def @main (params (%argc i32) (%argv i8**)) i32 (do
  (let %$0 (* 7 %argc))
  (call-vargs @printf (args (str-get 0) %$0))

  (return 0)
))

(str-table (0 "test multiply: 7 * argc = %d\0A\00"))