(def @Grammar$ptr.get-keyword (params (%this %struct.Grammar*) (%type-name %struct.StringView*)) %struct.Texp (do
  (let %prod (call @Grammar$ptr.getProduction (args %this %type-name)))
  (let %rule (load (index %prod 1)))
  (if (call @Texp$ptr.value-check (args %rule "|\00")) (do
    (return (call @Optional.none args))
  ))

  (return (call @Texp.makeFromString (args %rule-value)))
))
