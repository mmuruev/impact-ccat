package impact.cat.dao;

import impact.cat.calculation.Interest;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 21.09.15
 * Time: 22:21
 */
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

}
