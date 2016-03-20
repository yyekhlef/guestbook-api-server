FROM nginx:stable-alpine
MAINTAINER Christophe Furmaniak <christophe.furmaniak@gmail.com>

LABEL LOOZ_VERSION=v0.0.8

COPY src/main/config/nginx-base.conf /etc/nginx/nginx.conf
COPY src/main/html/* /etc/nginx/html/
COPY src/main/scripts/wait-for-upstream-and-start.sh /

CMD ["/bin/ash","/wait-for-upstream-and-start.sh"]
