# Hello Thorntail

This module contains the code of the Thorntail application used to compare startup time and RSS usage with Quarkus.
It's a simple _hello_ application

The application is exposed on `http://localhost:8080/hello`

**IMPORTANT**: The module does not compile with Java 17.

## Build

Build it the application package using:

```shell
> mvn clean package
```

The output is located in the `target` directory.

## Run

Once packaged, you can run the application with:

```shell
> java -jar target/thorntail-hello-world-thorntail.jar
```