
(def @i8$ptr.eqn (params (%this i8*) (%other i8*) (%len u64)) i1 (do
  (if (== 0 %len) (do
    (return true)
  ))

  (if (!= (load %this) (load %other)) (do
    (return false)
  ))

  (let %next-this  (cast i8* (+ 1 (cast u64 %this))))
  (let %next-other (cast i8* (+ 1 (cast u64 %other))))
  (return (call @i8$ptr.eqn (args %next-this %next-other (- %len 1))))
))