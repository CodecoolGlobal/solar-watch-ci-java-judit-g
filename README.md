SolarWatch
This project is a comprehensive full-stack application that provides sunset and sunrise data for any city and date through a REST API. The application includes a backend that interacts with a sunset and sunrise data source, as well as a frontend for user interaction. The app is fully containerized with Docker for easy deployment and scalability.

Table of Contents
Tech Stack
Prerequisites
Installation
Usage
Docker Commands
Contact
Tech Stack
Java
Spring Boot
React
Vite
Docker
Prerequisites
Docker and Docker Compose must be installed on your machine.
Installation
Clone the project to your local machine:

git clone https://github.com/bmatthun/solarwatch.git
cd solarwatch
Build and start the application using Docker Compose:

docker-compose up --build
This command will build both backend and frontend services if needed, and start them in detached mode.

Verify that both services are running:

Backend: Accessible at http://localhost:8080
Frontend: Accessible at http://localhost:5173
Usage
To start the entire application stack:
docker-compose up
To stop the services:
docker-compose down
Admin Access: Certain features require administrator access, which you can obtain by logging in with the username admin and password admin.

Docker Commands
While Docker Compose manages most commands for the multi-container setup, here are some additional commands:

Rebuild images: docker-compose up --build
View logs: docker-compose logs
Stop containers: docker-compose down
