# Hello Reactive Messaging

This module is a simple reactive application using Quarkus and Reactive Messaging.

## Dev

You can run this application in dev mode using:

```shell
> mvn quarkus:dev
```

The application prints the transiting messages.

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
