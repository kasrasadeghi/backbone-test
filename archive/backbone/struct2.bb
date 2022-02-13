(struct %struct.Basic
  (a i32)
  (b i32)
  (c i32))

(str-table
  (0 "Basic{a = %d, b = %d, c = %d}\0A\00"))

(decl @calloc (types i64 i64) i8*)

(def @makeBasic (params (%v i32)) %struct.Basic*
  (do
    (let %_r (call @calloc (types i64 i64) i8* (args 1 4)))
    (let %r (cast i8* %struct.Basic* %_r))
    (let %field_loc (index %r %struct.Basic 0))
    (store %v i32 %field_loc)
    (return %r %struct.Basic*)
  ))

(decl @printf (types i8* ...) i32)

(def @main (params (%argc i32) (%argv i8**)) i32
  (do
    (let %t (call @makeBasic (types i32) %struct.Basic* (args %argc)))
    
    (let %a_loc (index %t %struct.Basic 0))
    (let %a_val (load i32 %a_loc))

    (let %b_loc (index %t %struct.Basic 1))
    (let %b_val (load i32 %b_loc))

    (let %c_loc (index %t %struct.Basic 2))
    (let %c_val (load i32 %c_loc))

    (call-vargs @printf (types i8* i32 i32 i32) i32 (args
      (str-get 0)
      %a_val %b_val %c_val))
    (return 0 i32)
  ))
  