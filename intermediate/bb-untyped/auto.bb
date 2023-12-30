(decl @printf (types i8* ...) i32)

(def @main (params) i32 (do
  (auto %cptr i8)
  (store 65 %cptr)
  (let %c (load %cptr))
  (call-vargs @printf (args (str-get 0) %c))
  (return 0)
))

(str-table (0 "c = %c\0A\00"))