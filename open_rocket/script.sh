#!/bin/bash

plugin_name="ExtensionTemplate";

# Creates jar file
cd "$(dirname "$0")"
ant

# Moves jar file to OpenRocket plugins directory
case "$OSTYPE" in
  darwin*)  mv $plugin_name.jar ~/Library/Application\ Support/OpenRocket/Plugins/$plugin_name.jar ;;
  linux*)   mv $plugin_name.jar ~/.openrocket/Plugins/$plugin_name.jar ;;
  msys*)    mv $plugin_name.jar c:\Application Data\OpenRocket\ThrustCurves\Plugins\$plugin_name.jar ;;
  *)        echo "unknown: $OSTYPE" ;;
esac

# Opens OpenRocket with new plugin
cd "$(dirname "$0")"
java -jar OpenRocket.jar