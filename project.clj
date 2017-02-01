(defproject org/questionsquestions "0.0.1-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}


  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [compojure "1.1.8"]
                 [javax.servlet/servlet-api "2.5"]

                 [hiccup "1.0.5"]
                 
                 [com.google.appengine/appengine-api-1.0-sdk "1.9.34"]]


  :source-paths      ["src/clojure"]
  ;;:java-source-paths ["src/java"]
  :javac-options     ["-target" "1.7" "-source" "1.7" "-Xlint:-options"]

  :min-lein-version "2.0.0"

  :resource-paths ["config", "resources"]

  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [http-kit "2.1.16"]]
                   ;;:aliases {"run-dev" ["trampoline" "run" "-m" "user/go"]}
                   }
             
             :production
             {:ring
              {:open-browser? false
               :stacktraces? true
               :auto-reload? false}}
             
             :uberjar {:aot [org.questionsquestions.server]}}
  :main ^{:skip-aot true} org.questionsquestions.server

  :plugins [[lein-ring "0.9.7"]]
  :ring {
         :handler org.questionsquestions.server/app-engine-handlers
         :init org.questionsquestions.server/app-engine-start
         :destroy org.questionsquestions.server/app-engine-stop})

