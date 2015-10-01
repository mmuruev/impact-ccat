package impact.cat.rabbit.header;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;

import java.util.Map;

/**
 * Created by maksim on 01/10/15.
 */
public class MyJarMessagePropertiesConverter extends DefaultMessagePropertiesConverter
{

    /**
     *  Header customization here
     * @param source
     * @param envelope
     * @param charset
     * @return
     */
    public MessageProperties toMessageProperties(final AMQP.BasicProperties source, final Envelope envelope,
                                                 final String charset) {
        Map<String, Object> headers = source.getHeaders();
        headers.remove("__TypeID__");  // get rid off from Spring type injection
        return super.toMessageProperties(source,envelope,charset);
    }
}
