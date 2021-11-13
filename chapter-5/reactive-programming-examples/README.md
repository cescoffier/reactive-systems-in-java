# Reactive Programming Examples

This module contains various examples of code using reactive programming constructs.

## Build

Build it the application package using:

```shell
> mvn clean package
```

## Running the examples

Run the example using:

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.future.Futures"
```

Replace `org.acme.future.Futures` with the example you want to run:

* `org.acme.future.Futures` - Example using completable futures and completion stages
* `org.acme.reactive.GreetingExample` - Example demonstrating events
* `org.acme.reactive.StreamsExample` - Example demonstrating streams
* `org.acme.streams.BufferingExample` - Example demonstrating buffer back-pressure strategy (use `CTRL+C` to terminate the application after the failure)
* `org.acme.streams.DropExample` - Example demonstrating the drop back-pressure strategy (use `CTRL+C` to terminate the application)
* `org.acme.streams.BackPressureExample` - Example demonstrating the Reactive Streams back-pressure (use `CTRL+C` to terminate the application after the failure)

