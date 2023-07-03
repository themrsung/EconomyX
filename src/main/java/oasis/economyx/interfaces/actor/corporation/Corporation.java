package oasis.economyx.interfaces.actor.corporation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.types.asset.cash.Cash;

/**
 * A corporation is capable of issuing shares
 */
public interface Corporation extends Employer, Shared, Democratic {
    /**
     * Gets the currency this corporation accepts
     *
     * @return Currency
     */
    @JsonIgnore
    Cash getCurrency();
}

