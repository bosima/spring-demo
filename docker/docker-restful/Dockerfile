FROM openjdk:17.0.2-slim-buster
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/bosima/springdemo-restful.jar
ENTRYPOINT ["java", "-jar", "/usr/share/bosima/springdemo-restful.jar"]
EXPOSE 8080
