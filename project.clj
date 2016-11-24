(defproject redux "0.1.4"
  :description "A Clojure library of reducing combinators"
  :url "https://github.com/henrygarner/redux"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.8.40"]
                                  [doo "0.1.6"]]
                   :plugins [[lein-cljsbuild "1.1.3"]
                             [lein-doo "0.1.6"]]}}
  :cljsbuild
  {:builds
   {:test {:source-paths ["src" "test"]
           :compiler {:output-to "target/main.js"
                      :main 'redux.test-runner
                      :optimizations :whitespace}}}}
  :aliases
  {"test-cljs" ["doo" "phantom" "test" "once"]})
