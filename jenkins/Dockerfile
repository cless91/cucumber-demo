FROM jenkins/jenkins:lts

USER root

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV DISPLAY=:0.0

# prepare apt repositories
RUN apt update
RUN apt install software-properties-common -y
RUN apt-add-repository "deb http://ftp.de.debian.org/debian sid main "
RUN apt update

# maven installation (needs to be updated for the latest maven version)
RUN apt install maven -y

# java 14 installation
COPY ./jdk-14.0.1_linux-x64_bin.deb /
RUN apt install libasound2 -y
RUN dpkg -i jdk-14.0.1_linux-x64_bin.deb

# other installs
RUN apt install firefox -y
RUN apt install xvfb -y
RUN apt install vim -y

# configuring virtual display for test recording
RUN nohup bash -c "Xvfb :0 -screen 0 1024x768x16 &"

#USER jenkins
#COPY --chown=jenkins:jenkins ./.ssh /var/jenkins_home/.ssh
COPY ./.ssh /root/.ssh
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt

# jenkins initialization material
ARG GITHUB_ACCESS_TOKEN
COPY ./jenkins-cli.jar /
COPY ./job-config.xml /
COPY ./github-credentials.xml /
COPY ./configure_jenkins.sh /
RUN sed -i "s|<password></password>|<password>$GITHUB_ACCESS_TOKEN</password>|" github-credentials.xml
COPY entrypoint-wrap.sh /entrypoint-wrap.sh

RUN ["chmod", "+x", "/jenkins-cli.jar"]
RUN ["chmod", "+x", "/entrypoint-wrap.sh"]
RUN ["chmod", "+x", "/configure_jenkins.sh"]
ENTRYPOINT ["/entrypoint-wrap.sh"]
