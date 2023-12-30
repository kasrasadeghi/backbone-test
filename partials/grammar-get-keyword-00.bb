(def @Grammar$ptr.get-keyword (params (%this %struct.Grammar*) (%type-name %struct.StringView*)) %struct.Texp (do
  (call @Texp$ptr.value-check (args %rule "|\00"))
))