FROM adoptopenjdk/openjdk8:slim
VOLUME /tmp
ADD bpm-management-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8781
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
