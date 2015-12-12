# Redux

A Clojure library of reducing combinators.

## Usage

```clojure
(require '[redux.core :refer [facet fuse]])

(transduce identity (facet + [dec inc]) (range 5))

;; => [5 15]

(transduce identity (fuse {:a + :b conj}) (range 5))

;; => {:a 10, :b [0 1 2 3 4]}

```

## References

Strongly inspired by [Tesser](https://github.com/aphyr/tesser).

## License

Copyright © 2015 Henry Garner

Distributed under the Eclipse Public License version 1.0.
