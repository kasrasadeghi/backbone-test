; CMD: bbfmt ./tmp/struct-until-include.texp
; FILENAME: "./tmp/struct-until-include.texp"

(struct %struct.Basic (a i32))

(decl @calloc (types i64 i64) i8*)

(def @makeBasic (params (%a i32)) %struct.Basic*(do 
  (let %r (cast %struct.Basic* (call @calloc (args 1 4))))
  (let %field_loc (index %r 0))
  (store %a %field_loc)
  (return %r)
))

(decl @printf (types i8* ...) i32)

(def @main (params (%argc i32) (%argv i8**)) i32(do 
  (let %t (call @makeBasic (args %argc)))
  (let %field_loc (index %t 0))
  (let %field_val (load %field_loc))
  (call @printf (args "Basic{a = %d}\0A\00" %field_val))
  (return 0)
))
