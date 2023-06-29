package oasis.economyx.actor.types.institutional;

import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * An interest rate provide provides a base rate for banking
 */
public interface InterestRateProvider extends Institutional {
    /**
     * Gets the interest rate of this provider
     * Interest is only paid to deposits in cash with the same currency as this banker
     * Denoted in raw rate (e.g. 1% -> 0.01f)
     *
     * @return Annual interest rate
     */
    float getInterestRate();

    /**
     * Converts interest rate to daily rate
     * Accounts for leap years
     *
     * @return Daily interest rate
     */
    default float getDailyInterestRate() {
        LocalDate now = new LocalDate();
        int days = Days.daysBetween(now, now.plusYears(1)).getDays();

        return (float) (Math.pow(getInterestRate() + 1, (double) 1 / days) - 1);
    }

    /**
     * Converts interest rate to hourly rate
     * @return Hourly interest rate
     */
    default float getHourlyInterestRate() {
        return (float) (Math.pow(getDailyInterestRate() + 1, (double) 1 / 24) - 1);
    }

    /**
     * Sets the interest rate of this provider
     * Interest is only paid to deposits in cash with the same currency as this banker
     * @param rate Annual interest rate
     */
    void setInterestRate(float rate);
}
