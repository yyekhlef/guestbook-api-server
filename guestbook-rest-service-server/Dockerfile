FROM fabric8/java-alpine-openjdk8-jdk:1.2.1
MAINTAINER Christophe Furmaniak <christophe.furmaniak@gmail.com>

ENV AB_OF= \
    JAVA_APP_DIR=/usr/local/appli \
    JAVA_APP_NAME=guestbook-rest-service-server \
    LOOZ_VERSION=v0.5.0

LABEL DOCKERFILE_PATH=/usr/local/appli/Dockerfile

RUN mkdir -p /usr/local/appli

COPY target/guestbook-rest-service-server-*.jar  /usr/local/appli/

COPY Dockerfile /usr/local/appli/

EXPOSE 8080
