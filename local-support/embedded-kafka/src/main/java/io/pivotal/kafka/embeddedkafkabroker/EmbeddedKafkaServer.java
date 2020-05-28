package io.pivotal.kafka.embeddedkafkabroker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.*;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class EmbeddedKafkaServer {

    private static Logger log = LoggerFactory.getLogger(EmbeddedKafkaServer.class);

    private static List<String> topics = Collections.emptyList();

    public static void main(String[] args) {
        topics = Arrays.stream(args).filter(arg -> false == arg.startsWith("--")).collect(toList());
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
        if (topics.size() > 0) {
            log.info("broker starting on port {} with topics {}", port, topics);
            embeddedKafkaBroker = new EmbeddedKafkaBroker(1, false,
                    topics.toArray(new String[0]));
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
