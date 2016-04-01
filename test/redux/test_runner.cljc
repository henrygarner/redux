(ns redux.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [redux.core-test]
            [redux.utils-test]))

#?(:cljs (doo-tests 'redux.core-test
                    'redux.utils-test))
