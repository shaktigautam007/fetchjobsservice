# Job Service Application

## Overview
The Job Service Application fetches job data from external portals like LinkedIn and provides data in simple json provided below . The service is resilient and fault-tolerant, using Resilience4j for handling rate-limiter, and circuit breakers.

## Features
- Fetch jobs from external APIs (e.g., LinkedIn) for multiple job types:
    - Saved Jobs
    - Applied Jobs
    - Archived Jobs
    - In-Progress Jobs
- Resilience4j is used  to to handle API failures and rate limits.
- Fault-tolerant fallback  to return default null  data when external services are unavailable again using resiliance4j also logs service error .
- Asynchronous processing using `CompletableFuture`.

## Technologies Used
- **Java 21**
- **Spring Boot 3.5.0-SNAPSHOT**
- **WebFlux** for non-blocking HTTP calls using Webclient
- **Resilience4j** for rate limiting, retries, and circuit breakers
- **Lombok** for boilerplate code reduction
- **Maven** for dependency management

## Getting Started

### Prerequisites
- Java 21 
- Maven 3.X 

### Installation
1. Clone job repository repository:
   ```bash
   git clone https://github.com/shaktigautam007/fetchjobsservice.git
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```



### Configuration
Add the following properties to your `application.properties`:

```properties
spring.application.name=jobservice
springdoc.api-docs.enabled=true
# Resilience4j Rate Limiter Configuration
resilience4j.ratelimiter.instances.default.limitForPeriod=4
resilience4j.ratelimiter.instances.default.limitRefreshPeriod=500ms
resilience4j.ratelimiter.instances.default.timeoutDuration=1000ms


## Sample API Request
### Endpoint
```http
GET http://localhost:8080/api/v1/jobs?source=LINKEDIN
Swagger Endpoint http://localhost:8080/swagger-ui/index.html#/
```

### Headers
```json
{
  "x-csrf-token": "<auth-token>",
  "cookie": "<cookie>"
}
```

### Response
```json
{
  "saved": {
    "jobs": [
      {
        "role": "Java Backend Lead Software Engineer",
        "company": "JPMorganChase",
        "location": "Glasgow (On-site)",
        "appliedOn": "Posted 1w ago"
      },
      {
        "role": "Senior Associate, Full-Stack Engineer",
        "company": "BNY",
        "location": "Greater Manchester (On-site)",
        "appliedOn": "Be an early applicant"
      }
    ]
  },
  "applied": {
    "jobs": [
      {
        "role": "Senior Software Engineer",
        "company": "Roku",
        "location": "Cambridge (On-site)",
        "appliedOn": "Applied 26d ago"
      }
    ]
  },
  "inProgress": {
    "jobs": []
  },
  "archived": {
    "jobs": [
      {
        "role": "Java Lead Software Engineer",
        "company": "JPMorganChase",
        "location": "Glasgow (On-site)",
        "appliedOn": "No longer accepting applications"
      }
    ]
  }
}


## Contact
For any queries or issues, please contact [shaktigautam007@gmail.com].

