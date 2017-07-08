(defproject jwatki06/wallpaper "SNAPSHOT"
  :description "Wallpaper from The New Turing Omnibus"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring-server "0.4.0"]
                 [reagent "0.7.0"]
                 [reagent-utils "0.2.1"]
                 [ring "1.6.1"]
                 [ring/ring-defaults "0.3.0"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "1.9.671"
                  :scope "provided"]]

  :plugins [[lein-environ "1.0.2"]
            [lein-cljsbuild "1.1.5"]
            [lein-asset-minifier "0.2.7"
             :exclusions [org.clojure/clojure]]]

  :ring {:handler wallpaper.handler/app
         :uberwar-name "wallpaper.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "wallpaper.jar"

  :main wallpaper.server

  :clean-targets ^{:protect false} [:target-path
                                    [:cljsbuild :builds :app :compiler :output-dir]
                                    [:cljsbuild :builds :app :compiler :output-to]]
  
  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]

  :minify-assets {:assets {"resources/public/css/site.min.css"
                           "resources/public/css/site.css"}}

  :cljsbuild {:builds {:min {:source-paths ["src/cljs" "src/cljc" "env/prod/cljs"]
                             :compiler {:output-to "target/cljsbuild/public/js/app.js"
                                        :output-dir "target/uberjar"
                                        :optimizations :advanced
                                        :pretty-print  false}}

                       :app {:source-paths ["src/cljs" "src/cljc" "env/dev/cljs"]
                             :figwheel {:on-jsload "wallpaper.core/mount-root"}
                             :compiler {:main "wallpaper.dev"
                                        :asset-path "/js/out"
                                        :output-to "target/cljsbuild/public/js/app.js"
                                        :output-dir "target/cljsbuild/public/js/out"
                                        :source-map true
                                        :optimizations :none
                                        :pretty-print  true}}}}
  
  :figwheel {:http-server-root "public"
             :server-port 3449
             :nrepl-port 7002
             :nrepl-middleware ["cemerick.piggieback/wrap-cljs-repl"
                                "cider.nrepl/cider-middleware"
                                "refactor-nrepl.middleware/wrap-refactor"]
             :css-dirs ["resources/public/css"]
             :ring-handler wallpaper.handler/app}
  
  :profiles {:dev {:repl-options {:init-ns wallpaper.repl
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                   :dependencies [[binaryage/devtools "0.9.4"]
                                  [ring/ring-mock "0.3.1"]
                                  [ring/ring-devel "1.6.1"]
                                  [prone "1.1.4"]
                                  [figwheel-sidecar "0.5.11"]
                                  [org.clojure/tools.nrepl "0.2.13"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [pjstadig/humane-test-output "0.8.2"]]

                   :source-paths ["env/dev/clj"]

                   :plugins [[lein-figwheel "0.5.11"]
                             [cider/cider-nrepl "0.10.0-SNAPSHOT"]
                             [org.clojure/tools.namespace "0.3.0-alpha2"
                              :exclusions [org.clojure/tools.reader]]
                             [refactor-nrepl "2.0.0-SNAPSHOT"
                              :exclusions [org.clojure/clojure]]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]}

             :uberjar {:hooks [minify-assets.plugin/hooks]
                       :source-paths ["env/prod/clj"]
                       :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
                       :env {:production true}
                       :aot :all
                       :omit-source true}})
