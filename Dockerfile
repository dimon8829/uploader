FROM openjdk:8-jdk-slim-stretch
VOLUME /tmp
RUN apt-get -qq update
RUN apt-get -qq -y install curl
RUN apt-get install -y iputils-ping procps net-tools telnet
ARG JAR_FILE
ARG MODULE_PATH
ARG JMX_PORT
ADD ${JAR_FILE} uploader.jar
ENTRYPOINT java -jar /uploader.jar