# Streaming example with Spring Cloud Data Flow

Example for the blog post: http://zoltanaltfatter.com/2017/08/24/streaming-with-spring-cloud-data-flow

This is a mvn project without any mvnw so use Intellij Maven Projects to execute the mvn commands
for clean and package

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
