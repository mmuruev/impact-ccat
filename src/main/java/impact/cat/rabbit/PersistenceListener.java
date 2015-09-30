package impact.cat.rabbit;

import impact.cat.dao.Loan;
import impact.cat.dao.LoanCalculations;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 20.09.15
 * Time: 12:56
 */
public class PersistenceListener {

    public void receiveMessage(LoanCalculations message) {
         System.out.println("Persistence: Message Received <Days:" + message.getDays() + " Sum:"+ message.getSum()+">");
     }

}
