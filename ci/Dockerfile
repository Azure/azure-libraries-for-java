FROM openjdk:8u121-jdk-alpine

LABEL a01.product="javasdk"
LABEL a01.index.schema="v2"

LABEL a01.env.AZURE_CLIENT_ID="secret:client"
LABEL a01.env.AZURE_CLIENT_SECRET="secret:key"
LABEL a01.env.AZURE_TENANT_ID="secret:tenant"
LABEL a01.env.AZURE_SUBSCRIPTION_ID="secret:subscription"
LABEL a01.env.AZURE_TEST_MODE="arg-mode"

RUN apk add --no-cache curl tar bash

ARG MAVEN_VERSION=3.5.2
ARG USER_HOME_DIR="/root"
ARG SHA=707b1f6e390a65bde4af4cdaf2a24d45fc19a6ded00fff02e91626e3e42ceaff
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha256sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

COPY . /azure-libraries-for-java
COPY ./ci/app /app

RUN mvn install -DskipTests=true -f /azure-libraries-for-java/pom.xml -Dmaven.repo.local=/repository -q

CMD /app/a01droid