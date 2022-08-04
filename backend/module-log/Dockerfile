FROM openjdk:11-jre-slim

WORKDIR /app

COPY ./build/libs/*.jar ./module-log.jar

CMD java -jar -Duser.timezone=Asia/Seoul ./module-log.jar