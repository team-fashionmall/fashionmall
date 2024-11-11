#!/usr/bin/env bash

function find_idle_profile()
{
    RESPONSE_CODE=$(sudo curl -s -o /dev/null -w "%{http_code}" http://43.203.244.137/cart/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    then
        CURRENT_PROFILE=cart-blue
    else
        CURRENT_PROFILE=$(sudo curl -s http://43.203.244.137/cart/profile) # 여기서 cart-green, cart-blue의 값을 뽑아주는 api 필요
    fi

    if [ ${CURRENT_PROFILE} == cart-green ]
    then
      IDLE_PROFILE=cart-blue
    else
      IDLE_PROFILE=cart-green
    fi

    echo "${IDLE_PROFILE}"
}
# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == cart-green ]
    then
      echo "8080"
    else
      echo "8087"
    fi
}