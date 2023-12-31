(decl @write  (types i32 i8* u64) i64)

;; minimum from string.bb, i8.bb, stdc.bb, intstr.bb
(struct %struct.StringView
  (%ptr i8*)
  (%size u64))


;; i8.bb

; NOTE: does not include trailing zero
(def @i8$ptr.length_ (params (%this i8*) (%acc u64)) u64 (do
  (if (== (load %this) 0) (do
    (return %acc)
  ))

  (return (call-tail @i8$ptr.length_ (args (cast i8* (+ 1 (cast u64 %this))) (+ %acc 1))))
))

(def @i8$ptr.length (params (%this i8*)) u64 (do
  (return (call-tail @i8$ptr.length_ (args %this 0)))
))

(def @StringView.makeFromi8$ptr (params (%charptr i8*)) %struct.StringView (do
  (auto %result %struct.StringView)
  (store %charptr (index %result 0))
  (store (call @i8$ptr.length (args %charptr)) (index %result 1))
  (return (load %result))
))

(def @i8$ptr.printn (params (%this i8*) (%n u64)) void (do
  (let %FD_STDOUT (+ 1 (0 i32)))
  (call @write (args %FD_STDOUT %this %n))
  (return-void)
))

; NOTE: unsafe!
(def @i8$ptr.unsafe-print (params (%this i8*)) void (do
  (let %length (call @i8$ptr.length (args %this)))
  (call @i8$ptr.printn (args %this %length))
  (return-void)
))

(def @i8$ptr.unsafe-println (params (%this i8*)) void (do
  (call @i8$ptr.unsafe-print (args %this))
  (call @println args)
  (return-void)
))

(def @println params void (do
  (let %NEWLINE (+ 10 (0 i8)))
  (call @i8.print (args %NEWLINE))
  (return-void)
))

(def @i8.print (params (%this i8)) void (do
  (auto %c i8)
  (store %this %c)
  (let %FD_STDOUT (+ 1 (0 i32)))
  (call @write (args %FD_STDOUT %c 1))
  (return-void)
))

;============ intstr.bb ===================

(def @u64.string_ (params (%this u64) (%acc %struct.String*)) void (do
  (if (== 0 %this) (do
    (return-void)
  ))

  (let %ZERO (+ 48 (0 i8)))
  (let %top  (% %this 10))
  (let %c (+ %ZERO (cast i8 (cast i64 %top))))
  (call @String$ptr.pushChar (args %acc %c))

  (let %rest (/ %this 10))
  (call-tail @u64.string_ (args %rest %acc))
  (return-void)
))

(def @u64.string (params (%this u64)) %struct.String (do
  (auto %acc %struct.String)
  (store (call @String.makeEmpty args) %acc)

  (let %ZERO (+ 48 (0 i8)))

  (if (== 0 %this) (do
    (call @String$ptr.pushChar (args %acc %ZERO))
    (return (load %acc))
  ))

  (call @u64.string_ (args %this %acc))
  (call @String$ptr.reverse-in-place (args %acc))

  (return (load %acc))
))

(def @u64.print (params (%this u64)) void (do
  (auto %string %struct.String)
  (store (call @u64.string (args %this)) %string)
  (call @String$ptr.print (args %string))
  (return-void)
))

(def @u64.println (params (%this u64)) void (do
  (call @u64.print (args %this))
  (call @println args)
  (return-void)
))

;============== string.bb =========================

(def @String.makeEmpty params %struct.String (do
  (auto %result %struct.String)
  (store (cast i8* (0 i64)) (index %result 0))
  (store 0 (index %result 1))
  (return (load %result))
))

(decl @malloc (types u64) i8*)
(decl @realloc (types i8* u64) i8*)

(def @String$ptr.reverse-in-place (params (%this %struct.String*)) void (do
  (let %begin (load (index %this 0)))
  (let %size (load (index %this 1)))
  (if (== 0 %size) (do (return-void)))
; begin + size -> null char, so begin + size - 1 -> last char
  (let %end (cast i8* (+ (- %size 1) (cast u64 %begin))))
  (call-tail @reverse-pair (args %begin %end))
  (return-void)
))

(def @String$ptr.setFromChar (params (%this %struct.String*) (%c i8)) void (do
  (let %ptr-ref (index %this 0))
  (let %size-ref (index %this 1))

  (let %ptr (call @malloc (args 2)))
  (store %c %ptr)
  (store 0 (cast i8* (+ 1 (cast u64 %ptr))))
  (store %ptr %ptr-ref)
  (store 1 %size-ref)
  (return-void)
))


; even though Strings own an extra byte at the end, we don't have to print it because it's definitionally null char
(def @String$ptr.print (params (%this %struct.String*)) void (do
  (call @i8$ptr.printn (args (load (index %this 0)) (load (index %this 1))))
  (return-void)
))

(struct %struct.String
  (%ptr i8*)
  (%size u64)
)


(def @i8$ptr.swap (params (%this i8*) (%other i8*)) void (do
  (let %this_value (load %this))
  (let %other_value (load %other))
  (store %this_value %other)
  (store %other_value %this)
  (return-void)
))


(def @reverse-pair (params (%begin i8*) (%end i8*)) void (do
  (if (>= (cast u64 %begin) (cast u64 %end)) (do
    (return-void)
  ))
  (call @i8$ptr.swap (args %begin %end))
  (let %next-begin (cast i8* (+ (cast u64 %begin) 1)))
  (let %next-end   (cast i8* (- (cast u64 %end) 1)))
  (call-tail @reverse-pair (args %next-begin %next-end))
  (return-void)
))


(def @String$ptr.pushChar (params (%this %struct.String*) (%c i8)) void (do
  (let %ptr-ref (index %this 0))
  (let %size-ref (index %this 1))

  (if (== 0 (cast u64 (load %ptr-ref))) (do
;   TODO assert size is also zero
    (call-tail @String$ptr.setFromChar (args %this %c))
    (return-void)
  ))

  (let %old-size (load %size-ref))
; the size does not include the terminating byte, but it is guaranteed to be there
; add 1 for the null character and 1 for the pushed character, 2 total
  (store (call @realloc (args (load %ptr-ref) (+ 2 %old-size))) %ptr-ref)
  (store (+ 1 %old-size) %size-ref)

  (let %new-char-loc (cast i8* (+ %old-size (cast u64 (load (%ptr-ref))))))
  (store %c %new-char-loc)
  (return-void)
))


(def @StringView.length (params (%this %struct.StringView)) u64 (do
  (auto %local %struct.StringView)
  (store %this %local)
  (return (load (index %local 1)))
))

(def @test.stringview-length params void (do
  (let %stringview (call @StringView.makeFromi8$ptr (args "Hello World\00")))

  (let %msg "should print '11'\00")
  (call @i8$ptr.unsafe-println (args %msg))
  (call @u64.println (args (call @StringView.length (args %stringview))))
  (return-void)
))

(def @main params i32 (do
  (call @test.stringview-length args)
  (return 0)
))