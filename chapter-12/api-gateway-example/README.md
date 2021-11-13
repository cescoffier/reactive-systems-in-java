# API Gateway Example

This module illustrates the construction of API gateway using Quarkus, RESTEasy Reactive, the Reactive REST Client and Fault Tolerance.
The system is composed by:

* a quote service
* a greeting service
* the api gateway

### Running the Quote Service

In a terminal, navigate to the _chapter-12/api-gateway-example/quote-service_ directory and run:

```shell
> mvn package
> java -jar target/quarkus-app/quarkus-run.jar
```

The application is listening on the port 9020.

### Running the Greeting Service

In another terminal, navigate to the _chapter-12/api-gateway-example/greeting-service_ directory and run:

```shell
> mvn package
> java -jar target/quarkus-app/quarkus-run.jar
```

The application is listening on the port 9010.

### Running the API Gateway

In a third terminal, navigate to the _chapter-12/api-gateway-example/api-gateway_ directory and run:

```shell
> mvn quarkus:dev # Or package and run it.
```

Invoke the application as follows:

```shell
> curl http://localhost:8080 # Call both services 
> curl http://localhost:8080/quote # Just call the quote service
```
