#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Sink App
cd .. && ./gradlew applications:sink-app:bootRun
