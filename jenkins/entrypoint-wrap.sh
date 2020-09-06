#! /bin/sh

Xvfb :0 -screen 0 1280x720x16 &

#exec /docker-entrypoint.sh "$@"
retval=255
until [ $retval -ne 255 ]
do
  /configure_jenkins.sh
  retval=$?
  >&2 echo "Jenkins is unavailable - sleeping";
  sleep 3;
 done &

exec /sbin/tini -- /usr/local/bin/jenkins.sh
