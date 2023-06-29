package oasis.economyx.classes.actor.institution.tripartite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.classes.actor.institution.Institution;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class Legislature extends Institution {


    @JsonProperty
    private final ActorType type = ActorType.LEGISLATURE;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
