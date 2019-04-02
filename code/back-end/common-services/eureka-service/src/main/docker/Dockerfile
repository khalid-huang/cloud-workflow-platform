FROM openjdk:8-jre

VOLUME /tmp
ADD eureka-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8760
ENV JAVA_OPTS="\
-XX:+UnlockExperimentalVMOptions \
-XX:+UseCGroupMemoryLimitForHeap"
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT java ${JAVA_OPTS} -Djava.awt.headless=true  -Djava.security.egd=file:/dev/./urandom -jar /app.jar
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar

