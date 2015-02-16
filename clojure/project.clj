(defproject funny-words "0.1.0-SNAPSHOT"
  :description "A scientific method for calculating the funniness of Finnish words."
  :url "http://wunderdog.fi/koodaus-hassuimmat-sanat/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 ]
  :main ^:skip-aot funny-words.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.8"]
                                  [midje "1.6.3"]
                                  ]
                   :plugins [[lein-midje "3.1.3"]]}})
