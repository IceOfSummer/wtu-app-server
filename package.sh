#!/bin/sh

if [ -z "$1"]; then
    echo "项目名称为空!"
    exit 2
fi

if [ -z "$2"]; then
    echo "启动器模块名称为空!"
    exit 2
fi

project_name=$1
launcher_module_name=$2
base_url=$3

mvn install -f $base_url install
if [ $? -ne 0]; then
  exit $?
fi

mvn install -f $base_url/$project_name install
if [ $? -ne 0]; then
  exit $?
fi

mvn install -f $base_url/$project_name/$launcher_module_name clean package spring-boot:repackage -DskipTests
if [ $? -ne 0]; then
  echo -e "\e[34m 如果你是第一次打包，请先将security模块手动install，否则打包会失败 \e[0m"
  exit $?
fi

# 打包后的jar名称
runnable_jar_name=$(ls $base_url/$project_name/$launcher_module_name/target/ | grep .jar)
# 删除之前的文件
rm -f $runnable_jar_name
# 移动打包好的文件
mv $base_url/$project_name/$module_name/target/$runnable_jar_name ./$runnable_jar_name

echo "打包完毕, 文件名称: $runnable_jar_name"
