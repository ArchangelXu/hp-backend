FROM openjdk:11-jdk
VOLUME /tmp
ARG PROFILE
ARG EXTRACTED
ENV PROFILE_ENV=$PROFILE
COPY ${EXTRACTED}/dependencies/ ./
COPY ${EXTRACTED}/spring-boot-loader/ ./
COPY ${EXTRACTED}/snapshot-dependencies/ ./
COPY ${EXTRACTED}/application/ ./
ENTRYPOINT java -Dspring.profiles.active=$PROFILE_ENV org.springframework.boot.loader.JarLauncher
