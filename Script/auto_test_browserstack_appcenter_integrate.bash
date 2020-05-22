#!/bin/bash

function join { local IFS="$1"; shift; echo "$*"; }

#formatted the argument into {<platform>_<market>_<scope>}
PLATFORM=$( echo "$1" | tr A-Z a-z)
BUILDVER=$2
EXECENV=$(echo "$3" | tr A-Z a-z)

IFS='_' read -r -a array <<< "$PLATFORM"

TESTPROFILE=${PLATFORM}
PLATFORM="${array[0]}"
TESTENV=${EXECENV:2}

APPNAME=""

echo "Test Profile=${TESTPROFILE}"
echo "Platform=${PLATFORM}"
echo "Test Environmentin Profile=${EXECENV}"
echo "Declared Test Environment for AppCenter=${TESTENV}"

if [ ! "$PLATFORM" = "ios" ] && [ ! "$PLATFORM" = "android" ]
then
	echo "Given platform from argument is not match. Only allow for 'IOS' or 'ANDROID'. Now exit..."
	exit 1
fi

if [ -z "$BUILDVER" ];then
       	echo "build veriosn is empty. Default assgin to latest"
	BUILDVER="latest"
fi


if [ "$PLATFORM" = "ios" ]
then
	if [ "$TESTENV" = "sit" ]
	then
		APPNAME="YOUTrip-iOS"
	else
		APPNAME="YOUTrip-iOS-DEV-1"
	fi

else
	if [ "$TESTENV" = "sit" ]
	then
		APPNAME="YOUTrip-Android-Sit"
	else
		APPNAME="YOUTrip-Android-DEV-1"
	fi
fi

echo "get ${APPNAME} build ${BUILDVER}"
DISTRIBUTE=$(curl -sX GET  "https://api.appcenter.ms/v0.1/apps/youco/${APPNAME}/releases/${BUILDVER}" \
-H "Content-Type: application/json" \
-H "X-Api-Token: ${APPCENTER_TOKEN}")


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

#if [ ${PLATFORM} = "ANDROID" ];then
#	mvn clean test -Pandroid_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit
#else
#	mvn clean test -Pios_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit
#fi
mvn clean test -P${TESTPROFILE} -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=${EXECENV}
