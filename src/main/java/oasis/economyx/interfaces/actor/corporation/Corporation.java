package oasis.economyx.interfaces.actor.corporation;

import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.types.asset.cash.Cash;

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
