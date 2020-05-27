# Streaming example with Spring Cloud Data Flow

# Install and setup
```
brew -v update && brew install kafka
brew services start zookeeper
brew services start kafka
```

Create env.properties
```
# Properties that should be provided by the services and cloud foundry environment
SPRING_PROFILES_ACTIVE=development
#SECURITY_BASIC_ENABLED=true
#MANAGEMENT_SECURITY_ENABLED=true
logging.level.org.springframework.integration.support.MessageBuilder=WARN

#SECURITY_USER_NAME=admin
#SECURITY_USER_PASSWORD=secret
#MANAGEMENT_SECURITY_ROLES=SUPERUSER
```

Create env_test.properties
```
# Properties that should be provided by the services and cloud foundry environment
SPRING_PROFILES_ACTIVE=development
SECURITY_BASIC_ENABLED=false
MANAGEMENT_SECURITY_ENABLED=false
logging.level.org.springframework.integration.support.MessageBuilder=WARN
```

## Changes to application.yml
Replace configuration like:
```yaml
spring:
  cloud:
    stream:
      bindings:
        output:
          destination: demo-processed
        input:
          destination: demo
          group: processors
```
With Kafka configuration like:
```yaml
spring:
  cloud:
    stream:
      kafka:
        binder:
          autoAddPartitions: true
          minPartitionCount: 2
      bindings:
        output:
          destination: kafka-demo-processed
          content-type: application/json
        input:
          destination: kafka-demo
          content-type: application/json
          group: processors
```
Changed the topic names just for clarity that this was Kafka.

# Running the apps...

 ## Source... running only 1 instance

 ## processor... running two instances with consumer groups
 if we dont use consumer groups then every instance processes
 every message and we will get duplicates

 ### Run Configuration for the first processor
 ```
 --spring.cloud.stream.bindings.input.destination= demo
 --spring.cloud.stream.bindings.output.destination= demo-processed
 --spring.cloud.stream.bindings.input.group=processors
 ```

### Run Configuration for the second processor
```
--spring.cloud.stream.bindings.input.destination=demo
--spring.cloud.stream.bindings.output.destination=demo-processed
--server.port=8182
--spring.cloud.stream.bindings.input.group=processors
```

We need a different port for the second instance and I choose to add 100 to the port number
Group name is processors because eventually I am going to have
a sink on each topic

## Sink.... running two instances with consumer groups

### Run Configuration for the first sink
```
--spring.cloud.stream.bindings.input.destination=demo-processed
--spring.cloud.stream.bindings.input.group=sinks
```

### Run Configuration for the second sink
```
--spring.cloud.stream.bindings.input.destination=demo-processed
--spring.cloud.stream.bindings.input.group=sinks
--server.port=8183
```

## Sink for the demo topic
just to see it work I added an adiditonal sink on
the demo channel so that we could use it as a debug
view port into the topic

### Run Configuration for the view port sink
```
--spring.cloud.stream.bindings.input.destination=demo
--spring.cloud.stream.bindings.input.group=sinks
--server.port=8084
```

**This last example could be a simple `source` to `sink` without any
processor.**

### Run Configuration for a debug processor
```
--spring.cloud.stream.bindings.input.destination=demo-processed
--server.port=8089
--spring.cloud.stream.bindings.input.group=debug
```
Which is actually done with:
```
SPRING_APPLICATION_JSON='{"spring.cloud.stream.bindings.ion": "demo-processed", "server.port": 8089, "spring.cloud.stream.bindings.input.group": "debug" }'
```

**This allows seeing all the messages that the other
processors should have processed.**

## Sending messages

The source app can publish messages using:
```
curl -v --data "message data" -H "Content-Type: text/plain" http://localhost:8081/publish
```

# Deploying to TAS
If you deploy to TAS (Tanzu Application Service) using a manifest then
you will need to set the `health-check-type:` for the processor
since it is not a REST API.

```
health-check-type: process
```

[For more information on the manifest](https://docs.run.pivotal.io/devguide/deploy-apps/manifest-attributes.html)
