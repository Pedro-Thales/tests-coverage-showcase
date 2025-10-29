# Test Coverage Showcase
##### **You can have pretty bad tests even with 100% coverage.**
###### My intention is to show why you shouldn't use coverage as a quality measure

![img.png](img.png)

###### _I've extrapoleted some things of course, but you get the point ;)_ 


It includes instructions to run and build the application.

---

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Building the Application](#building-the-application)
- [Building a Docker Image](#building-a-docker-image)

---

## Requirements

Ensure you have the following installed on your system:

- **Java 21**
- **Maven** (version 3.9.0 or later)
- **Docker**

---

## Installation

1. Clone the repository:

    ```bash
    git clone <repository-url>
    cd <repository-folder>
    ```

2. Verify the Maven dependencies:

    ```bash
    mvn dependency:resolve
    ```

---

## Running the Application

### Local Development


#### It'll be necessary that docker is installed and running. Since this project uses docker compose file to auto start the mysql database.

Run the application locally with:

```bash 
mvn spring-boot:run
```

Alternatively, you can build and execute the jar file:

```bash
mvn clean package
java -jar target/calculator-api-tests-showcase:0.0.1.jar
```

### Configuration
Modify the configuration properties in src/main/resources/application.properties or create a src/main/resources/application.yml file for custom settings.

### Building the Application
To build the application, run:

```bash
mvn clean package
```
The compiled .jar file will be located in the target/ directory.

### Building a Docker Image
This project includes configuration to use the Maven plugin to create a Docker image. 


Run the Maven command:

```bash
mvn spring-boot:build-image
```
The plugin will create a Docker image using the name and version specified in the pom.xml file.

Verify the image:

```bash
docker images
```
### Running the Docker Container
Run the application inside a Docker container:

```bash
docker run -d -p 8080:8080 <image-name>
```


## Happy coding! 🎉




