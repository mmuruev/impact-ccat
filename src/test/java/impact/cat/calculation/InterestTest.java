package impact.cat.calculation;

import impact.cat.calculation.bag.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by maksim on 28/09/15.
 */
public class InterestTest {

    private Interest interest;

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDayOnSetter() throws Exception {
        interest.setDays(new BigDecimal(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSumOnSetter() throws Exception {
        interest.setSum(new Money(new BigDecimal("-1")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDayOnConstructor() throws Exception {
        new Interest(new Money(new BigDecimal("123")), new BigDecimal(-1));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeSumOnConstructor() throws Exception {
        new Interest(new Money(new BigDecimal("-1")), new BigDecimal(5));
    }

    @Test
    public void testGetFinalInterestForOneDay() throws Exception {
        interest.setDays(new BigDecimal(1));
        interest.setSum(new Money(new BigDecimal("100")));
        Assert.assertEquals(new BigDecimal("4"), interest.getFinalInterest().getAmount()); //  interest: 4  %4
    }

    @Test
    public void testGetFinalInterestForThreeDays() throws Exception {
        interest.setDays(new BigDecimal(3));
        interest.setSum(new Money(new BigDecimal("100")));
        Assert.assertEquals(new BigDecimal("9"), interest.getFinalInterest().getAmount()); //  interest: 9  %4 + %4 + %1
    }

    @Test
    public void testGetFinalInterestForFiveDays() throws Exception {
        interest.setDays(new BigDecimal(5));
        interest.setSum(new Money(new BigDecimal("100")));
        Assert.assertEquals(new BigDecimal("15"), interest.getFinalInterest().getAmount()); //  interest: 15  %4 + %4 + %1 + %4 + %2
    }

    @Test
    public void testGetFinalInterestForFifteenDays() throws Exception {
        interest.setDays(new BigDecimal(15));
        interest.setSum(new Money(new BigDecimal("100")));
        Assert.assertEquals(new BigDecimal("43"), interest.getFinalInterest().getAmount()); //  interest: 43  %4 + %4 + %1 + %4 + %2 + 1% + %4 + %4 + 1% + %2 + %4 + 1% + %4 + %4 + %3
    }


    @Test
    public void testGetFinalInterest() throws Exception {
        Assert.assertEquals(new BigDecimal("18.45"), interest.getFinalInterest().getAmount()); //  interest: 18.45
    }

    @Test
    public void testGetTotalSum() throws Exception {
        Assert.assertEquals(new BigDecimal("141.45"), interest.getTotalSum().getAmount()); //  totalSum: 141.45
    }

    @Before
    public void setUp() throws Exception {
        interest = new Interest(new Money(new BigDecimal("123")), new BigDecimal(5));   // Incoming messages will look like following:{ sum: 123, days: 5 }

    }
}