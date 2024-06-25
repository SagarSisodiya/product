#Product

This application is a microservice and offers apis for all product related operations.

## Build

To build run below command :

	mvn clean install
	
## Development

To start the application in dev profile run :

	-Dspring.profiles.active=dev
	
## Security

All apis require jwt token for authentication and authorization.