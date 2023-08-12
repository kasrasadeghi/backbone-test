(str-table
  (0 "test multiply: 7 * argc = %d\0A\00"))

(decl @printf (types i8* ...) i32)

(def @main (params (%argc i32) (%argv i8**)) i32 (do
  (let %0 (* i32 7 %argc))
  (call-vargs @printf (types i8* i32) i32 (args (str-get 0) %0))

  (return 0 i32)
))
