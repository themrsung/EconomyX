package oasis.economyx.interfaces.actor.fund;

import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.ownership.Shared;

/**
 * A fund is representable and has shared ownership, but is not an employer.
 */
public interface Fund extends Representable, Shared {
}
