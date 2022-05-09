DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

TAG="latest"

rm -rf build/

./gradlew bootJar -DskipTests

docker container stop $(docker ps -aqf "name=^office-backend")

docker container rm $(docker ps -aqf "name=^office-backend")

docker rmi $(docker images 'tdx-images/office-backend' -q)

docker build -t tdx-images/office-backend:$TAG -f $DIR/Dockerfile $DIR

docker tag tdx-images/office-backend:latest tdx-images/office-backend:$TAG
