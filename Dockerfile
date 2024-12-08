FROM amd64/amazoncorretto:17

ARG JAR_FILE=build/libs/DoItTogether-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} DoItTogether.jar

CMD ["java", "-jar", "-Duser.timezone=Asia/Seoul", "DoItTogether.jar"]
