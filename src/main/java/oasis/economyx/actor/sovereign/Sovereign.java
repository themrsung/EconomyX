package oasis.economyx.actor.sovereign;

import oasis.economyx.actor.types.Counterparty;
import oasis.economyx.actor.types.Representable;

/**
 * A sovereign actor must be represented by a person, but is not owned by any other actor
 * Sovereigns are also capable of issuing and fulfilling contracts as a counterparty
 */
public interface Sovereign extends Representable, Counterparty {

}
