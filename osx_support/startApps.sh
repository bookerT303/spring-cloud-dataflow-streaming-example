#!/bin/bash
open-term () {
osascript <<END
tell app "Terminal"
     do script "cd \"`pwd`\" && $1"
     activate
     end tell
END
}
open-term ./launchSinkApp.sh
sleep 14
open-term ./launchSinkApp2.sh
sleep 14
open-term ./launchProcessorApp.sh
sleep 14
open-term ./launchProcessorApp2.sh
sleep 14
open-term ./launchSourceApp.sh
