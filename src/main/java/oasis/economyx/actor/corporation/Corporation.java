package oasis.economyx.actor.corporation;

import oasis.economyx.actor.types.Counterparty;
import oasis.economyx.actor.types.Employer;
import oasis.economyx.actor.types.Shared;

/**
 * A corporation is capable of issuing shares and being a counterparty in contracts
 */
public interface Corporation extends Employer, Counterparty, Shared {

}
