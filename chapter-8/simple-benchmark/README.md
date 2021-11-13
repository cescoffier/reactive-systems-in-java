# RESTEasy vs. RESTEasy Reactive Benchmark

This directory contains three variants of the same _hello_ application:

* [classic (RESTEasy classic)](./classic)
* [reactive (RESTEasy reactive)](./reactive)
* [reactive with blocking (RESTEasy reactive using a blocking signature)](./reactive-blocking)

They all exposed the `/hello` endpoint.

These applications are Quarkus applications.
So you can run them in dev mode using `mvn quarkus:dev`, or package them using `mvn package`.
To run the package, you need to package them, and then run them using: `java -jar target/quarkus-app/quarkus-run.jar`

The benchmark use `wrk` (https://github.com/wg/wrk):

```shell
> wrk -t 10 -c50 -d40s http://localhost:8080/hello
```
