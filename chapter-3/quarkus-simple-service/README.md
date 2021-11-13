# Quarkus Simple Service

This application illustrates the various type of failure that can happen in a distributed system

## Dev

You can run this application in dev mode using:

```shell
> mvn quarkus:dev
```

The application is exposed on `http://localhost:8080/`

## Fault Injection

Fault are injected using:

```shell
> curl "localhost:8080/fault?mode=$MODE&ratio=$RATIO"
```

`$MODE` can be:

* `NONE`
* `INBOUND_REQUEST_LOSS`
* `SERVICE_FAILURE`
* `OUTBOUND_RESPONSE_LOSS`

`$RATIO` is a float in [0, 1). The default ratio is 0.5.

If neither the mode and the ratio are set, it resets it to `NONE` / `0.5`

Here are a few examples:

```shell
> curl "localhost:8080/fault?mode=INBOUND_REQUEST_LOSS"
> curl "localhost:8080/fault?mode=SERVICE_FAILURE&ratio=0.7"
> curl "localhost:8080/fault?mode=OUTBOUND_RESPONSE_LOSS&ratio=0.1"
> curl "localhost:8080/fault" # Reset
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