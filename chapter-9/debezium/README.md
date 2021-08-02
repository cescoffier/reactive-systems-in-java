# CDC with Debezium example

Start Zookeeper, Kafka, PostgreSQL, and Kafka Connect:

```shell
docker compose up
```

In another terminal, run the Quarkus application in _/chapter-9/debezium_:

```shell
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

## Create Postgres connector

```shell
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/ -d @register.json
```

### Verify Postgres connector was created

```shell
curl -H "Accept:application/json" localhost:8083/connectors/
```

### Verify configuration of the Postgres connector

```shell
curl -X GET -H "Accept:application/json" localhost:8083/connectors/customer-connector
```

## Verify topics created for tables

```shell
docker exec -ti kafka bin/kafka-topics.sh --list --zookeeper zookeeper:2181
```

# Consume messages from the Customer table topic

```shell
docker-compose exec kafka /kafka/bin/kafka-console-consumer.sh \
--bootstrap-server kafka:9092 \
--from-beginning \
--property print.key=true \
--topic quarkus-db-server.public.customer
```

Upon starting, four messages will be processed representing the four customers that were loaded
by the Quarkus application upon starting.
If you don't want to see those messages,
remove `--from-beginning` in the above command.

# Trigger customer updates

In another terminal, create or update customer data to see the messages appear.

## Update a customer name

```shell
curl -X PUT -H "Content-Type:application/json" http://localhost:8080/customer/2 -d '{"id" : 2, "name" : "Marsha Willis"}'
```

## Create a new customer

```shell
curl -X POST -H "Content-Type:application/json" http://localhost:8080/customer -d '{"name" : "Harry Houdini"}'
```

# Shut down

When finished, run the below to stop and remove the services:

```shell
docker compose stop
docker compose rm
```
