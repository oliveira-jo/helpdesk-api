# Helpdesk Backend

> Status: Finished V.1

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-000?style=for-the-badge&logo=postgresql)
![Vscode](https://img.shields.io/badge/Vscode-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white) 


A RESTful Help Desk API built with Java and Spring Boot for managing support tickets, users, and customer service operations, and a RESTful API for integration with frontends or other services.

## 🚀 Technologies
- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Maven
- Hibernate / JPA
- PostgreSQL
- Swagger OpenAPI

## 📋 Features
- User authentication and authorization with JWT
- Create, update, and manage support tickets
- Ticket status tracking
- Role-based access control
- Request validation
- Global exception handling
- RESTful architecture with JSON responses
- Swagger/OpenAPI documentation

## 🏗️ Architecture
- Layered architecture: routes → controllers → services → repositories
- Relational database for persistent storage
- Stateless REST API suitable for horizontal scaling
- Optional background jobs for notifications

## 🔧 Instalação
1. Clone the repository:
   git clone https://github.com/oliveira-jo/helpdesk-api
2. Install dependencies:
   cd helpdesk
   yarn install
3. Configure environment variables:
   - Copy .env.example to .env and set DB credentials, JWT secret, and ports.

## ▶️ Como executar
- Development:
  yarn dev
- Build and run:
  npm run build
  npm start

## 📡 Endpoints da API
Note: replace :id with resource ids and set Authorization header with a valid Bearer token.

- POST /api/auth/login — Authenticate and receive JWT
- POST /api/users — Create a user (admin)
- GET /api/users/:id — Get user details
- POST /api/tickets — Create a ticket
- GET /api/tickets — List tickets (supports ?page=&limit=&status=&assignedTo=)
- GET /api/tickets/:id — Get ticket details
- PATCH /api/tickets/:id — Update ticket (status, assignee, comments)
- POST /api/tickets/:id/comments — Add a comment
- GET /api/reports/summary — Basic reporting (admin)

Include request/response examples in API docs or use an OpenAPI/Swagger spec file for full references.

## 🔐 Segurança
- Use HTTPS in production.
- Authenticate API with JWT tokens and enforce token expiry.
- Apply role-based access control for sensitive endpoints.
- Validate and sanitize input to prevent injection.
- Store secrets (JWT secret, DB password) in environment variables or a secrets manager.

## 🧪 Testes
- Unit tests with Jest.
- Integration tests with Supertest hitting the API routes.
- Run tests:
  npm test
  or
  yarn test

## 📁 Project Structure
Overview of the typical repository structure:
````
src
 ┣ main
 ┃ ┣ java
 ┃ ┃ ┗ com.oliveira.helpdesk
 ┃ ┃    ┣ configuration
 ┃ ┃    ┣ controller
 ┃ ┃    ┣ dto
 ┃ ┃    ┣ domain
 ┃ ┃    ┣ enums
 ┃ ┃    ┣ exception
 ┃ ┃    ┣ mapper
 ┃ ┃    ┣ repository
 ┃ ┃    ┣ security
 ┃ ┃    ┣ service
 ┃ ┃    ┗ utils
 ┃ ┗ resources
 ┃    ┣ db
 ┃      ┣ migration
 ┃    ┣ application.yml

````

## 👨‍💻 Author
- Name: Jonathan Oliveira
- Contact: devjoliveira@gmail.com
-

