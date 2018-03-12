#############################################
# Define colored output func
function title {
    LGREEN='\033[1;32m'
    CLEAR='\033[0m'

    echo -e ${LGREEN}$1${CLEAR}
}

#############################################

title 'Generating tasks list'
java -jar ./ci/java-test-collector-1.0-SNAPSHOT.jar ./

title 'Building docker image'
image=azuresdk-a01-droid:java-$TRAVIS_BUILD_NUMBER
docker build -t $image -f ci/Dockerfile .

title 'Pushing docker image'
image=azuresdk-a01-droid:java-$TRAVIS_BUILD_NUMBER
docker login azureclidev.azurecr.io -u $AZURESDKDEV_ACR_SP_USERNAME -p $AZURESDK_ACR_SP_PASSWORD
docker tag azuresdk-a01-droid:java-$TRAVIS_BUILD_NUMBER azureclidev.azurecr.io/azuresdk-a01-droid:java-$TRAVIS_BUILD_NUMBER
docker tag azuresdk-a01-droid:java-$TRAVIS_BUILD_NUMBER azureclidev.azurecr.io/azuresdk-a01-droid:java-latest
docker push azureclidev.azurecr.io/azuresdk-a01-droid:java-$TRAVIS_BUILD_NUMBER
docker push azureclidev.azurecr.io/azuresdk-a01-droid:java-latest
