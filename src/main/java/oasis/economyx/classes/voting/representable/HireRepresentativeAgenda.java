package oasis.economyx.classes.voting.representable;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.personal.representable.RepresentativeHiredEvent;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda to hire a person as the representative of a representable
 */
public final class HireRepresentativeAgenda implements Agenda, References {
    public HireRepresentativeAgenda(@NonNull Representable representable, @NonNull Person candidate) {
        this.representable = representable;
        this.candidate = candidate;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return candidate.getName() + "을 " + representable.getName() + "의 대표로 선임합니다.";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Representable representable;
    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Person candidate;

    @NonNull
    @JsonIgnore
    public Representable getRepresentable() {
        return representable;
    }

    @NonNull
    @JsonIgnore
    public Person getCandidate() {
        return candidate;
    }

    @Override
    @JsonIgnore
    public void run() {
        Bukkit.getPluginManager().callEvent(new RepresentativeHiredEvent(
                getCandidate(),
                getRepresentable()
        ));
    }

    @JsonProperty
    private final Type type = Type.HIRE_REPRESENTATIVE;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"representable", "candidate"})
    private HireRepresentativeAgenda() {
        this.representable = null;
        this.candidate = null;
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
