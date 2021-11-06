# location-simulator
Spring Boot REST project for finding Latitude and Longitudes at a constant interval distance (default 50m)

## Executing the Application (Locally)

```cmd
mvn clean install
```

```cmd
java -jar target/location-simulator-0.0.1-SNAPSHOT.jar
```

## API Documentation

After running the application successfully, use the below link to access the [API Documentation](http://localhost:8080/swagger-ui.html).

```
http://localhost:8080/swagger-ui.html
```
## Testing

API takes 3 inputs as Query Parameters.
1. origin (latitude and longitude separated by comma)
2. destination (latitude and longitude separated by comma)
3. interval (Optional) - Default value is 50

Endpoint
```
/api/location?origin=<ORIGIN>&destination=<DESTINATION>
```

Example:
Get all the cordinated between origin (12.93175,77.62872) & destination (12.92662,77.63696) placed 50 m apart
```
curl localhost:8080/api/location?origin=12.93175,77.62872&destination=12.92662,77.63696
```

Click on the button below to get the Postman Collection for Conversion Calculator.

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/5003574bbb74381c88b8)

## Health Check Probes for Kubernetes

[Liveness Check](http://localhost:8080/actuator/health/liveness): http://localhost:8080/actuator/health/liveness

[Readiness Check](http://localhost:8080/actuator/health/readiness): http://localhost:8080/actuator/health/readiness
