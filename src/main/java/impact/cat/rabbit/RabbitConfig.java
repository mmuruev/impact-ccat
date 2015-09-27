package impact.cat.rabbit;

import impact.cat.dao.Loan;
import impact.cat.queue.MyJarQueue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 20.09.15
 * Time: 12:52
 */
@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String BROADCAST_INTEREST_QUEUE = "interest-queue";
    public static final String BROADCAST_SOLVED_QUEUE = "solved-interest-queue";

    public static final String RABBIT_HOST = "impact.ccat.eu";
    public static final String RABBIT_LOGIN= "myjar";
    public static final String RABBIT_PASSWORD = "myjar";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(RABBIT_HOST);
        //connectionFactory.setHost(RABBIT_HOST);
        connectionFactory.setUsername(RABBIT_LOGIN);
        connectionFactory.setPassword(RABBIT_PASSWORD);
        return connectionFactory;
    }


    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public Queue interestQueue() {
        return new MyJarQueue(BROADCAST_INTEREST_QUEUE);
    }

/*
    @Bean
    public Queue solvedQueue() {
        return new MyJarQueue(BROADCAST_SOLVED_QUEUE);
    }
*/

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange interestExchange() {
        DirectExchange exchange = new DirectExchange("");
        return exchange;
    }

    @Bean
    public Binding interestBinding() {
        return BindingBuilder.bind(interestQueue()).to(interestExchange()).with(BROADCAST_INTEREST_QUEUE);
    }

/*
    @Bean
    public Binding solvedBinding() {
        return BindingBuilder.bind(solvedQueue()).to(interestExchange()).with(BROADCAST_SOLVED_QUEUE);
    }
*/

    @Bean
    SimpleMessageListenerContainer persistenceListenerContainer(ConnectionFactory connectionFactory,
                                                                @Qualifier("persistenceListenerAdapter") MessageListenerAdapter listenerAdapter,
                                                                MessageConverter messageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(interestQueue()/*, solvedQueue()*/);
        listenerAdapter.setMessageConverter(messageConverter);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter persistenceListenerAdapter(PersistenceListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer webAppListenerContainer(ConnectionFactory connectionFactory,
                                                           @Qualifier("webAppListenerAdapter") MessageListenerAdapter listenerAdapter,
                                                           MessageConverter messageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(interestQueue()/*, solvedQueue()*/);
        listenerAdapter.setMessageConverter( new Jackson2JsonMessageConverter());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter webAppListenerAdapter(WebAppListener webAppListener) {
        return new MessageListenerAdapter(webAppListener, "receiveMessage");
    }

    @Bean
    PersistenceListener persistenceListener() {
        return new PersistenceListener();
    }

    @Bean
    WebAppListener webAppListener() {
        return new WebAppListener();
    }


/*    @Bean
    public DefaultClassMapper typeMapper() {
        DefaultClassMapper typeMapper = new DefaultClassMapper();
        Map<String, Class<Loan>> idClassMapping = new HashMap<String, Class<Loan>>();
        //idClassMapping.put("range", Loan.class);
      //  typeMapper.setIdClassMapping(idClassMapping);
        //typeMapper.setDefaultType(Loan.class);
        return typeMapper;
    }*/

/*    @Bean
    	public MessageConverter messageConverter(DefaultClassMapper defaultClassMapper){
    		JsonMessageConverter jsonMessageConverter = new JsonMessageConverter();
    		jsonMessageConverter.setClassMapper(defaultClassMapper);
    		return jsonMessageConverter;
    	}*/

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
