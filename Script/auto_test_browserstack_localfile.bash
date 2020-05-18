#!/bin/bash

FPATH="$1"
APPVER="$2"

if [ -z ${FPATH} ];then
	echo "No file is given from argument. Now exit..."
	exit 0
fi

EXTENSION=${FPATH##*.}
PLATFORM=""

echo "Extract file extension from given file: ${EXTENSION}"


if [ ! "${EXTENSION}" = "apk" ] && [ ! "${EXTENSION}" = "ipa" ];then
	echo "Given file is neither .ipa nor .apk file for mobile build. Exit in error"
	exit 1
elif [ "${EXTENSION}" = "apk" ];then
	PLATFORM="ANDROID"
else
	PLATFORM="IOS"
fi

echo "Assumed platform to be execute in: ${PLATFORM}"

if [ -z ${APPVER} ];then
	echo "No build name is given. apply with default guessing:"
	APPVER=$(basename -- ${FPATH%.*})
	echo ${APPVER}
else
	echo "Use given build name:${APPVER}"
fi

APPHASH=$(curl -u "rexwong1:SyJxysLVtf8VSETXzTrd" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "file=@${FPATH}" | jq .app_url | sed -e 's/^"//' -e 's/"$//')

echo "BrowserStack returned AppHash: ${APPHASH}"

if [${PLATFORM} = "ANDROID"];then
	echo "mvn clean test -Pandroid_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit"
else
	echo "mvn clean test -Pios_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit"
fi
