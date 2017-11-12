#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Source Server
cd .. && ./gradlew applications:source-app:bootRun
