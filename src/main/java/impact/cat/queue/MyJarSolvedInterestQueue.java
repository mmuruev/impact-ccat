package impact.cat.queue;

import org.springframework.amqp.core.Queue;

/**
 * Created by maksim on 30/09/15.
 */
public class MyJarSolvedInterestQueue extends Queue {

    /**
     * The queue is durable, non-exclusive and non auto-delete.
     *
     * @param name the name of the queue.
     */
    public MyJarSolvedInterestQueue(String name) {
        super(name,true,false,false);
    }

}