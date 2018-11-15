#!/bin/bash
open-term () {
osascript <<END
tell app "Terminal"
     do script "cd \"`pwd`\" && $1"
     activate
     end tell
END
}
open-term ./launchDebugSink.sh
