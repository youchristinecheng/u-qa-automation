#!/bin/bash
PLATFORM=$( echo "$1" | tr a-z A-Z)
BUILDVER=$2
APPNAME=""


if [ ! "$PLATFORM" = "IOS" ] && [ ! "$PLATFORM" = "ANDROID" ]
then
	echo "platform is not match"
	exit 1
fi

if [ -z "$BUILDVER" ];then
       	echo "build veriosn is empty. Default assgin to latest"
	BUILDVER="latest"
fi


if [ "$PLATFORM" = "IOS" ]
then
	APPNAME="YOUTrip-iOS"
else
	APPNAME="YOUTrip-Android-Sit"
fi

echo "get ${APPNAME} build ${BUILDVER}"
DISTRIBUTE=$(curl -sX GET  "https://api.appcenter.ms/v0.1/apps/youco/${APPNAME}/releases/${BUILDVER}" \
-H "Content-Type: application/json" \
-H "X-Api-Token: cf3182d2b3197a0a6809fa0a5a3945336331ff45")


jq . <<< ${DISTRIBUTE}

DLURL=$(jq .download_url <<< ${DISTRIBUTE})
APPVER=$(jq .short_version <<< ${DISTRIBUTE} | sed -e 's/^"//' -e 's/"$//')
echo "AppCenter downlaod url: ${DLURL}" 
echo "AppCenter  build display version: ${APPNAME}"

echo "Upload build to browserStack"
APPHASH=$(echo $(curl -u "rexwong1:SyJxysLVtf8VSETXzTrd" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "data={\"url\": ${DLURL}}") | jq .app_url | sed -e 's/^"//' -e 's/"$//')

echo "BrowserStack returned AppHash: ${APPHASH}"

if [ ${PLATFORM} = "ANDROID" ];then
	echo "mvn clean test -Pandroid_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit"
else
	echo "mvn clean test -Pios_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit"
fi
