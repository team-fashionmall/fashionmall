FROM --platform=linux/amd64 gcr.io/distroless/java17

WORKDIR /fashion-mall/module-item-service

COPY *SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} *SNAPSHOT.jar