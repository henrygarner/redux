# Redux

A Clojure library of reducing function combinators.

## Installation

Add the following dependency to your `project.clj` or `build.boot`:

```clojure
[redux "0.1.0"]
```

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