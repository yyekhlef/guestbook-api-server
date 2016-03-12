FROM fabric8/java-alpine-openjdk8-jdk
MAINTAINER Christophe Furmaniak <christophe.furmaniak@gmail.com>

ENV AB_OF= \
    JAVA_APP_DIR=/usr/local/appli \
    JAVA_APP_NAME=guestbook-rest-service-server

LABEL DOCKERFILE_PATH=/usr/local/appli/Dockerfile

RUN mkdir -p /usr/local/appli

COPY target/guestbook-rest-service-server-*.jar  /usr/local/appli/

COPY Dockerfile /usr/local/appli/

EXPOSE 8080