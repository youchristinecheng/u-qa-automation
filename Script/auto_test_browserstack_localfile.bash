#!/bin/bash

#formatted the argument into {<platform>_<market>_<scope>}
FPATH="$1"

if [ -z ${FPATH} ];then
	echo "No file is given from argument. Now exit..."
	exit 0
fi
EXTENSION=${FPATH##*.}
echo "Extract file extension from given file: ${EXTENSION}"

TESTPROFILE=$( echo "$2" | tr A-Z a-z)
if [ -z ${TESTPROFILE} ];then
	echo "No Test profile is given from argument. Now exit..."
	exit 0
fi

IFS='_' read -r -a array <<< "$TESTPROFILE"
PLATFORM="${array[0]}"

EXECENV=$( echo "$3" | tr A-Z a-z)
#EXECENV="${array[1]}${array[2]}"
echo "Extracted Test Profile parameters:"
echo "Test Profile=${TESTPROFILE}"
echo "Test Platform=${PLATFORM}"
echo "Test Environment=${EXECENV}"

APPVER="$4"
if [ -z ${APPVER} ];then
	echo "No build name is given. apply with default guessing:"
	APPVER="[localfile]$(basename -- ${FPATH%.*})"
	APPVER=$(tr "\n" " " <<< ${APPVER})
	APPVER=$(tr " " "_" <<< ${APPVER})
	echo ${APPVER}
else
	APPVER="[localfile]${APPVER}"
	echo "Use given build name:${APPVER}"
fi

if [ ! "${EXTENSION}" = "apk" ] && [ ! "${EXTENSION}" = "ipa" ];then
	echo "Given file is neither .ipa nor .apk file for mobile build. Exit in error"
	exit 1
elif [ \( "${EXTENSION}" = "apk" \) -a \( ${PLATFORM} != "android" \) ];then
	echo "Assuming platform as android required matching a .apk file to be run..."
	exit 1
elif [ \( "${EXTENSION}" = "ipa" \) -a \( ${PLATFORM} != "ios" \) ];then
	echo  "Assuming platform as ios required matching a .ipa file to be run..."
	exit 1
else
	echo "Esitmating platform as ${PLATFORM} as paired to .${EXTENSION} file for run"
fi

#echo "Assumed platform to be execute in: ${PLATFORM}"
exit 1

APPHASH=$(curl -u "${BROWSERSTACK_USER}:${BROWSERSTACK_PWD}" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "file=@${FPATH}" | jq .app_url | sed -e 's/^"//' -e 's/"$//')

echo "BrowserStack returned AppHash: ${APPHASH}"

#if [ ${PLATFORM} = "ANDROID" ];then
#	echo "mvn clean test -Pandroid_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit"
#else
#	echo "mvn clean test -Pios_regression_single -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=sgsit"
#fi

echo "mvn clean test -P${TESTPROFILE} -Dapp=${APPHASH} -Dbuild=${APPVER} -Denv=${EXECENV}"
