#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Sink App
cd .. && SERVER_PORT=8183 ./gradlew applications:sink-app:bootRun
