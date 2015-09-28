package impact.cat.rabbit;

import impact.cat.dao.Loan;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 20.09.15
 * Time: 12:56
 */
public class WebAppListener {
    public void receiveMessage(String message) {
         System.out.println("WebAppListener: Message Received <" + message + ">");
     }

    public void receiveMessage(Object message) {
         System.out.println("WebAppListener: Message Received <" + message.toString() + ">");
     }

    public void receiveMessage(Loan message) {
         System.out.println("WebAppListener: Message Received <Days:" + message.getDays() + " Sum:"+ message.getSum()+">");
     }

}