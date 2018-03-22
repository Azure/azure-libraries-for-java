#!/usr/bin/env bash

set -e
# ###########################################
# Define colored output func
function title {
    LGREEN='\033[1;32m'
    CLEAR='\033[0m'

    echo -e ${LGREEN}$1${CLEAR}
}

#############################################
LOG_PARAMS='-Dorg.slf4j.simpleLogger.defaultLogLevel=error -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn --batch-mode' ;
if [ ${TRAVIS_JDK_VERSION} = "oraclejdk8" ]; then
    title 'Running checkstyle:check'
    mvn checkstyle:check ;
fi
title 'Running mvn -pl !azure-samples package javadoc:aggregate -DskipTests=true $LOG_PARAMS'
mvn -pl !azure-samples package javadoc:aggregate -DskipTests=true $LOG_PARAMS ;
echo '==> Starting mvn test ===' ;
mvn test -Dsurefire.rerunFailingTestsCount=3 $LOG_PARAMS -Dparallel=classes -DthreadCount=2 -DforkCount=1C ;
