# SmallRye Mutiny Examples

This module contains various examples of code using SmallRye Mutiny.

## Build

Build it the application package using:

```shell
> mvn clean package
```

## Running the examples

Run the example using:

```shell
> mvn exec:java -Dexec.mainClass=org.acme.MultiApi"
```

Replace `org.acme.MultiApi` with the example you want to run:

* `org.acme.CreationWithSubscription`
* `org.acme.UniApi`
* `org.acme.MultiApi` 
* `org.acme.UniCombine`
* `org.acme.UniFailure`
* `org.acme.UniTransformAsync`
* `org.acme.MultiObserve`
* `org.acme.MultiTransform`
* `org.acme.MultiTransformAsync`
* `org.acme.MultiCollect`
* `org.acme.MultiCombine`
* `org.acme.MultiMergeAndConcat`

