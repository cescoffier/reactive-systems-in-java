# RESTEasy Reactive Example

This module is a simple reactive application using Quarkus and RESTEasy Reactive.

## Dev

You can run this application in dev mode using:

```shell
> mvn quarkus:dev
```

The application is exposed on `http://localhost:8080/hello-resteasy-reactive`. 
For example:

```shell
> curl http://localhost:8080/hello-resteasy-reactive
> curl http://localhost:8080/hello-resteasy-reactive/blocking
```

## Build

Build it the application package using:

```shell
> mvn clean package
```

The output is located in `target/quarkus-app`

## Run

Once packaged, you can run the application with:

```shell
> java -jar target/quarkus-app/quarkus-run.jar
```