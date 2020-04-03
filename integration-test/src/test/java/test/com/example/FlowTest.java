package test.com.example;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import test.com.example.support.ApplicationServer;
import test.com.example.support.MapBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static test.com.example.support.MapBuilder.envMapBuilder;

public class FlowTest {

    private static final String SOURCE_TOPIC = "kafka-demo";
    private static final String SOURCE_DLQ_TOPIC = "kafka-demo-dlq";
    private static final String SINK_TOPIC = "kafka-demo-processed";

    private final String workingDir = System.getProperty("user.dir");

    private ApplicationServer embeddedKafka = new ApplicationServer(workingDir + "/../local-support/embedded-kafka/build/libs/embedded-kafka.jar", "8901");
    private ApplicationServer sinkApp = new ApplicationServer(workingDir + "/../applications/sink-app/build/libs/sink-app.jar", "8883", "/actuator/health");
    private ApplicationServer processorApp = new ApplicationServer(workingDir + "/../applications/processor-app/build/libs/processor-app.jar", "8882", "/actuator/health");
    private ApplicationServer sourceApp = new ApplicationServer(workingDir + "/../applications/source-app/build/libs/source-app.jar", "8881", "/actuator/health");

    @Before
    public void setUp() throws Exception {

        embeddedKafka.start();

        //Kafka needs time to start before apps can attach.
        Thread.sleep(3000);

        sinkApp.start();
        processorApp.start(envMapBuilder().put("com.example.processor.errorEnabled", "false").build());
        sourceApp.start(envMapBuilder().put("com.example.source.schedulerEnabled", "false").build());

        ApplicationServer.waitOnPorts(100, sinkApp, processorApp, sourceApp);
    }

    @After
    public void tearDown() {
        sourceApp.stop();
        processorApp.stop();
        sinkApp.stop();
        embeddedKafka.stop();
    }

    @Test
    public void testBasicFlow() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>("This is a flow test message");
        for (int warmingUp=0; warmingUp < 10; warmingUp++) {

            restTemplate.postForEntity(sourceApp.url("/publish"), request, Void.class);
            String count = restTemplate.getForObject(sinkApp.url("/counter"), String.class);
            if ("0".equals(count)) {
                Thread.sleep(200);
            } else {
                break;
            }
        }
        restTemplate.delete(sinkApp.url("/counter"));

        ResponseEntity<Void> response = restTemplate.postForEntity(sourceApp.url("/publish"), request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String count = "0";
        for (int attempts = 0; attempts < 10 && count.equals("0"); attempts++) {
            // not immediate
            count = restTemplate.getForObject(sinkApp.url("/counter"), String.class);
            if ("0".equals(count)) {
                Thread.sleep(200);
            }
        }
        assertThat(count).isEqualTo("1");
    }
}
