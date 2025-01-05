# Minimum Coins Calculator Service

A backend service that calculates the minimum number of coins required to make up a target amount.

## Description

This application provides a REST API endpoint that helps determine the minimum number of coins needed to make up a given amount. It's built using Java and can be run using Docker for easy deployment.

## Prerequisites

* Docker
* Java 11 or higher (for development)
* Maven (for development)

## Getting Started

### Docker Setup

1. Build the Docker image:
```bash
docker build -t coins-calculator .
```

2. Run the container:
```bash
docker run -p 8080:8080 -p 8081:8081 coins-calculator
```

The application will be available at:
* Main application: http://localhost:8080
* Admin interface: http://localhost:8081

### Local Development Setup

1. Build the project:
```bash
mvn clean package
```

2. Run the application:
```bash
java -jar target/2025_Sri_Guru_Aakash_Darsi_BE-1.0-SNAPSHOT.jar server config.yml
```

## Configuration

The application uses a `config.yml` file for configuration. Make sure this file exists in your project root directory.

## Docker Configuration Details

The Dockerfile contains the following configuration:
```dockerfile
FROM adoptopenjdk/openjdk11:latest
EXPOSE 8080 8081
RUN mkdir app
COPY target/2025_Sri_Guru_Aakash_Darsi_BE-1.0-SNAPSHOT.jar app
COPY config.yml app
WORKDIR app
CMD ["java","-jar","2025_Sri_Guru_Aakash_Darsi_BE-1.0-SNAPSHOT.jar","server","config.yml"]
```

## API Usage

### Calculate Minimum Coins
Endpoint: `POST /api/calculate`

Request Body:
```json
{
   "targetAmount": 43
}
```

Example using curl:
```bash
curl -X POST \
  http://localhost:8080/api/calculate \
  -H 'Content-Type: application/json' \
  -d '{"targetAmount": 43}'
```

Response Format:
```json
{
   "coinCounts": [
      {
         "denomination": 1.0,
         "count": 1
      },
      {
         "denomination": 2.0,
         "count": 1
      },
      {
         "denomination": 10.0,
         "count": 4
      }
   ],
   "message": "Calculation successful",
   "success": true
}
```

Example using Postman:
1. Set method to `POST`
2. Enter URL: `http://localhost:8080/api/calculate`
3. Set Headers: `Content-Type: application/json`
4. In Body tab, select 'raw' and 'JSON', then enter:
   ```json
   {
       "targetAmount": 43
   }
   ```

## Health Check

You can check the application's health at:
```
http://localhost:8081/healthcheck
```

## Troubleshooting

If you encounter issues:

1. Check if ports 8080 and 8081 are available
2. Ensure Docker is running
3. Verify the config.yml file exists and is properly formatted
4. Check application logs:
```bash
docker logs <container-id>
```