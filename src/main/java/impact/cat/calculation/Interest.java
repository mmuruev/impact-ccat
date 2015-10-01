package impact.cat.calculation;

import impact.cat.calculation.bag.Money;
import impact.cat.dao.LoanCalculations;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 27.09.15
 * Time: 16:19
 *
 * Interest formula

     Interest is calculated based on sum and days fields
     Interest is calculated per day as a percentage from the original amount
     If day is...
         divisible by three, the interest is: 1%
         divisible by five, the interest is: 2%
         divisible by both three and five, the interest is: 3%
         not divisible by either three or five, interest is: 4%
     Each day interest amount is rounded to two digits
     Final interest is a sum of all days interests
     Total sum is the sum of original amount and total interest

 */
public class Interest {

    private Money sum;

    private BigDecimal days;

    static final Logger logger = Logger.getLogger(Interest.class);

    public static int FIRST_INTEREST_DAY = 1;

    public final String NEGATIVE_VALUE_ERROR_MESSAGE = "cannot be negative. Value: ";


    /**
     *  Formatted string for values
     *  1. value type
     *  2. value
     */
    public final String VALUE = "<%s>: %s has been given";

    /**
     * Formatted string for calculated values
     * 1. value type
     * 2. value
     */
    public final String CALCULATED_VALUE = "<%s>: %s has been calculated";

    public Interest(Money sum, BigDecimal days) {
        setSum(sum);
        setDays(days);

    }

    public Money getSum() {
        return sum;
    }

    public BigDecimal getDays() {
        return days;
    }

    public void setDays(BigDecimal days) {
        checkPositive(days, "days");
        this.days = days;
    }

    public void setSum(Money sum) {
        checkPositive(sum.getAmount(),"sum");
        this.sum = sum;
    }

    /**
     *
     * @return - all calculations for loan
     */
    public LoanCalculations getLoanCalculations(){
        return new LoanCalculations(
                getSum().getAmount(),
                getDays(),
                getFinalInterest().getAmount(),
                getTotalSum().getAmount()
        );
    }

    public Money getFinalInterest(){
        Money finalInterest = new Money();
        for(BigDecimal day = new BigDecimal(FIRST_INTEREST_DAY); day.compareTo(days) <= 0 ; day = day.add(BigDecimal.ONE)){
           finalInterest = finalInterest.add(getAmountForDay(day));
        }
        logger.debug(String.format(CALCULATED_VALUE,"final_interest",finalInterest.getAmount()));
        return finalInterest;
    }

    public Money getTotalSum(){
       Money finalInterest =  sum.add(getFinalInterest());
       logger.debug(String.format(CALCULATED_VALUE, "total_sum",finalInterest));
       return finalInterest;
    }

    private Money getAmountForDay(BigDecimal day){
        return sum.getPercentAmount(getPercentForDay(day));
    }
    /**
     *  Get percent for day according bussiness logic
     * @param day - current day
     * @return - percentage
     */
    private BigDecimal getPercentForDay(BigDecimal day){
        boolean byThree = isDividable(day, 3);
        boolean byFive = isDividable(day, 5);

        if(byThree && byFive){
            return new BigDecimal("3");
        }

        if(byThree){
            return BigDecimal.ONE;
        }

        if(byFive){
            return new BigDecimal("2");
        }

        return new BigDecimal("4");
    }

    /**
     *  Checking divisible by
     * @param value - actually value
     * @param divider - check against this
     * @return - checking result
     */
    private boolean isDividable(BigDecimal value, int divider){
        return value.remainder(new BigDecimal(divider)).compareTo(BigDecimal.ZERO) == 0;
    }

    private void checkPositive(BigDecimal value, String fieldName){
        if(value.compareTo(BigDecimal.ZERO) == -1 ){
            logger.error(NEGATIVE_VALUE_ERROR_MESSAGE + value);
            throw new IllegalArgumentException("<" + fieldName + "> "+ NEGATIVE_VALUE_ERROR_MESSAGE + value);
        }
        logger.debug(String.format(VALUE,"sum",value));
    }
}
