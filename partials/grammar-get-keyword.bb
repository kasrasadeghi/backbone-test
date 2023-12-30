(def @Grammar$ptr.get-keyword (params (%this %struct.Grammar*) (%type-name %struct.StringView*)) %struct.Texp (do
  (let %prod (call @Grammar$ptr.getProduction (args %this %type-name)))
  (let %rule (load (index %prod 1)))
  (if (call @Texp$ptr.value-check (args %rule "|\00")) (do
    (return (call @Optional.none args))
  ))

; TODO make into assert
  (let %rule-value (index %rule 0))
  (if (call @String$ptr.is-empty (args %rule-value)) (do
    (call @i8$ptr.unsafe-println (args "rule value should not be empty for rule:\00"))
    (call @Texp$ptr.parenPrint (args %rule))
    (call @exit (args 1))
  ))

  (let %HASH (+ 35 (0 i8)))
  (if (== %HASH (call @String$ptr.char-at-unsafe (args %rule-value 0))) (do
    (return (call @Optional.none args))
  ))

  (return (call @Texp.makeFromString (args %rule-value)))
))
