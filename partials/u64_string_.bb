(def @u64.string_ (params (%this u64)) void (do
  (if (== 0 %this) (do
    (return-void)
  ))

  (let %ZERO (+ 48 (0 i8)))
  (let %top  (% %this 10))
  (let %c (+ %ZERO (cast i8 (cast i64 %top))))

  (let %rest (/ %this 10))
  (call-tail @u64.string_ (args %rest))
  (return-void)
))