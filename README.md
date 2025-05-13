# SUBSCRIPTIONS 

Service for subscription management.

Упрощенная модель сервиса для управления подписками. 
---

## ⚡ Features- 
- Java
- PostgreSQL
- Swagger
- Logs in JSON format for ELK/console and plaintext logs for file

---

## ⚙ Requirements

### For Docker Compose

You can choose **one** of the following:

- **[Docker Desktop](https://www.docker.com)**  
  Recommended for most platforms. It includes both Docker Engine and Docker Compose.


- **[Docker Engine](https://docs.docker.com/engine/install/) + [Docker Compose](https://docs.docker.com/compose/)**  
  Install separately, typically on Linux servers.

> ✅ Java, Maven, and PostgreSQL are **not required** when running via Docker Compose.

### For Local Development (without Docker)
- 
- Java 17
- Maven 3.9+
- PostgreSQL installed locally or in Docker

---

## 🚀 Run with Docker Compose

1. Build and run:
   ```bash
   docker-compose up --build
   ```

2. Access the API: 
    - Swagger UI: http://localhost:8080/swagger-ui.html
    - User creation (POST): http://localhost:8080/v1/users
    - Get user info (GET): http://localhost:8080/v1/users/{userId}
    - User update (PUT): http://localhost:8080/v1/users/{userId}
    - Delete user (DELETE): http://localhost:8080/v1/users/{userId}
    - Add subscription to user (POST): http://localhost:8080/v1/users/{userId}/subscriptions
    - Get user's subscriptions (GET): http://localhost:8080/v1/users/{userId}/subscriptions
    - Delete user's subscription (DELETE): http://localhost:8080/v1/users/{userId}/subscriptions/{subscriptionId}
    - Get top-3 most popular subscriptions (GET): http://localhost:8080/v1/subscriptions/top- 

---

## 🪖 Run locally (without Docker)

1. Set up PostgreSQL locally and create a `subscriptions` database.

2. Replace in `src/main/resources/application.properties` this line:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/subscriptions
   ```

3. Run with Maven:
    - **Standard launch**
      ```bash
      mvn spring-boot:run
      ```  
   

4. Alternatively, build and run the JAR:
    - **Build the JAR**
      ```bash  
      mvn clean package  
      ```  

    - **Run (default profile)**
      ```bash  
      java -jar target/subscriptions-0.0.1-SNAPSHOT.jar      

---

## ✍ Notes
- The application architecture is simplified for demonstration purposes.
- Additional features like improved and expanded business logic can be added if needed.

---

## 📄 Useful Paths
- Swagger docs: `/v1/api-docs`
- Swagger UI: `/swagger-ui`
- API base: `/v1`

---

## 🧪 Testing 

To run all tests (unit and integration), use:
```bash  
  mvn test
```

---

## ⚖ License
Apache 2.0

---

## 📊 Version
Defined by `@project.version@` in Maven.
