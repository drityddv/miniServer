#!/usr/bin/env bash
#!/usr/bin/env bash
#docker rmi -f ddvddv/mini-server:mysql-v1
#docker rmi -f ddvddv/mini-server:redis-v1
#docker rmi -f ddvddv/mini-server:centos7-v1
#docker rmi -f ddvddv/mini-server:service-center-v1
#docker rmi -f ddvddv/mini-server:game-v1
#docker rmi -f ddvddv/mini-server:game-v1
docker rmi -f ddvddv/mini-server:netty-v2
##build software
#cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/mysql
#docker build -t ddvddv/mini-server:mysql-v1 .
#cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/redis
#docker build -t ddvddv/mini-server:redis-v1 .
#cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/centos
#docker build -t ddvddv/mini-server:centos7-v1 .
##build microService
#cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/service/center
#docker build -t ddvddv/mini-server:service-center-v1 .
#cd /Users/ddv/workspace/java/miniServer
#docker build -t ddvddv/mini-server:game-v1 .
cd /Users/ddv/workspace/java/miniServer/docker/dockerfile/netty
docker build -t ddvddv/mini-server:netty-v2 .
