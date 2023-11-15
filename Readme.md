# Streaming example with Spring Cloud Data Flow

Example for the blog post: http://zoltanaltfatter.com/2017/08/24/streaming-with-spring-cloud-data-flow

This is a mvn project has been converted to gradle and using appcontinuum structure

# Install and setup
```
brew -v update && brew install rabbitmq
brew services start rabbitmq
```

Create env.properties
```
# Properties that should be provided by the services and cloud foundry environment
SPRING_PROFILES_ACTIVE=development
SECURITY_BASIC_ENABLED=false
MANAGEMENT_SECURITY_ENABLED=false
logging.level.org.springframework.integration.support.MessageBuilder=WARN

SECURITY_USER_NAME=admin
SECURITY_USER_PASSWORD=secret
MANAGEMENT_SECURITY_ROLES=SUPERUSER
```

Create env_test.properties
```
# Properties that should be provided by the services and cloud foundry environment
SPRING_PROFILES_ACTIVE=development
SECURITY_BASIC_ENABLED=false
MANAGEMENT_SECURITY_ENABLED=false
logging.level.org.springframework.integration.support.MessageBuilder=WARN
```
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