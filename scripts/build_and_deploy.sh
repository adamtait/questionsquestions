#!/usr/bin/env bash

## build the war
lein clean
lein ring uberwar



## unpack it
rm -rf target/war
mkdir target/war

unzip -d target/war target/questionsquestions-0.0.1-SNAPSHOT-standalone.war > /dev/null

## add App Engine configs
cp app.yaml target/war
cp appengine-web.xml target/war/WEB-INF/
cp logging.properties target/war/WEB-INF/


## deploy
appcfg.sh update target/war
