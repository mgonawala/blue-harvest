# blue-harvest

mvn spring-boot:run

http://localhost:8585


List of apis:

http://localhost:8585/swagger-ui.html

To run sonar report:
sonar:sonar -Dsonar.host.url=http://localhost:9000

docker pull jenkins
docker tag jenkins:latest 852666377066.dkr.ecr.us-east-1.amazonaws.com/jenkins:latest


c424b0b21bf74b31a5664483096f2ddf


FROM jenkins/jenkins:lts
MAINTAINER miiro@getintodevops.com
USER root

# Install the latest Docker CE binaries
RUN apt-get update && \
    apt-get -y install apt-transport-https \
      ca-certificates \
      curl \
      gnupg2 \
      software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable" && \
   apt-get update && \
   apt-get -y install docker-ce
