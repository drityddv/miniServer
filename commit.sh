#!/usr/bin/env bash
msg=$1
if [ -n "$msg" ]; then
   git add .
   git commit -m"${msg}"
   git push origin master
   git status
   echo "我的github: https://github.com/drityddv"
else
    echo "commit msg missed !!!"
fi
