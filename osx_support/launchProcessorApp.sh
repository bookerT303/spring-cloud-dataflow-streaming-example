#!/bin/bash
setTitle() {
echo -n -e "\033]0;$*\007"
}
setTitle Processor App
cd .. && ./gradlew applications:processor-app:bootRun
