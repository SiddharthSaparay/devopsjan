FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY src/com ./com
RUN javac com/wip/MainClass.java
CMD ["java", "com.wip.MainClass"]
