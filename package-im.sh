#!/bin/sh

# 先清除资源
mvn -f ./app-im-chat clean
if [ $? -ne 0 ]; then
  exit $?
fi
# 安装必要的依赖
mvn -f ./security clean install
if [ $? -ne 0 ]; then
  exit $?
fi

./package.sh app-im-chat im-chat-launcher ..
