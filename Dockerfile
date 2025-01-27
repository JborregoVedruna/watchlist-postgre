FROM maven:3.8.3-openjdk-17
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn package
#COPY /target/watchlist-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Ddb=jdbc:postgresql://192.168.1.170:5432/watchlist", "-Duser=joaquin", "-Dpass=Abcd1234", "-Ddbinitmode=never", "-jar", ".\target\watchlist-0.0.1-SNAPSHOT.jar"]