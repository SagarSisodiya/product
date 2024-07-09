#Product

This application is a microservice and offers apis for all product related operations. It will refuse to launch if it cannot connect to **config-server** (microservice for providing configurations at runtime)

## Security

Apis in this service require a valid JWT token passed as header with attribute name **Authorization**.

Post successful login JWT token can be found in response header of below endpoint of **auth-handler** (microservice for handling authentication and authorization) with attribute name **Authorization**.

	/auth/login
	
## Setup

Property value for **spring:config:import:** in **application-dev.yml** file should match below value

	configserver:<config-server-host-url>
	
**config-server** will fetch configuration from **service-configuration-dev** git repo.

This service uses MongoDB as datasource. Details of MongoDB database server should be updated in **product.yml** file in **service-configuration-dev** git repo.
These details will be fetched by **config-server** at launch.

Below are properties to be updated.

	spring:
	  data:
    	mongodb:
	      uri: <database-uri-with-credentials>
    	  database: <database-name>
    	  
## Build

To build run below command :

	mvn clean install
	
## Development

To start the application in dev profile run :

	-Dspring.profiles.active=dev
	
## Testing

To launch the application tests, run :

	mvn test
	
## Note

For running this application in prod profile, replace **dev**  with **prod**.
