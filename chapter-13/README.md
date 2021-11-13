# Chapter 13 - Observing Reactive and Event-Driven Architectures

## Pre-requisites

- [Helm](https://helm.sh/) installed

## Kubernetes cluster

Have a Kubernetes cluster running, or start Minikube:

```shell
minikube start --memory=4096
```

## Install Kafka

### Install Strimzi

```shell
kubectl create ns strimzi
kubectl create ns kafka

helm repo add strimzi https://strimzi.io/charts
helm install strimzi strimzi/strimzi-kafka-operator -n strimzi --set watchNamespaces={kafka} --wait --timeout 300s
```

When Strimzi is finished installing, we will see the operator in a running state:

```shell
kubectl get pods -n strimzi
NAME                                        READY   STATUS    RESTARTS   AGE
strimzi-cluster-operator-58fcdbfc8f-mjdxg   1/1     Running   0          46s
```

### Create Kafka cluster

With the operator running, we can now create a Kafka cluster!

```shell
kubectl apply -f deploy/kafka/kafka-cluster.yaml -n kafka
```

We can verify successful installation of the cluster with:

```shell
kubectl get pods -n kafka
NAME                                          READY   STATUS    RESTARTS   AGE
my-cluster-entity-operator-765f64f4fd-2t8mk   3/3     Running   0          90s
my-cluster-kafka-0                            1/1     Running   0          113s
my-cluster-zookeeper-0                        1/1     Running   0          2m12s
```

### Create Topics

```shell
kubectl apply -f deploy/kafka/ticks.yaml
kubectl apply -f deploy/kafka/processed.yaml
```

## Install Jaeger - Only for Distributed Tracing

### Install Jaeger All In One

```shell
kubectl create ns jaeger

kubectl apply -f deploy/jaeger/jaeger-simplest.yaml -n jaeger
```

This is not the recommended way to install Jaeger for production usage,
but it's sufficient for our needs.

We can verify successful installation with:

```shell
kubectl get pods -n jaeger
NAME                      READY   STATUS    RESTARTS   AGE
jaeger-64ffd7f658-vbwwg   1/1     Running   0          5s
```

### Update application

Ensure each of the services has the following dependency present in `pom.xml`:

```xml
<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-opentelemetry-exporter-jaeger</artifactId>
</dependency>
```

Verify the configuration for the jaeger endpoint is uncommented in `application.properties`:

```properties
quarkus.opentelemetry.tracer.exporter.jaeger.endpoint=http://simplest-collector.jaeger:14250
```

## Application deployment

If using Minikube, run:

```shell
eval $(minikube -p minikube docker-env)
```

Build and deploy the services:

```shell
cd viewer
mvn verify -Dquarkus.kubernetes.deploy=true
cd ..
# To deploy the processor with nacking for health check verification
cd processor-health
# To deploy the processor without nacking
cd processor
mvn verify -Dquarkus.kubernetes.deploy=true
cd ..
cd ticker
mvn verify -Dquarkus.kubernetes.deploy=true
cd ..
```

Verify they're all working with `kubectl get pods`.

### Redeploy updated code

```shell
mvn verify -Dquarkus.container-image.build=true
```

## Using the application

With the application started,
open a browser window to the SSE endpoint to see the results:

```shell
export VIEWER_URL=`minikube service --url observability-viewer`
echo "Open url: ${VIEWER_URL}"
```
