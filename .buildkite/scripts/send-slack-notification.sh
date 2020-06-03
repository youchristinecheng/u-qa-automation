#!/bin/sh
## 1. Attachmend; 2. ENV; 3. PLATFORM; 4.BUILDVER; 5. STATUS

SLACK_OAUTH_TOKEN=$BUILDKITE_SLACK_TOKEN
FILE_PATH=$1
SLACK_CHANNEL=buildkite_test_report

## MODIFY JSON DATA
if [ "$5" == "true" ]; then RESULT=36a64f STATUS=PASSED; else  RESULT=ff0000 STATUS=FAILED; fi

sed -e "s/<ENV>/$2/g; s/<PLATFORM>/$3/g; s/<BUILDVER>/$4/g; s/<BUILDNUM>/$BUILDKITE_BUILD_NUMBER/g; s/<COLOR>/$RESULT/g; s/<STATUS>/$STATUS/g; s/<JOBTYPE>/$BUILDKITE_JOBTYPE/g; s/<JOBNAME>/$BUILDKITE_PIPELINE_SLUG/g; " slackmsg_template.json > slackmsg.json
curl -H "Content-Type: application/json" --data @slackmsg.json -H "Authorization: Bearer $SLACK_OAUTH_TOKEN" https://slack.com/api/chat.postMessage
curl -F "file=@$FILE_PATH" -F title=HTML_REPORT_FILE -F channels=$SLACK_CHANNEL -H "Authorization: Bearer $SLACK_OAUTH_TOKEN" https://slack.com/api/files.upload
