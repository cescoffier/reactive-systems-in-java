# REST Client + Messaging example

This module illustrates how to use the REST Client with Reactive Messaging.

## Dev

You can run this application in dev mode using:

```shell
> mvn quarkus:dev
```

The application is exposed on `http://localhost:8080/order`:

```shell
> http :8080/order name=pen quantity:=20
> http :8080/order
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