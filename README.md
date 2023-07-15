## Testing Device Booking System

### Key Concepts:
The mobile software testing team has 10 mobile phones that it needs to share for testing purposes.
- Samsung Galaxy S9
- 2x Samsung Galaxy S8
- Motorola Nexus 6
- Oneplus 9
- Apple iPhone 13
- Apple iPhone 12
- Apple iPhone 11
- iPhone X
- Nokia 3310

**This API allows for booking/returning these testing devices by a end user**

### Assumptions
The system take into consideration following assumptions -
- Considering above devices are available these are loaded into DB on startup, however this can be CRUD api that allows user to register new device. Currently, it only supports get all devices and device by id.
- Booking are based on days and not hours so if the startDate is `2023-07-15` and endDate is `2023-07-15` then the device is booked for complete day and can not be booked for that day again.

### Configurations
The application uses `application.yaml` for its configurations.

### Build
This application use **gradle** as its build plugin, please refer to HELP.md for any addition gradle references.

### Running the application

The application can be run as follows -
> ./gradlew booRun

### Testing the application

This application test suite can be run using the gradle task as follows
> ./gradlew cleanTest test

### API Documentation
Open API documentation can be accessed after running the application
> ./gradlew booRun

Then navigate to http://localhost:8080/v3/api-docs

Swagger UI can be found at http://localhost:8080/swagger-ui/index.html