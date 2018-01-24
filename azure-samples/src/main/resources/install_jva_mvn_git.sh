#!/bin/bash

# install Oracle Java8
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get update
echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | sudo debconf-set-selections
sudo apt-get install -y oracle-java8-installer
sudo apt-get install -y oracle-java8-set-default
source /etc/environment

# install Maven
sudo apt-get install -y maven
source /etc/environment

# install Git
sudo apt-get update
sudo apt-get install -y git