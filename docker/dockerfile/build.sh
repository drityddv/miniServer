#!/usr/bin/env bash
docker rmi -f dockerfile-mysql:v1
docker rmi -f dockerfile-redis:v1
cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/mysql
docker build -t dockerfile-mysql:v1 .
cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/redis
docker build -t dockerfile-redis:v1 .
