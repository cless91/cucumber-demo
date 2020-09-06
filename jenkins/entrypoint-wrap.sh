#! /bin/sh

Xvfb :0 -screen 0 1280x720x16 &

#exec /docker-entrypoint.sh "$@"
until /configure_jenkins.sh; do
  >&2 echo "Jenkins is unavailable - sleeping";
  sleep 3;
 done &

exec /sbin/tini -- /usr/local/bin/jenkins.sh
