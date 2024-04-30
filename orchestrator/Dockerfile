FROM openjdk:21 AS BUILD_IMAGE
RUN microdnf install findutils
ENV APP_HOME=/root
RUN mkdir -p $APP_HOME/src/main/java
WORKDIR $APP_HOME

COPY ./build.gradle ./gradlew ./gradlew.bat $APP_HOME
COPY gradle $APP_HOME/gradle
COPY ./src $APP_HOME/src/

RUN ./gradlew clean build -x test

RUN ls -la /root

FROM openjdk:21
WORKDIR /app/
COPY --from=BUILD_IMAGE /root/build/libs/*.jar ./orchestrator.jar
EXPOSE 8080
CMD ["java","-jar","orchestrator.jar"]