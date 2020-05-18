#!/bin/sh

SLACK_OAUTH_TOKEN=$BUILDKITE_SLACK_TOKEN
FILE_PATH=./target/surefire-reports/emailable-report.html
SLACK_CHANNEL=buildkite_test_report

curl -F file=$FILE_PATH -F channels=$SLACK_CHANNEL -H "Authorization: Bearer $SLACK_OAUTH_TOKEN" https://slack.com/api/files.upload
