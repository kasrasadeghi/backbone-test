(decl @printf (types i8* ...) i32)

(def @main (params) i32 (do
  (auto %cptr i8)
  (store 65 i8 %cptr)
  (let %c (load i8 %cptr))
  (call-vargs @printf (types i8* i8) i32 (args "c = %c\0A\00" %c))
  (return 0 i32)
))