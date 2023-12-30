(decl @printf (types i8* ...) i32)

(def @main (params) i32 (do
  (auto %cptr i8)
  (store 65 %cptr)
  (let %c (load %cptr))
  (call-vargs @printf (args "c = %c\0A\00" %c))
  (return 0)
))