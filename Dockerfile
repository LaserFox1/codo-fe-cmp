#------------------------------------------------------------------------------
FROM remote-registry.artifactory.prod.lkw-walter.com/maven:3.6.3-jdk-11-slim as builder

# 1. initialise
ENV M2_HOME=/usr/share/maven
SHELL ["/bin/bash", "-c"]
WORKDIR /usr/project
COPY settings.xml /root/.m2/settings.xml

# 2. only copy pom.xmls and resolve dependencies (using the docker-cache)
COPY pom.xml /usr/project/
RUN mvn --batch-mode --no-transfer-progress -T 8 -DexcludeGroupIds=com.lkww.codo dependency:resolve-plugins dependency:resolve || echo "Dependency-resolve failed because https://issues.apache.org/jira/browse/MDEP-556. Ignoring for now..."

# 3. now copy the remaining sources and start the build with tests.
COPY ./src/ /usr/project/src/
ARG APP_VERSION=1.0.0-SNAPSHOT
## INPUT: get BUILD_NAME and BUILD_NUMBER from pipline, needed to publish to artifactory
RUN mvn --batch-mode --no-transfer-progress versions:set -DnewVersion=$APP_VERSION -DgenerateBackupPoms=false

## disable GA for reportportal
ENV AGENT_NO_ANALYTICS=true
ARG REPORTPORTAL_ARGS
ENV BUILD_COMMAND="mvn --batch-mode --no-transfer-progress -Psonar-coverage $REPORTPORTAL_ARGS install \
                       && echo 'SUCCESS' > target/build-result.txt \
                       || echo 'FAILED'  > target/build-result.txt"
RUN echo "$BUILD_COMMAND" && eval "$BUILD_COMMAND"

# -------------------------------------------------------------------------------------------------------------
FROM registry.artifactory.prod.lkw-walter.com/basis/spring-boot-basis:1.0.5

COPY --from=builder /usr/project/target/codo-codo-fe-cmp-*.jar ./app.jar
