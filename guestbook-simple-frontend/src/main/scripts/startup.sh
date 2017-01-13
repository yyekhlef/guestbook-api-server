#!/bin/ash

echo "Testing runtime env"
echo
echo "content of /etc/nginx/html"
ls /etc/nginx/html
echo "------------------------------------"
echo "content of /etc/nginx/html/config"
ls /etc/nginx/html/config
echo "------------------------------------"

if /usr/local/bin/confd -onetime -backend env
  then
    echo
    echo "w00t w00t!! config has been generated, let's start nginx now"
    nginx -g 'daemon off;'
 else
    echo "You failed at starting confd and/or nginx properly"
fi