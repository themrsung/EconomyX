package oasis.economyx.classes.voting.representable;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.personal.representable.RepresentativeFiredEvent;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda to fire the representative of a representable
 */
public final class FireRepresentativeAgenda implements Agenda, References {
    public FireRepresentativeAgenda(@NonNull Representable representable) {
        this.representable = representable;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return representable.getName() + "의 대표를 해임합니다.";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Representable representable;

    @NonNull
    @JsonIgnore
    public Representable getRepresentable() {
        return representable;
    }

    @Override
    @JsonIgnore
    public void run() {
        Bukkit.getPluginManager().callEvent(new RepresentativeFiredEvent(
                getRepresentable()
        ));
    }

    @JsonProperty
    private final Type type = Type.FIRE_REPRESENTATIVE;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"representable"})
    private FireRepresentativeAgenda() {
        this.representable = null;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        for (Representable orig : state.getRepresentables()) {
            if (orig.getUniqueId().equals(representable.getUniqueId())) {
                representable = orig;
                break;
            }
        }
    }
}
