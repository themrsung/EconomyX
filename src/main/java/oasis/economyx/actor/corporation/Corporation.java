package oasis.economyx.actor.corporation;

import oasis.economyx.actor.types.employment.Employer;
import oasis.economyx.actor.types.ownership.Shared;
import oasis.economyx.asset.cash.Cash;

/**
 * A corporation is capable of issuing shares
 */
public interface Corporation extends Employer, Shared {
    /**
     * Gets the currency this corporation accepts
     * @return Currency
     */
    Cash getCurrency();
}

