package impact.cat.queue;

import org.springframework.amqp.core.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 22.09.15
 * Time: 20:27
 */
public class MyJarInterestQueue extends Queue {

    /**
     * The queue is durable, non-exclusive and non auto-delete.
     *
     * @param name the name of the queue.
     */
    public MyJarInterestQueue(String name) {
        super(name,false,false,true);
    }

}
