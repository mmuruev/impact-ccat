package impact.cat.calculation;

import impact.cat.calculation.bag.Money;

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

    private int days;

    public static int FIRST_INTEREST_DAY = 1;

    public Interest(Money sum, int days) {
        this.sum = sum;
        this.days = days;
    }

    public Money getSum() {
        return sum;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setSum(Money sum) {
        this.sum = sum;
    }

    public Money getFinalInterest(){
        Money finalInterest = new Money();
        for(int day= FIRST_INTEREST_DAY; day< days ; day++){
           finalInterest.add(getAmountForDay(day));
        }
        return finalInterest;
    }

    public Money getTotalSum(){
       return sum.add(getFinalInterest());
    }

    private Money getAmountForDay(int day){
        return sum.getPercentAmount(getPercentForDay(day));
    }
    /**
     *  Get percent for day according bussiness logic
     * @param day - current day
     * @return - percentage
     */
    private BigDecimal getPercentForDay(int day){
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
    private boolean isDividable(int value, int divider){
        return value % divider == 0;
    }
}
