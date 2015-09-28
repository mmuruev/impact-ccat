package impact.cat.dao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import impact.cat.calculation.Interest;
import impact.cat.json.LoanCalculationsSerializer;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 21.09.15
 * Time: 22:21
 */
@JsonSerialize(using = LoanCalculationsSerializer.class)
public class LoanCalculations extends Loan {

    /**
     * Default token for outgoing messages
     */
    public static final String DEFAULT_TOKEN = "maximus";

    private BigDecimal interest;

    private BigDecimal totalSum;

    private String token = DEFAULT_TOKEN;

    public LoanCalculations(Interest calculated) {
        this(
                calculated.getSum().getAmount(),
                calculated.getDays(),
                calculated.getFinalInterest().getAmount(),
                calculated.getTotalSum().getAmount()
        );
    }

    public LoanCalculations(BigDecimal sum, int days, BigDecimal interest, BigDecimal totalSum) {
        super(sum, days);
        this.interest = interest;
        this.totalSum = totalSum;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public String getToken() {
        return token;
    }
}
