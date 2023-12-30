(decl @printf (types i8* ...) i32)

(def @void-returner (params) void (do
  (auto %cptr i8)
  (store 65 %cptr)
  (let %c (load %cptr))
  (call-vargs @printf (args "c = %c\0A\00" %c))
  (return-void)
))

(def @main (params) i32 (do
  (call @void-returner args)
  (return 0)
))