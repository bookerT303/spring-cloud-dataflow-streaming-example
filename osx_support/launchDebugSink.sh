#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Sink App

cd .. && SPRING_APPLICATION_JSON='{"spring.cloud.stream.bindings.input.destination": "kafka-demo", "server.port": 8089, "spring.cloud.stream.bindings.input.group": "debug" }' ./gradlew applications:sink-app:bootRun
