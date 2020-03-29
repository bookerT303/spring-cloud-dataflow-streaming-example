#!/bin/bash
open-term () {
osascript <<END
tell app "Terminal"
     do script "cd \"`pwd`\" && $1"
     activate
     end tell
END
}
open-term ./launchKafka.sh
sleep 4
open-term ./launchSinkApp.sh
sleep 4
open-term ./launchProcessorApp.sh
sleep 4
open-term ./launchSourceApp.sh
