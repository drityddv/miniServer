#!/usr/bin/env bash
docker rmi -f dockerfile-mysql:v1
docker rmi -f dockerfile-redis:v1
docker rmi -f dockerfile-miniserver:v1
#build java jar
cd
cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/mysql
docker build -t dockerfile-mysql:v1 .
cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/redis
docker build -t dockerfile-redis:v1 .
cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/centos
docker build -t dockerfile-centos:v1 .
cd /Users/ddv/workspace/java/miniServer
docker build -t dockerfile-miniserver:v1 .

