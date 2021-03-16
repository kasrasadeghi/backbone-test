(include "../../cornerstone/lib/core.bb")

(struct %struct.A
  (%a u64)
  (%b u64)
)

(def @main (params) i32 (do
  (auto %A %struct.A)
  (store 0 (index %A (+ 0 (0 u64))))
  (call @u64.println (args (load (index %A 0))))
  (return 0)
))