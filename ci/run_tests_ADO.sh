#!/usr/bin/env bash

set -e
# ###########################################
# Define colored output func
function title {
    LGREEN='\033[1;32m'
    CLEAR='\033[0m'

    echo -e ${LGREEN}$1${CLEAR}
}

cd ..
CURR_DIR=$(pwd)
echo "==> executing in path : ${CURR_DIR} ===" ;

JAVA_VER=$(java -version 2>&1 | sed -n ';s/.* version "\(.*\)\.\(.*\)\..*"/\1\2/p;')
echo "==> java version : ${JAVA_VER} ===" ;

LOG_PARAMS='-Dorg.slf4j.simpleLogger.defaultLogLevel=error -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn --batch-mode' ;

#############################################
#run jetty
echo '==> Starting mvn jetty:run ===' ;
mvn jetty:run $LOG_PARAMS &
JETTY_PID=$!

echo "==> jetty pid : ${JETTY_PID} ===" ;

#############################################
#wait for jetty to start
echo '==> Waiting for jetty to start ===' ;
sleep 10

#############################################
if [ ${JAVA_VER} -eq 18 ]; then
    title 'Running checkstyle:check under java 1.8'
    mvn checkstyle:check ;
fi
title 'Running mvn -pl !azure-samples package javadoc:aggregate -DskipTests=true $LOG_PARAMS'
mvn -pl !azure-samples package javadoc:aggregate -DskipTests=true $LOG_PARAMS ;
echo '==> Starting mvn test ===' ;
mvn test -Dsurefire.rerunFailingTestsCount=3 $LOG_PARAMS -Dparallel=classes -DthreadCount=2 -DforkCount=1C ;

#############################################
#kill jetty
echo "==> kill the jetty process with PID : ${JETTY_PID} ===" ;
kill $JETTY_PID
