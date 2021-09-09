FROM openjdk:15-slim-buster
WORKDIR /app
ADD MathServer.java /app
ADD MathServerClass.java /app
ADD ProgramaServidor.java /app
RUN javac MathServer.java
RUN javac MathServerClass.java
RUN javac ProgramaServidor.java
CMD ["java","ProgramaServidor"]
