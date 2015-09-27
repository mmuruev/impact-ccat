package impact.cat;


import java.util.concurrent.CountDownLatch;
/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 20.09.15
 * Time: 12:20
 */
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

   	public void receiveMessage(String message) {
   		System.out.println("Received <" + message + ">");
   		latch.countDown();
   	}

   	public CountDownLatch getLatch() {
   		return latch;
   	}
}
