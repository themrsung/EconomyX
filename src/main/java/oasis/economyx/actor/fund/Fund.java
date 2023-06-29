package oasis.economyx.actor.fund;

import oasis.economyx.actor.types.governance.Representable;
import oasis.economyx.actor.types.ownership.Shared;

/**
 * A fund is representable and has shared ownership, but cannot employ persons
 */
public interface Fund extends Representable, Shared {
}
