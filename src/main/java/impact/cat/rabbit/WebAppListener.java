package impact.cat.rabbit;

import impact.cat.calculation.Interest;
import impact.cat.calculation.bag.Money;
import impact.cat.dao.Loan;
import impact.cat.dao.LoanCalculations;
import impact.cat.rabbit.header.MyJarMessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 20.09.15
 * Time: 12:56
 */
public class WebAppListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void receiveMessage(Loan message) {
        System.out.println("WebAppListener: Message Received <Days:" + message.getDays() + " Sum:" + message.getSum() + ">");

        Interest interest = new Interest(new Money(message.getSum()), message.getDays());

        LoanCalculations loanCalculations = interest.getLoanCalculations();
        rabbitTemplate.convertAndSend(loanCalculations, new MyJarMessagePostProcessor());
        System.out.println("WebAppListener: Message has been send " + loanCalculations.toString());

    }

}
