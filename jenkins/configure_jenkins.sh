#! /bin/bash

java -jar jenkins-cli.jar -s http://localhost:8080/ -webSocket create-job contact-app < job-config.xml
java -jar jenkins-cli.jar -s http://localhost:8080 create-credentials-by-xml  system::system::jenkins _ < heroku-credentials.xml 
java -jar jenkins-cli.jar -s http://localhost:8080 create-credentials-by-xml  system::system::jenkins _ < github-credentials.xml
