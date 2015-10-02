package impact.cat.rabbit;

import impact.cat.dao.Loan;
import impact.cat.dao.LoanCalculations;
import impact.cat.queue.MyJarInterestQueue;
import impact.cat.queue.MyJarSolvedInterestQueue;
import impact.cat.rabbit.header.MyJarMessagePropertiesConverter;
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
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new MyJarInterestQueue(BROADCAST_INTEREST_QUEUE);
    }

    @Bean
    public Queue solvedQueue() {
        return new MyJarSolvedInterestQueue(BROADCAST_SOLVED_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMessagePropertiesConverter(new MyJarMessagePropertiesConverter());
        return rabbitTemplate ;
    }

    @Bean
    public DirectExchange interestExchange() {
        return new DirectExchange("");
    }

    @Bean
    public Binding interestBinding() {
        return BindingBuilder.bind(interestQueue()).to(interestExchange()).with(BROADCAST_INTEREST_QUEUE);
    }

    @Bean
    public Binding solvedBinding() {
        return BindingBuilder.bind(solvedQueue()).to(interestExchange()).with(BROADCAST_SOLVED_QUEUE);
    }

    @Bean
    SimpleMessageListenerContainer calculationsListenerContainer(ConnectionFactory connectionFactory,
                                                                 @Qualifier("calculationsListenerAdapter") MessageListenerAdapter listenerAdapter,
                                                                 MessageConverter messageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(solvedQueue());

        DefaultClassMapper typeMapper = new DefaultClassMapper();
        typeMapper.setDefaultType(LoanCalculations.class);

        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(typeMapper);

        listenerAdapter.setMessageConverter(jsonMessageConverter);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter calculationsListenerAdapter(CalculationsListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer interestListenerContainer(ConnectionFactory connectionFactory,
                                                             @Qualifier("interestListenerAdapter") MessageListenerAdapter listenerAdapter, MessageConverter messageConverter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(interestQueue());
        listenerAdapter.setMessageConverter(messageConverter);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter interestListenerAdapter(InterestListener interestListener) {
        return new MessageListenerAdapter(interestListener, "receiveMessage");
    }

    @Bean
    CalculationsListener calculationsListener() {
        return new CalculationsListener();
    }

    @Bean
    InterestListener interestListener() {
        return new InterestListener();
    }


    @Bean
    public DefaultClassMapper typeMapper() {
        DefaultClassMapper typeMapper = new DefaultClassMapper();
        typeMapper.setDefaultType(Loan.class);
        return typeMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(DefaultClassMapper defaultClassMapper) {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(defaultClassMapper);
        return jsonMessageConverter;
    }

}
