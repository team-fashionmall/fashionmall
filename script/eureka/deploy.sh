#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PROFILE=$(find_idle_profile)
IDLE_PORT=$(find_idle_port)
REPOSITORY=/home/ec2-user/app/demo/module-eureka-server

CONTAINER_ID=$(docker container ls -f "name=eureka-${IDLE_PROFILE}" -q)

echo "> 컨테이너 ID는 무엇?? ${CONTAINER_ID}"
echo "> 현재 프로필은 무엇?? eureka-${IDLE_PROFILE}"

if [ -z ${CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> docker stop eureka-${IDLE_PROFILE}"
  sudo docker stop eureka-${IDLE_PROFILE}
  echo "> docker rm eureka-${IDLE_PROFILE}"
  sudo docker rm eureka-${IDLE_PROFILE}    # 컨테이너 이름을 지정해서 사용하기 때문에.. 꼭 컨테이너 삭제도 같이 해주셔야 합니다. (나중에 다시 띄울거기 때문에..)
  sleep 5
fi

# 구분

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY      # 새로운 jar file 계속 덮어쓰기

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

sudo chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

echo "> $JAR_NAME 를 profile=eureka-$IDLE_PROFILE 로 실행합니다."

cd $REPOSITORY

sudo docker build -t eureka ./ || exit 1 # 이미지 생성(모듈별)
sudo docker run -it --name "eureka-$IDLE_PROFILE" -d -e SPRING_PROFILES_ACTIVE=eureka-$IDLE_PROFILE -p $IDLE_PORT:$IDLE_PORT eureka || exit 1 #컨테이너 실행(모듈별)