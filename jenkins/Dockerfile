FROM jenkins/jenkins:lts

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

USER root
RUN apt update

# maven installation (needs to be updated for the latest maven version)
RUN apt install maven -y

# java 14 installation
COPY ./jdk-14.0.1_linux-x64_bin.deb /
RUN apt install libasound2 -y
RUN dpkg -i jdk-14.0.1_linux-x64_bin.deb

# firefox installation for cucumber
RUN apt install firefox-esr -y

#USER jenkins
COPY --chown=jenkins:jenkins ./.ssh /var/jenkins_home/.ssh
COPY ./.ssh /root/.ssh
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt


