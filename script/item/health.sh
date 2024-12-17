#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://13.125.10.163:$IDLE_PORT/item/profile"
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://13.125.10.163:${IDLE_PORT}/item/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep -E -o 'green|blue' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("item" 문자열이 있는지 검증)
      echo "> Health check 성공"
      switch_proxy
      # 기존 컨테이너 삭제
      echo "> 기존 컨테이너 중지"
      if [ "$RESPONSE" == "green" ]
      then
        sudo docker stop item-blue
      else
        sudo docker stop item-green
      fi

      # 필요시 컨테이너와 이미지 삭제
      echo "> 기존 컨테이너 및 이미지 삭제"
      if [ "$RESPONSE" == "green" ]
      then
        sudo docker rm item-blue
      else
        sudo docker rm item-green
      fi

      # 더 이상 재시도할 필요 없이 종료
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
      echo "> Health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done

# 컨테이너 삭제 후 시스템에서 리소스 정리
echo "> Docker system prune 실행"
sudo docker system prune -a -f