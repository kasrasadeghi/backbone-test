# backbone-test

This is the testing repository for comparing other implementations with the
reference implementation of the backbone programming language.

Definitely check out `./languages.graph`.  It annotates the entire intermediate
language relationships in a Texp.

## directory structure

The test suite takes backbone from ./input/, and generates:
- the intermediate passes in ./intermediate/, which are currently:
  - ./intermediate/bb-strliteral/
  - ./intermediate/bb-tall/
  - ./intermediate/bb-untyped/
  - ./intermediate/bb0/
- the generated output in ./gen/llvm/
