# LinkedIn Spring Application

This project is a LinkedIn-like application built using a Spring Boot backend and a modern React frontend powered by Vite. It includes features such as user authentication, feed management, and database interaction.

## Features

- **Backend**:
  - Spring Boot framework for building RESTful APIs.
  - Authentication and Authorization using JWT.
  - Feed functionalities for posts and comments.
  - Email service integration for notifications.

- **Frontend**:
  - Modern UI built with React and TypeScript.
  - Authentication pages (Login, Signup, Password Reset).
  - Dynamic Feed with posts and comments.

- **Database**:
  - Support for relational databases with Spring Data JPA.

- **Deployment**:
  - Docker Compose for containerized deployment.
  - Gradle for backend build and dependency management.

## Project Structure

- **Backend**: Located in `backend/`
  - `src/main/java`: Java source code.
  - `src/main/resources`: Configuration files.
  - `docker-compose.yml`: Docker setup for backend services.

- **Frontend**: Located in `frontend/`
  - `src/`: React components and pages.
  - `public/`: Static assets like icons and logos.
  - `.env`: Environment configurations.

## Prerequisites

- **Backend**:
  - JDK 21+
  - Gradle
  - Docker and Docker Compose

- **Frontend**:
  - Node.js 16+
  - npm or yarn

## Getting Started

### Backend Setup

1. Navigate to the `backend/` directory:
   ```bash
   cd backend
   ```

2. Build the backend:
   ```bash
   ./gradlew build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. Alternatively, use Docker Compose:
   ```bash
   docker-compose up
   ```

### Frontend Setup

1. Navigate to the `frontend/` directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Run the application:
   ```bash
   npm run dev
   ```

4. Access the frontend at `http://localhost:5173`.

## Ports

- **Backend**: Default port `8080` (can be configured in `application.properties`).
- **Frontend**: Default port `5173` (Vite development server; can be changed in `.env`).

## API Documentation

The backend exposes RESTful APIs for all major functionalities. You can explore the endpoints using a tool like Postman or Swagger.

## Environment Variables

### Backend

- Configure in `backend/src/main/resources/application.properties`.

### Frontend

- Configure in `frontend/.env`.

---

Happy coding! 🎉

