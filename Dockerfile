FROM maven:3.8.4-openjdk-17

COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

ENTRYPOINT ["mvn", "spring-boot:run"]
