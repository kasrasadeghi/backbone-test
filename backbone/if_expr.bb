(str-table
  (0 "Hello World!\00")
  (1 "Goodbye World!\00"))

(decl @puts (types i8*) i32)

(def @main (params (%argc i32) (%argv i8**)) i32
  (do
    (let %condless (< i32 %argc 3))
    (if %condless
      (do
        (call @puts (types i8*) i32 (args (str-get 0)))
      )
    )

    (let %condgte (>= i32 %argc 3))
    (if %condgte
      (do
        (call @puts (types i8*) i32 (args (str-get 1)))
      )
    )

    (return 0 i32)
  ))
