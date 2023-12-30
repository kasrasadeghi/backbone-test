(def @test.parser-whitespace params void (do
  (auto %filename %struct.StringView)
  (call @StringView$ptr.set (args %filename ""))
))