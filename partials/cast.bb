(decl @putchar (types i8) i32)

(def @main (params) i32 (do
  (let %this (+ 10 (0 u64)))

  (let %ZERO (+ 48 (0 i8)))
  (let %top  (% %this 10))
  (let %c (+ %ZERO (cast i8 (cast i64 %top))))

  (call @putchar (args %c))
  (return-void)
))