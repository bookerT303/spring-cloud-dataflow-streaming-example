package io.pivotal.kafka.embeddedkafkabroker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"schemaRegistryPort=9094"})
class EmbeddedKafkaServerTests {

    @Test
    void contextLoads() {
    }

}
