(decl @printf (types i8* ...) i32)

(def @main (params) i32 (do
  (auto %cptr i8)
  (store 65 %cptr)
  (call-vargs @printf (args (str-get 0) (load %cptr)))
  (return 0)
))

(str-table (0 "c = %c\0A\00"))