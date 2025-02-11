# SolarWatch

SolarWatch is a comprehensive full-stack application that provides sunset and sunrise data for any city and date through a REST API. It features a backend that interacts with a sunset and sunrise data source and a frontend for user interaction. The application is fully containerized with Docker for easy deployment and scalability.

## Table of Contents
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Docker Commands](#docker-commands)
- [Contact](#contact)

## Tech Stack
- **Backend:** Java, Spring Boot
- **Frontend:** React, Vite
- **Containerization:** Docker

## Prerequisites
Ensure that you have the following installed on your machine:
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Installation
1. Clone the project to your local machine:
   ```sh
   git clone https://github.com/bmatthun/solarwatch.git
   cd solarwatch
   ```
2. Build and start the application using Docker Compose:
   ```sh
   docker-compose up --build
   ```
   This command will build both backend and frontend services if needed and start them.

3. Verify that both services are running:
   - **Backend:** Accessible at [http://localhost:8080](http://localhost:8080)
   - **Frontend:** Accessible at [http://localhost:5173](http://localhost:5173)

## Usage
- To start the entire application stack:
  ```sh
  docker-compose up
  ```
- To stop the services:
  ```sh
  docker-compose down
  ```
- **Admin Access:** Certain features require administrator access. Use the following credentials:
  - **Username:** admin
  - **Password:** admin

## Docker Commands
While Docker Compose manages most commands for the multi-container setup, here are some additional commands:
- **Rebuild images:**
  ```sh
  docker-compose up --build
  ```
- **View logs:**
  ```sh
  docker-compose logs
  ```
- **Stop containers:**
  ```sh
  docker-compose down
  ```

## Contact
For any inquiries or support, feel free to contact the repository owner.

---
This project is open source and contributions are welcome!

