(def @main (params) i32 (do 
  (let %$0 (+ i32 1 2))
  (do
    (let %$1 (+ i32 1 2))
    (let %$2 (+ i32 1 2))
    (let %$3 (+ i32 1 2))
  )
  (if true (do
    (let %$4 (+ i32 1 2))
    (let %$5 (+ i32 1 2))
    (let %$6 (+ i32 1 2))
  ))
  (return 0 i32)
))