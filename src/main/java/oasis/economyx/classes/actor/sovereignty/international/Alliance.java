package oasis.economyx.classes.actor.sovereignty.international;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.classes.actor.sovereignty.Sovereignty;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class Alliance extends Sovereignty {


    @JsonProperty
    private final ActorType type = ActorType.ALLIANCE;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
