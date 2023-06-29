package oasis.economyx.classes.actor.company.special;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class Mercenary extends Company {


    @JsonProperty
    private final ActorType type = ActorType.MERCENARY;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
