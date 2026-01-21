# Sensor Reading API

A Spring Boot REST API application for managing and querying sensor readings from various IoT devices.

## Overview

This application provides a RESTful API to submit sensor readings and retrieve them with filtering and pagination capabilities. It uses an in-memory H2 database for data persistence and provides endpoints to manage sensor data by device ID and timestamp ranges.

## Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use the included Maven wrapper)

## How to Run the Application

### Option 1: Using Maven Wrapper (Recommended)

#### On Linux/Mac:
```bash
./mvnw spring-boot:run
```

#### On Windows:
```cmd
mvnw.cmd spring-boot:run
```

### Option 2: Using Maven

```bash
mvn spring-boot:run
```

### Option 3: Build and Run JAR

```bash
# Build the application
./mvnw clean package

# Run the JAR file
java -jar target/sensor-reading-*.jar
```

The application will start on **http://localhost:8080** by default.

## API Endpoints

### 1. Submit a Sensor Reading

**POST** `/sensorreading/readings`

Submit a new sensor reading. 

**Request Body:**
```json
{
  "device_id": "sensor-001",
  "timestamp": "2026-01-21T10:30:00Z",
  "value": 23.5
}
```

**Validation Rules:**
- `device_id`: Required, cannot be blank
- `value`: Required, must be a positive number (> 0)
- `timestamp`: Optional, must be in ISO 8601 format if provided

**Response:**
- **201 Created** - "Success"
- **400 Bad Request** - Invalid device_id or timestamp format
- **422 Unprocessable Entity** - Invalid sensor value

---

### 2. Get Sensor Readings

**GET** `/sensorreading/readings`

Retrieve sensor readings with optional filtering and pagination. 

**Query Parameters:**
- `device_id` (required): The device identifier
- `from` (optional): Start timestamp in ISO 8601 format
- `to` (optional): End timestamp in ISO 8601 format
- `page` (optional): Page number, default = 0
- `page_size` (optional): Number of results per page, default = 20, max = 100

**Examples:**

Get all readings for a device:
```
GET /sensorreading/readings?device_id=sensor-001
```

Get readings within a date range:
```
GET /sensorreading/readings?device_id=sensor-001&from=2026-01-01T00:00:00Z&to=2026-01-21T23:59:59Z
```

Get paginated results:
```
GET /sensorreading/readings?device_id=sensor-001&page=1&page_size=50
```

**Response:**
```json
{
  "page": 0,
  "page_size": 20,
  "total": 100,
  "data": [
    {
      "device_id": "sensor-001",
      "timestamp": "2026-01-21T10:30:00Z",
      "value": 23.5
    }
  ]
}
```

---

### 3. Get All Sensor Readings

**GET** `/sensorreading/getall`

Retrieve all sensor readings (no pagination or filtering).

**Response:**
```json
[
  {
    "device_id": "sensor-001",
    "timestamp": "2026-01-21T10:30:00Z",
    "value": 23.5
  }
]
```

## Assumptions Made

1. **Data Storage**: The application uses an in-memory H2 database. Data will be lost when the application restarts. For production use, a persistent database (PostgreSQL, MySQL, etc.) should be configured. 

2. **Timestamp Format**: All timestamps follow ISO 8601 format (e.g., `2026-01-21T10:30:00Z`). The application validates this format on input.

3. **Sensor Value Constraints**: Sensor values must be positive numbers. Zero and negative values are rejected.

4. **Device ID**: Device IDs are treated as strings and can be any non-blank value. No pre-registration of devices is required.

5. **Date Range Queries**: When using `from` and `to` parameters, both must be provided. If only one is provided, the application treats the query as a simple device ID search.

## Improvements with More Time

### 1. **Comprehensive Testing**
- Unit tests for service layer (`SensorReadingService`)
- Unit tests for repository queries
- Unit tests for `DateUtils` validation
- Integration tests for API endpoints using MockMvc
- Test edge cases (boundary values, null handling, concurrent requests)
- Add test coverage reporting (JaCoCo)

### 2. **Enhanced Validation**
- Use `@Valid` annotation with proper exception handling
- Add custom validators for device_id format
- Implement global exception handler (`@ControllerAdvice`)
- Return standardized error response DTOs

### 3. **Database Improvements**
- Configure a production-ready database (PostgreSQL/MySQL)
- Add database migrations using Flyway or Liquibase
- Create database indexes on `device_id` and `timestamp` for query performance
- Add connection pooling configuration

## Technology Stack

- **Java 17+**
- **Spring Boot** - Application framework
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database
- **Maven** - Build tool
- **JUnit 5** - Testing framework

## Author

kaygt0810