package impact.cat.calculation.bag;

import impact.cat.calculation.bag.type.Currency;
import impact.cat.calculation.bag.type.Euro;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 27.09.15
 * Time: 16:19
 */
public class Money {

    /**
     *  Money money
     */
    private BigDecimal amount;
    /**
     *  Money type
     */
    private Currency currency;

    /**
     *  Rounding for money
     */
    public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;

    /**
     *  scale of the {@code BigDecimal} quotient to be returned.
     */
    public static final int SCALE  = 2;

    /**
     *  For percentage calculation
     */
    private static final BigDecimal HUNDRED = new BigDecimal(100);

    /**
     *  Empty wallet
     */
    public Money(){
        this(BigDecimal.ZERO);
    }

    public Money(BigDecimal amount){
        this.amount = amount;
        this.currency = getDefaultCurrency();
    }

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(amount.multiply(multiplier), currency);
    }

    public Money add(Money amount) {
        moneyCheck(amount);
        return new Money(getAmount().add(amount.getAmount()), getCurrency());
    }

    public Currency getDefaultCurrency(){
        return new Euro();
    }

    /**
     * Get current amount + percentage slice from current amount
     * @param percent - percentage
     * @return - current amount + percent from current amount
     */
    public Money getAmountWithPercent(BigDecimal percent){
        return  new Money(percentage(getAmount(),percent).add(getAmount()),getCurrency());
    }

    /**
     * Get percent from current amount
     * @param percent - percentage
     * @return - percent slice in money
     */
    public Money getPercentAmount(BigDecimal percent){
        return new Money(percentage(getAmount(),percent),getCurrency());
    }

    public BigDecimal getAmount() {
        return amount.stripTrailingZeros();
    }

    public Currency getCurrency() {
        return currency;
    }

    private static BigDecimal percentage(BigDecimal base, BigDecimal percent){
        return percentage(base, percent, DEFAULT_ROUNDING);
    }

    private static BigDecimal percentage(BigDecimal base, BigDecimal percent, RoundingMode roundingMode){
        return base.multiply(percent).divide(HUNDRED, SCALE, roundingMode);
    }

    private void moneyCheck(Money money){
        if (!getCurrency().equals(money.getCurrency())) {
            throw new IncompatibleCurrenciesException(getCurrency(), money.getCurrency());
        }
    }

}
