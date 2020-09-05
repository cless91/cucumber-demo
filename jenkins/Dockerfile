FROM jenkins/jenkins:lts

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

COPY --chown=jenkins:jenkins ./.ssh /var/jenkins_home/.ssh
COPY ./.ssh /root/.ssh
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt


