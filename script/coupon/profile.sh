#!/usr/bin/env bash

function find_idle_profile()
{
    RESPONSE_CODE=$(sudo curl -s -o /dev/null -w "%{http_code}" http://3.38.103.4/)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=coupon_blue
    else
        CURRENT_PROFILE=$(sudo curl -s http://3.38.103.4/)
    fi

    if [ ${CURRENT_PROFILE} == coupon_green ]
    then
      IDLE_PROFILE=coupon_blue
    else
      IDLE_PROFILE=coupon_green
    fi

    echo "${IDLE_PROFILE}"
}
# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == coupon_green ]
    then
      echo "8081"
    else
      echo "8088"
    fi
}