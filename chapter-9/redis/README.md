# REdis Example

This module is a simple reactive application using Quarkus and Redis.

## Dev

You can run this application in dev mode using:

```shell
> mvn quarkus:dev
```

The application is exposed on `http://localhost:8080/customer`.

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

In production mode, you need to start a Redis instance in Docker.
You can create one using:

```shell
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name redis_quarkus_test -p 6379:6379 redis:5.0.6
```
