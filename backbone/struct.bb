(struct %struct.Basic
  (a i32))

(str-table
  (0 "Basic{a = %d}\0A\00"))

(decl @calloc (types i64 i64) i8*)

(def @makeBasic (params (%a i32)) %struct.Basic*
  (do
    (let %_r (call @calloc (types i64 i64) i8* (args 1 4)))
    (let %r (cast i8* %struct.Basic* %_r))
    (let %field_loc (index %r %struct.Basic 0))
    (store %a i32 %field_loc)
    (return %r %struct.Basic*)
  ))

(decl @printf (types i8* ...) i32)

(def @main (params (%argc i32) (%argv i8**)) i32
  (do
    (let %t (call @makeBasic (types i32) %struct.Basic* (args %argc)))
    (let %field_loc (index %t %struct.Basic 0))
    (let %field_val (load i32 %field_loc))
    (call-vargs @printf (types i8* i32) i32 (args
      (str-get 0)
      %field_val))
    (return 0 i32)
  ))
  