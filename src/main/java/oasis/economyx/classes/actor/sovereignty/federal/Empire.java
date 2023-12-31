package oasis.economyx.classes.actor.sovereignty.federal;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.sovereignty.Sovereignty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.sovereign.Federal;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Empire extends Sovereignty implements Federal {
    /**
     * Creates a new empire
     *
     * @param uniqueId      Unique ID of this empire
     * @param name          Name of this empire
     * @param currency      Currency used to pay the emperor
     * @param foundingState Founding state (cannot be null)
     */
    public Empire(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Sovereign foundingState) {
        super(uniqueId, name, currency);

        this.memberStates = List.of(foundingState);
        this.representativeState = foundingState;
    }

    public Empire() {
        super();

        this.memberStates = new ArrayList<>();
        this.representativeState = null;
    }

    public Empire(Empire other) {
        super(other);

        this.memberStates = other.memberStates;
        this.representativeState = other.representativeState;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final List<Sovereign> memberStates;

    @Nullable
    @JsonProperty
    @JsonIdentityReference
    private Sovereign representativeState;

    @Override
    @JsonIgnore
    public @NonNull List<Sovereign> getMemberStates() {
        return new ArrayList<>(memberStates);
    }

    @Override
    @JsonIgnore
    public void addMemberState(@NonNull Sovereign state) {
        memberStates.add(state);
    }

    @Override
    @JsonIgnore
    public void removeMemberState(@NonNull Sovereign state) {
        memberStates.remove(state);
    }

    @Override
    @JsonIgnore
    public @Nullable Sovereign getRepresentativeState() {
        return representativeState;
    }

    @Override
    @JsonIgnore
    public void setRepresentativeState(@NonNull Sovereign state) throws IllegalArgumentException {
        if (!getMemberStates().contains(state)) throw new IllegalArgumentException();

        this.representativeState = state;
    }

    @JsonProperty
    private final Type type = Type.EMPIRE;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        super.initialize(state);

        List<Sovereign> refs = getMemberStates();
        memberStates.clear();

        for (Sovereign orig : state.getSovereigns()) {
            for (Sovereign ref : refs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    memberStates.add(orig);
                }
            }
        }

        if (representativeState != null) {
            for (Sovereign orig : state.getSovereigns()) {
                if (representativeState.getUniqueId().equals(orig.getUniqueId())) {
                    representativeState = orig;
                    break;
                }
            }
        }
    }
}
