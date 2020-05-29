package test.com.example.support;

import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.assertj.core.api.Assertions.fail;
import static test.com.example.support.MapBuilder.envMapBuilder;

public class ApplicationServer {

    private final String jarPath;
    private final String port;
    private String testEndpoint;

    private Process serverProcess;

    public ApplicationServer(String jarPath, String port) {
        this(jarPath, port, "");
    }

    public ApplicationServer(String jarPath, String port, String testEndpoint) {
        this.jarPath = jarPath;
        this.port = port;
        this.testEndpoint = testEndpoint;
    }

    public void start() throws IOException, InterruptedException {
        start(envMapBuilder().build());
    }

    public void start(Map<String, String> env) throws IOException, InterruptedException {
        start("", env);
    }

    public void start(String jvmParameters, Map<String, String> env) throws IOException, InterruptedException {
        ProcessBuilder processBuilder;
        if (StringUtils.isEmpty(jvmParameters)) {
            processBuilder = new ProcessBuilder()
                    .command("java", "-jar", jarPath, "--server.port=" + port)
                    .inheritIO();
        } else {
            processBuilder = new ProcessBuilder()
                    .command("java", jvmParameters, "-jar", jarPath, "--server.port=" + port)
                    .inheritIO();
        }

        env.forEach((key, value) -> processBuilder.environment().put(key, value));

        serverProcess = processBuilder.start();
    }

    public void stop() {
        serverProcess.destroyForcibly();
    }

    public static void waitOnPorts(ApplicationServer... servers) throws InterruptedException {
        waitOnPorts(120, servers);
    }

    public static void waitOnPorts(long timeLimitSeconds, ApplicationServer... servers) throws InterruptedException {
        for (ApplicationServer server : servers) waitUntilServerIsUp(timeLimitSeconds, server);
    }

    private static void waitUntilServerIsUp(long timeoutSeconds, ApplicationServer server) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        Instant start = Instant.now();
        boolean isUp = false;

        System.out.print("Waiting on port " + server.port + "...");

        while (!isUp) {
            try {
                restTemplate.getForEntity("http://localhost:" + server.port + server.testEndpoint, String.class);
                isUp = true;
                System.out.println(" server is up.");
            } catch (Throwable e) {
                if (e instanceof HttpClientErrorException) {
                    HttpClientErrorException httpException = (HttpClientErrorException) e;
                    int statusCode = httpException.getRawStatusCode();
                    if (statusCode != 404) {
                        isUp = true;
                        System.out.printf("Server returned %d, assuming server is up.", statusCode);
                        continue;
                    }
                } else if (e instanceof HttpServerErrorException) {
                    int statusCode = ((HttpServerErrorException) e).getRawStatusCode();
                    isUp = true;
                    System.out.printf("Server returned %d, assuming server is up.", statusCode);
                    continue;
                }
                long timeSpent = ChronoUnit.SECONDS.between(start, Instant.now());
                if (timeSpent > timeoutSeconds) {
                    fail("Timed out waiting for server on port " + server.port);
                }

                System.out.print(".");
                Thread.sleep(200);
            }
        }
    }

    public String url(String path) {
        return "http://localhost:" + port + path;
    }
}

