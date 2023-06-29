package oasis.economyx.classes.actor.sovereignty.singular;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.classes.actor.sovereignty.Sovereignty;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class Principality extends Sovereignty {


    @JsonProperty
    private final ActorType type = ActorType.PRINCIPALITY;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
