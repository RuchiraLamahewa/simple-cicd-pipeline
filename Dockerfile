FROM maven:3.6.3-jdk-11-slim

ENV PROJECT_HOME /mnt/project

# add the directory to the path
ADD . /mnt/project

#Add a directory for logs
RUN mkdir -p /mnt/project/logs

# Run maven
RUN cd /mnt/project && mvn clean install

# Expose the port
EXPOSE 8080

# Run the jar file
CMD ["java","-jar","-DlogPath=/mnt/project/logs","/mnt/project/target/simplecicd-1.0.0.jar"]