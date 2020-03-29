#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Kafka
cd .. && ./gradlew local-support:embedded-kafka:bootRun
