# microservices-parent
Welcome to my Microservices project! This repository serves as a parent project for developing microservices using Spring Boot, Spring Cloud, RabbitMQ, Zipkin, Docker, Docker Compose, and Kubernetes.
It provides a foundation and common configurations to build scalable and resilient microservice architectures.

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Features](#features)
- [Architecture](#architecture)
- [Usage](#usage)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#licence)
- [References](#references)

### Project Overview
The Microservices Parent project is a comprehensive repository that enables developers to quickly bootstrap and develop microservices-based applications. It promotes modularity, scalability, and maintainability through the use of microservice architectural patterns and industry-standard technologies.

### Technologies used
* Spring Boot 3
* Java 17
* Spring Security Oauth2
* Spring Cloud Gateway
* Spring Cloud Config
* Spring Web
* Spring Data JPA
* Spring Data Mongo
* Microservices
* Elastic Stack
* Prometheus & Grafana
* Maven
* RabbitMQ
* Zipkin Server
* Docker
* Docker-compose
* Kubernetes
* IntelliJ

### Getting Started
To get started with the Microservices project, follow these steps:
1. Clone the repository using the below command:
   * `git clone https://github.com/mohjyehia99/microservices-parent.git`
2. Navigate to the project directory:
   * `cd microservices-parent`
3. Customize the configurations and add your microservices modules.
4. Build and run the project using your preferred IDE or the command line.

For detailed instructions on how to use specific features or deploy the application, refer to the relevant sections in this README.

### Features
- **Modular Architecture**: The project follows a modular architecture that allows for independent development and deployment of microservices.
- **API Gateway**: Spring cloud gateway is used as a gateway for all the microservices.
- **Service Discovery**: Spring Cloud provides service discovery capabilities using Netflix Eureka.
- **Load Balancing**: Microservices can be load balanced using Ribbon or Spring Cloud LoadBalancer.
- **Oauth2 Security**: Spring boot oauth2 resource server is used for providing authentication and authorization to each microservice.
- **Fault Tolerance**: Resilience4J library is used to handle faults and provide resilience in microservices communication.
- **Distributed Tracing**: Zipkin is integrated for distributed tracing across microservices.
- **Messaging**: RabbitMQ is used for asynchronous communication between microservices.
- **Observability**: Using the elastic stack (elasticsearch, logstash, filebeat & kibana) along with prometheus & grafana for observability.
- **Containerization**: Docker and Docker Compose are utilized for containerization and local development.
- **Orchestration**: Kubernetes can be used for deploying and managing microservices in a production environment.

### Architecture
// TODO
The Microservices Parent project follows a microservice architecture, which consists of multiple loosely coupled services that communicate with each other through APIs. The architecture promotes scalability, fault tolerance, and independent development and deployment of services.

### Usage
// TODO
Describe here how to use the project and any important details developers should be aware of. Provide examples or code snippets if necessary.

### Deployment
// TODO
- The Microservices Parent project can be deployed in different environments, including local development, docker environment, and kubernetes.
- The deployment process may vary depending on the target environment and the technologies used.
- Here are a few general steps to consider:
  - Local Deployment:
    - Using the docker compose file `docker-compose-local.yml` as this file has only the containers required for running the microservices locally without observability tools and without the elastic stack:
      - `docker-compose -f docker-compose-local.yml up -d`
  - Docker Deployment:
    - Using the docker compose file `docker-compose.yml` as this file has all the containers included in the `docker-compose-local.yml` along with the elastic stack & observability tools:
      - `docker-compose -f docker-compose.yml up -d`

### Contributing
Contributions to the Microservices Parent project are welcome! If you find any issues or have suggestions for improvements, please submit an issue or a pull request. For more information, see our Contribution Guidelines.

### Licence
This project is licensed under the MIT License. See the LICENSE file for the full text of the license.

### References
// TODO