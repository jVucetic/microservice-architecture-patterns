# Microservice architecture patterns

### Appointment Management Service

This is a sample Spring Boot microservice managed with Gradle. Intention of this service is managing lifecycle of 
appointments.


### Appointment Notification Service

This is a sample Spring Boot microservice managed with Gradle. Intention of this service is sending
notifications or reminders related to appointments.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK) 21** installed on your local machine. 
You can download it from [OpenJDK](https://adoptopenjdk.net/) or [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html).
- **Gradle** installed on your local machine. 
You can download it from the [Gradle website](https://gradle.org/install/).
- **Docker** installed on your local machine.
    - For Windows and macOS, you can install [Docker Desktop](https://www.docker.com/products/docker-desktop/), which includes Docker Compose.
    - For Linux, follow the official [Docker installation guide](https://docs.docker.com/get-docker/).
    - Make sure Docker Compose is also installed, which typically comes with Docker Desktop.


## Getting Started

To get a local copy up and running, follow these simple steps:

1. Clone the repository:

   ```
   git clone https://github.com/jVucetic/microservice-architecture-patterns.git

2. Navigate to the docker compose config directory and run:

    ```
    docker compose up -d --build
