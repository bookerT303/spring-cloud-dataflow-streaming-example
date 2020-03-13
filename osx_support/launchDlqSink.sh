#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Sink App

cd .. && SPRING_APPLICATION_JSON='{"spring.cloud.stream.bindings.input.destination": "kafka-demo-dlq", "server.port": 8091, "spring.cloud.stream.bindings.input.group": "dlq" }' ./gradlew applications:sink-app:bootRun
