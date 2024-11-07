#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$user_service_url http://3.38.103.4:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    sudo docker exec -d nginx nginx -s reload
    echo "> docker exec -it nginx nginx -s reload"
}