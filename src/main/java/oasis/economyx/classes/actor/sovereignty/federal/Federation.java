package oasis.economyx.classes.actor.sovereignty.federal;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.sovereign.Federal;
import oasis.economyx.classes.actor.sovereignty.Sovereignty;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Federation extends Sovereignty implements Federal {
    /**
     * Creates a new federation
     * @param uniqueId Unique ID of this federation
     * @param name Name of this federation
     * @param currency Currency used to pay the foundation's representative
     * @param foundingState Founding state (cannot be null)
     */
    public Federation(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Sovereign foundingState) {
        super(uniqueId, name, currency);

        this.memberStates = List.of(foundingState);
        this.representativeState = foundingState;
    }

    public Federation() {
        this.memberStates = new ArrayList<>();
        this.representativeState = null;
    }

    public Federation(Federation other) {
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
    private final Type type = Type.FEDERATION;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
