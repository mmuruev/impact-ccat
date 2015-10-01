package impact.cat;

import impact.cat.rabbit.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

//	final static String queueName = "spring-boot";
//
	@Autowired
	AnnotationConfigApplicationContext context;
//
//	@Autowired
//	RabbitTemplate rabbitTemplate;
//
//	@Bean
//	Queue queue() {
//		return new Queue(queueName, false);
//	}
//
//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange("spring-boot-exchange");
//	}
//
//	@Bean
//	Binding binding(Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(queueName);
//	}
//
//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames(queueName);
//		container.setMessageListener(listenerAdapter);
//		return container;
//	}
//
//    @Bean
//    Receiver receiver() {
//        return new Receiver();
//    }
//
//	@Bean
//	MessageListenerAdapter listenerAdapter(Receiver receiver) {
//		return new MessageListenerAdapter(receiver, "receiveMessage");
//	}

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
/*        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(queueName, "Hello from RabbitMQ!");
        receiver().getLatch().await(10000, TimeUnit.MILLISECONDS);
        context.close();*/

        System.out.println("Waiting five seconds...");
        Thread.sleep(5000);
        System.out.println("Press any key to exit");
        Scanner scanner = new Scanner(System.in);
        while(!scanner.hasNext()){
            Thread.sleep(1000);
        }
        //RabbitTemplate rabbitTemplate = (RabbitTemplate) context.getBean("rabbitTemplate");

       // Object response = rabbitTemplate.receiveAndConvert(RabbitConfig.BROADCAST_INTEREST_QUEUE);
       // String response = (String)rabbitTemplate.receiveAndConvert(RabbitConfig.BROADCAST_SOLVED_QUEUE);
       // System.out.print(response);

        context.close();
    }
}