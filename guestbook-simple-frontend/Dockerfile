FROM nginx:stable-alpine
MAINTAINER Christophe Furmaniak <christophe.furmaniak@gmail.com>

LABEL LOOZ_VERSION=v0.5.0

RUN mkdir -p /etc/nginx/html/config /etc/confd/conf.d /etc/confd/templates

COPY src/main/config/nginx-base.conf /etc/nginx/nginx.conf
COPY src/main/html/* /etc/nginx/html/
COPY src/main/confd/conf.d/* /etc/confd/conf.d/
COPY src/main/confd/templates/* /etc/confd/templates/
COPY target/confd src/main/scripts/startup.sh /usr/local/bin/

RUN  chmod +x /usr/local/bin/confd /usr/local/bin/startup.sh

CMD "/usr/local/bin/startup.sh"
