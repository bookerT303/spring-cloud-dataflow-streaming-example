#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Processor App
cd .. && SERVER_PORT=8182 ./gradlew applications:processor-app:bootRun
