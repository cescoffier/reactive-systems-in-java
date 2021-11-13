# Chapter 11 - The Event Bus: The Backbone

## Pre-requisites

- Running Kubernetes environment and `kubectl` connected to it.
- [Helm](https://helm.sh/) installed

## Install Kafka cluster

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

## Run AMQP Broker

Start a container with AMQP Broker image:

```shell
kubectl run amqp --image=quay.io/artemiscloud/activemq-artemis-broker --port=5672  --env="AMQ_USER=admin" --env="AMQ_PASSWORD=admin" -n event-bus
``` 

As we've only started a Pod, we also need to expose it as a service:

```shell
kubectl expose pod amqp --port=5672 -n event-bus
```

## Application deployment

Now create a namespace for the project:

```shell
kubectl create ns event-bus
```

### Build and push container images

Run `eval $(minikube -p minikube docker-env)` in each terminal window to ensure we use the Docker daemon inside Minikube.

```shell
cd ticker
docker build -f src/main/docker/Dockerfile.jvm -t event-bus/ticker .
cd ..

cd processor
docker build -f src/main/docker/Dockerfile.jvm -t event-bus/processor .
cd ..

cd viewer
docker build -f src/main/docker/Dockerfile.jvm -t event-bus/viewer .
cd ..
```

### Deploy with Helm

Adjust the image name for each service in `deploy/event-bus/values.yaml` if registry part needs modification.

Then run:

```shell
helm install event-bus-v1 deploy/event-bus -n event-bus --wait --timeout 300s
```

Verify the services are running with:

```shell
kubectl get pods -n event-bus
NAME                        READY   STATUS    RESTARTS   AGE
processor-d44564db5-48n97   1/1     Running   0          8s
ticker-694c8fff4f-2jwtf     1/1     Running   0          8s
viewer-f76cd668f-hsnvj      1/1     Running   0          8s
```

## Using the application

With the application started,
open a browser window to the SSE endpoint to see the results:

```shell
export VIEWER_URL=`minikube service --url viewer -n event-bus`
echo "Open url: ${VIEWER_URL}"
```
