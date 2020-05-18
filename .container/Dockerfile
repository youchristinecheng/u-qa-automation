FROM maven:3.6.3-openjdk-8-slim

# VARIABLE TO PASSED
ENV PLATFORM= \
    BUILDVER= \
    APPNAME=

# Install packages.
RUN apt-get update && \
    apt-get install -y jq && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /opt/u-qa-automation

COPY . .

RUN mvn clean install compile  -DskipTests=true

CMD ./Script/auto_test_browserstack_appcenter_integrate.bash $PLATFORM $BUILDVER
