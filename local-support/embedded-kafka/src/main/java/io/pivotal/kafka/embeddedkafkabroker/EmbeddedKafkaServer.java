package io.pivotal.kafka.embeddedkafkabroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class EmbeddedKafkaServer {

    private static Logger log = LoggerFactory.getLogger(EmbeddedKafkaServer.class);

    private static String[] topics = {};

    public static void main(String[] args) {
        topics = args;
        SpringApplication.run(EmbeddedKafkaServer.class, args);
    }

    private EmbeddedKafkaBroker broker;


    public EmbeddedKafkaServer(@Qualifier("EmbeddedKafkaBroker") EmbeddedKafkaBroker broker) {
        this.broker = broker;
    }

    @Bean("EmbeddedKafkaBroker")
    public static EmbeddedKafkaBroker broker() throws Exception {
        EmbeddedKafkaBroker embeddedKafkaBroker;
        int port = 9092;
        if (topics.length > 0) {
            log.info("broker starting on port {} with topics {}", port, Arrays.stream(topics).collect(toList()));
            embeddedKafkaBroker = new EmbeddedKafkaBroker(1, false,
                    topics);
        } else {
            embeddedKafkaBroker = new EmbeddedKafkaBroker(1, false);
        }
        embeddedKafkaBroker.brokerProperties(convert(System.getProperties()));
        embeddedKafkaBroker.kafkaPorts(port);
        return embeddedKafkaBroker;
    }

    private static Map<String, String> convert(Properties properties) throws Exception {
        Map<String, String> map = new HashMap<>();
        for (Object key : properties.keySet()) {
            String skey = key.toString();
            map.put(skey, properties.getProperty(skey));
        }
        return map;
    }
}
