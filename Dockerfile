FROM amd64/amazoncorretto:17

ARG JAR_FILE=./build/libs/DoItTogether-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} /app/DoItTogether.jar

CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "DoItTogether.jar"]
