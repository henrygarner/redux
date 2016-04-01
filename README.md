# Redux

A Clojure/ClojureScript library of reducing function combinators.

## Installation

Add the following dependency to your `project.clj` or `build.boot`:

```clojure
[redux "0.1.3"]
```

## Usage

```clojure
(require '[redux.core :refer [facet fuse]])

(transduce identity (facet + [dec inc]) (range 5))

;; => [5 15]

(transduce identity (fuse {:a + :b conj}) (range 5))

;; => {:a 10, :b [0 1 2 3 4]}

(def rf
  (facet
    (fuse {:sum  +
           :conj conj})
    [:a :b]))

(transduce identity rf [{:a 1 :b 2} {:a 3 :b 4}])

;; => [{:sum 4, :conj [1 3]} {:sum 6, :conj [2 4]}]
```

## References

Strongly inspired by [Tesser](https://github.com/aphyr/tesser).

## License

Copyright © 2016 Henry Garner

Distributed under the Eclipse Public License version 1.0.
