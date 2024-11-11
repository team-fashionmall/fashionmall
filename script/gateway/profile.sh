#!/usr/bin/env bash

function find_idle_profile()
{
    IDLE_PROFILE=gateway-green
    echo "${IDLE_PROFILE}"
}
# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == gateway-green ]
    then
      echo "8000"
    fi
}