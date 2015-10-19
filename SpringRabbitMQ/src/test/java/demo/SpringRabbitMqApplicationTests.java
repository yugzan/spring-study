package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yugzan.amqp.SpringRabbitMqApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringRabbitMqApplication.class)
public class SpringRabbitMqApplicationTests {

	@Test
	public void contextLoads() {
	}

}
