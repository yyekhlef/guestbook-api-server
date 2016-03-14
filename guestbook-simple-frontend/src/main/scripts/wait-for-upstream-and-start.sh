#!/bin/ash

max_wait=${MAX_WAIT:-10}
target_vip=${TARGET_VIP:-guestbook-rest-server}

t=0
resolved=0

echo "Let's wait till DNS resolution is ready (max_wait=${max_wait})"
echo

while [ $t -lt ${max_wait} ]
do
   echo $t
   if ping -c 1 ${target_vip}
   then
      resolved=1
      break
   fi
   echo
   t=`expr $t + 1`
   sleep 1
done

if [ ${resolved} -eq 0 ]
then
    echo "Could not resolve ${target_vip} in ${max_wait} seconds, exiting"
    exit 722
fi

echo
echo "w00t w00t!! ${target_vip} is reachable, let's start nginx now"
nginx -g 'daemon off;'
