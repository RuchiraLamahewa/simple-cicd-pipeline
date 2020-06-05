# Simple CI-CD Pipeline
This is a sample repository for CI-CD pipeline. In this, I talk about how to build a simple CI-CD pipeline without Jenkins servers.

![alt text](https://github.com/SidathWeerasinghe/simple-cicd-pipeline/blob/master/Pipeline_Integration_Flow.jpeg)

# Prerequisites

* Github account (https://github.com)
* Docker Hub account(https://hub.docker.com)
* Application deployed server

# Steps
Please follow the below steps to setup simple CI-CD pipeline with your on-premise servers. 

## Step 1
First, create a Docker file for your codebase to deploy that in a docker container. 

Create a file as "Dockerfile" in the code repository home and add the below content or you can write the docker scripts with respect to your application. 

```docker
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
```

## Step 2

Install docker in your on-premise server. 

```bash
sudo apt-get update
sudo apt-get remove docker docker-engine docker.io
sudo apt install docker.io
sudo systemctl start docker
sudo systemctl enable docker
```

To verification 

```bash
docker --version
docker ps
```

## Step 3

Install webhook to the on-premise server. 

```bash
https://packages.debian.org/sid/amd64/webhook/download
wget http://ftp.cn.debian.org/debian/pool/main/w/webhook/webhook_2.6.9-1+b1_amd64.deb
sudo dpkg -i webhook_2.6.9-1+b1_amd64.deb
```

## Step 4

Create a repository on Docker Hub


## Step 5

Add the secrets to GitHub Secrets under repository settings.

* DOCKER_REPO - address of your repository at Docker Hub
* DOCKER_USER - Docker Hub username
* DOCKER_PASS - Docker Hub password


## Step 6

Create a GitHub workflow. 

Log into Github -> go to your repository -> Action -> New Workflow -> set up a workflow yourself

  
```git
name: CI-CD Pipeline

on:
  push:
    branches: [ master ]

jobs:
  build:
    name: Building the codebase
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 1.8
      uses: actions/setup-java@v1
      with:
        java-version: 1.8
    - name: Build with Maven
      run: mvn -B package --file pom.xml

  docker:
    name: Publish - Docker Hub
    runs-on: ubuntu-16.04
    needs: [build]

    env:
      REPO: ${{ secrets.DOCKER_REPO }}
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 1.8
        uses: actions/setup-java@v1
        with:
          java-version: 1.8
      - name: Login to Docker Hub
        run: docker login -u ${{ secrets.DOCKER_USER }} -p ${{ secrets.DOCKER_PASS }}
      - name: Build Docker image
        run: docker build -t $REPO:latest .
      - name: Publish Docker image
        run: docker push $REPO

```

#Step 7

Goto the Github action tab and see the logs on the workflow executing. 

After execution has done. Go to the Docker Hub and check the docker image under your repository. 
