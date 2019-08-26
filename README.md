# Radix signup and login flow

This is a full stack web application written in Java v1.8 and React v16.9, implementing a basic user signup and login flow. 
User details are persisted to a local MySQL database.


#### Frameworks and libraries used

- React
- Bootstrap
- Spring Boot
- Spring Security
- Spring Data JPA


## Setting up and running the application locally

1. Make sure you have **JDK** v1.8+  and **NPM** v6.0+ installed in your environment.
2. Make sure you have **MySQL Server** v5.7+ installed and running.
3. Open the `radix-auth-backend` and `radix-auth-frontend` projects in your IDE.
4. Open `radix-auth-backend/src/main/resources/application.properties` and adjust your database connection settings as necessary.
5. Compile and run the projects in your IDE.


### Notes

* The database is created automatically at first run, so no DDL needs to be executed manually.
* The backend server is configured to run on port 8443 by default, with SSL enabled. A PKCS12 keystore with a self-signed certificate is provided.
* The frontend server is configured to run on port 8084 by default. This can be changed in `radix-auth-frontend/.env`.
