package impact.cat.rabbit.header;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;

/**
 * Created by maksim on 01/10/15.
 */
public class MyJarMessagePostProcessor implements MessagePostProcessor {
    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        MessageProperties properties = message.getMessageProperties();
        properties.getHeaders().remove("__TypeId__");
        properties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        return new Message(message.getBody(), properties);
    }
}
