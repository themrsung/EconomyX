package oasis.economyx.interfaces.actor.types.institutional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A base interface for institutional actors
 */
public interface Institutional extends Representable, Employer {
    @NonNull
    @JsonIgnore
    Sovereign getParent();
}
