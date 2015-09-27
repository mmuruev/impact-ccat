package impact.cat.calculation.bag;

import impact.cat.calculation.bag.type.Currency;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 27.09.15
 * Time: 16:41
 */
public class IncompatibleCurrenciesException extends RuntimeException {

    private static final String WRONG_CURRENCY_ERROR_MESSAGE = "Currency %s and %s are different";

    public IncompatibleCurrenciesException(String error) {
        super(error);
    }

    public IncompatibleCurrenciesException(Currency currency, Currency currency1) {
        this(String.format(WRONG_CURRENCY_ERROR_MESSAGE, currency, currency1));
    }
}


