# Blocking and Non-Blocking I/O examples

This module contains various examples of blocking and non-blocking servers.

## Build

Build it the application package using:

```shell
> mvn clean package
```

## Blocking Echo Server

Run this example using:

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.blocking.BlockingEchoServer"
```

Test it using, in another terminal (also in the module directory):

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.client.EchoClient"
```

Shutdown the server using `CTRL+C`.

## Blocking Echo Server with Workers

Run this example using:

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.blocking.BlockingWithWorkerEchoServer"
```

Test it using, in another terminal (also in the module directory):

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.client.EchoClient"
```

Shutdown the server using `CTRL+C`.

## Java Non-Blocking I/O

Run this example using:

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.nio.NonBlockingServer"
```

Test it using, in another terminal (also in the module directory):

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.client.EchoClient"
```

Shutdown the server using `CTRL+C`.

## Java Non-Blocking I/O (Async)

Run this example using:

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.nio.NonBlockingServerWithAsync"
```

Test it using, in another terminal (also in the module directory):

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.client.EchoClient"
```

Shutdown the server using `CTRL+C`.

## Netty

Run this example using:

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.netty.NettyEchoServer"
```

Test it using, in another terminal (also in the module directory):

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.client.EchoClient"
```

Shutdown the server using `CTRL+C`.

## Eclipse Vert.x

Run this example using:

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.vertx.VertxEchoServer"
```

Test it using, in another terminal (also in the module directory):

```shell
> mvn exec:java -D"exec.mainClass"="org.acme.client.EchoClient"
```

Shutdown the server using `CTRL+C`.
