LOG_PARAMS='-Dorg.slf4j.simpleLogger.defaultLogLevel=error -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn --batch-mode' ;
if [ ${TRAVIS_JDK_VERSION} = "oraclejdk8" ]; then
     mvn checkstyle:check || travis_terminate 1 ;
fi
mvn -pl !azure-samples package javadoc:aggregate -DskipTests=true $LOG_PARAMS || travis_terminate 1 ;
echo '==> Starting mvn test ===' ;
mvn test -Dsurefire.rerunFailingTestsCount=3 $LOG_PARAMS -Dparallel=classes -DthreadCount=2 -DforkCount=1C || travis_terminate 1 ;
