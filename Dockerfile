FROM openjdk:8
MAINTAINER ddv
COPY deploy /usr/src/miniServer
WORKDIR /usr/src/miniServer
CMD java -jar miniServer.jar
