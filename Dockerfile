from blueuninfo-registry.com:5000/oracle-java-font:8
MAINTAINER yueyu <yueyu@blueuninfo.com>
ENV LANG=en_US.UTF-8
ENV TZ=Asia/Shanghai
workdir /
add apply-service.jar apply.jar
arg active
run echo "${active}"
arg active
#"-Dspring.profiles.active=$active"
cmd ["java","-jar","/apply.jar"]