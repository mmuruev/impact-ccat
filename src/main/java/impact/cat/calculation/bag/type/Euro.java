package impact.cat.calculation.bag.type;

import impact.cat.calculation.bag.type.Currency;

/**
 * Created with IntelliJ IDEA.
 * User: mf
 * Date: 27.09.15
 * Time: 16:16
 */
public class Euro implements Currency {

    /**
     *  Currency name for human being
     */
    public static final String NAME = "Euro";

    /**
     *  Currency official code
     */
    public static final String CODE = "EUR";
    /**
     * @return - currency code
     */
    @Override
    public String getCurrencyCode() {
        return CODE;
    }

    /**
     * @return - currency name
     */
    @Override
    public String getDisplayName() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object currency) {
        return currency instanceof Euro;
    }
}
