(decl @printf (types i8* ...) i32)

(def @void-returner params void (do
  (auto %cptr i8) (store 65 i8 %cptr) (let %c (load i8 %cptr)) (call-vargs @printf (types i8* i8) i32 (args (str-get 0) %c))
  (return-void)
))

(def @main params i32 (do
  (call @void-returner types void args) (return (i32 0))
))

(str-table (0 "c = %c\0A\00"))