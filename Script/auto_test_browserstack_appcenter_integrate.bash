#!/bin/bash

PLATFORM=$( echo "$1" | tr a-z A-Z)
BUILDVER=$2
APPNAME=""


if [ ! "$PLATFORM" = "IOS" ] && [ ! "$PLATFORM" = "ANDROID" ]
then
	echo "Given platform from argument is not match. Only allow for 'IOS' or 'ANDROID'. Now exit..."
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
-H "X-Api-Token: ${APPCENTER_API_TOKEN}")


jq . <<< ${DISTRIBUTE}

SHORTVER=$(jq .short_version <<< ${DISTRIBUTE}| sed -e 's/^"//' -e 's/"$//')
VER=$(jq .version <<< ${DISTRIBUTE}| sed -e 's/^"//' -e 's/"$//')
APPVER="${APPNAME}-${SHORTVER}-${VER}"
DLURL=$(jq .download_url <<< ${DISTRIBUTE})
echo "AppCenter downlaod url: ${DLURL}" 
echo "AppCenter  build display version: ${APPVER}"

echo "Upload build to browserStack"
APPHASH=$(echo $(curl -u "${BROWSERSTACK_USER}:${BROWSERSTACK_PWD}" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "data={\"url\": ${DLURL}}") | jq .app_url | sed -e 's/^"//' -e 's/"$//')

echo "BrowserStack returned AppHash: ${APPHASH}"

if [ ${PLATFORM} = "ANDROID" ];then
	mvn clean test -Pandroid_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit
else
	mvn clean test -Pios_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit
fi
