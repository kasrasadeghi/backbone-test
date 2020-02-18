;========== Parser =================================================================================

(def @i8.isspace (params (%this i8)) i1 (do

; ' ', space 
  (if (== %this 32) (do (return true)))

; '\f', form feed
  (if (== %this 12) (do (return true)))

; '\n', new line
  (if (== %this 10) (do (return true)))
  
; '\r', carriage return
  (if (== %this 13) (do (return true)))

; '\t', horizontal tab
  (if (== %this 9) (do (return true)))

; '\v', vertical tab
  (if (== %this 11) (do (return true)))

  (return false)
))