FROM openjdk:8-jre
ENV IP_FINDER_ADDRS 0.0.0.0:47500
ADD ./target/lab4-1.0-SNAPSHOT.jar /app/lab4-1.0-SNAPSHOT.jar
CMD ["java", "-Xdebug", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app/lab4-1.0-SNAPSHOT.jar"]