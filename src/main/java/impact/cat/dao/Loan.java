package impact.cat.dao;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import impact.cat.json.LoanDeserializer;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 21.09.15
 * Time: 22:18
 */
@JsonDeserialize(using = LoanDeserializer.class)
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
