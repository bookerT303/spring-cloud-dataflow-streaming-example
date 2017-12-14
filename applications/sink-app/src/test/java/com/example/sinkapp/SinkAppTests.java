package com.example.sinkapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SinkAppTests {

	@Test
	public void contextLoads() {
	}

}
/*
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FieldValueCounterSinkApplication.class)
@WebIntegrationTest({"server.port:-1", "name:FVCounter", "store:redis", "fieldName:test"})
@DirtiesContext
public class FieldValueCounterSinkTests {

	@Rule
	public RedisTestSupport redisTestSupport = new RedisTestSupport();

	private static final String FVC_NAME = "FVCounter";

	@Autowired
	@Bindings(FieldValueCounterSink.class)
	private Sink sink;

	@Autowired
	private FieldValueCounterRepository fieldValueCounterRepository;

	@Before
	@After
	public void clear() {
		fieldValueCounterRepository.reset(FVC_NAME);
	}

	@Test
	public void testFieldNameIncrement() {
		assertNotNull(this.sink.input());
		Message<String> message = MessageBuilder.withPayload("{\"test\": \"Hi\"}").build();
		sink.input().send(message);
		message = MessageBuilder.withPayload("{\"test\": \"Hello\"}").build();
		sink.input().send(message);
		message = MessageBuilder.withPayload("{\"test\": \"Hi\"}").build();
		sink.input().send(message);
		assertEquals(2, this.fieldValueCounterRepository.findOne(FVC_NAME).getFieldValueCounts().get("Hi").longValue());
		assertEquals(1, this.fieldValueCounterRepository.findOne(FVC_NAME).getFieldValueCounts().get("Hello").longValue());
	}


 */