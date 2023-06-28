package oasis.economyx.classes;

import oasis.economyx.actor.person.Person;
import oasis.economyx.actor.sovereign.Sovereign;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Sovereignty extends EconomicActor implements Sovereign {
    public Sovereignty() {
        this.headOfState = null;
    }

    public Sovereignty(Sovereignty other) {
        super(other);
        this.headOfState = other.headOfState;
    }

    @Nullable
    private Person headOfState;

    @Override
    public @Nullable Person getRepresentative() {
        return headOfState;
    }

    @Override
    public void setRepresentative(@Nullable Person representative) {
        this.headOfState = representative;
    }

}
