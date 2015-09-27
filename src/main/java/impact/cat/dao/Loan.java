package impact.cat.dao;


import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 21.09.15
 * Time: 22:18
 */

public class Loan {

    private BigDecimal sum;

    private int days;

    public Loan(BigDecimal sum, int days) {
        this.sum = sum;
        this.days = days;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public int getDays() {
        return days;
    }
}
