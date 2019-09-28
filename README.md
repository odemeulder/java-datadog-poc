# java-datadog-poc
Datadog Custom Metrics POC

## prerequisites 
Datadog agent needs to run locally

## run
```
./gradlew bootrun
```

## Notable 
Emitting custom metrics using the `StatsDClient`. See `Metrics.java` as the wrapper for custom metrics implementation.
