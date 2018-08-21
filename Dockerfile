
FROM maven:alpine
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
WORKDIR /usr/src/app
EXPOSE 8000
CMD ["mvn", "clean install"]