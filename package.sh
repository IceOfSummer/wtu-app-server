#!/bin/sh

project_name=$1
launcher_module_name=$2
output_path=$3

if [ -z "$1" ]; then
    echo "项目名称为空!"
    exit 2
fi

if [ -z "$2" ]; then
    echo "启动器模块名称为空!"
    exit 2
fi

if [ -z "$3" ]; then
    echo "没有指定输出路径，将默认输出到当前目录"
    output_path="."
fi

echo "mvn install"
mvn install
if [ $? -ne 0 ]; then
  exit $?
fi

echo "mvn install -f ./$project_name install"
mvn install -f ./$project_name install
if [ $? -ne 0 ]; then
  exit $?
fi

echo "mvn install -f ./$project_name/$launcher_module_name package spring-boot:repackage -DskipTests"
mvn install -f ./$project_name/$launcher_module_name package spring-boot:repackage -DskipTests
if [ $? -ne 0 ]; then
  echo -e "\e[34m 如果你是第一次打包，请先将security模块手动install，否则打包会失败 \e[0m"
  exit $?
fi

# 打包后的jar名称
runnable_jar_name=$(ls ./$project_name/$launcher_module_name/target/ | grep '.jar$')
# 删除之前的文件
rm -f $output_path/$runnable_jar_name
# 移动打包好的文件
mv ./$project_name/$launcher_module_name/target/$runnable_jar_name $output_path/$runnable_jar_name

echo "打包完毕, 文件名称: $runnable_jar_name"
