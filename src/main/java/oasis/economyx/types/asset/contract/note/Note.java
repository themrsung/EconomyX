package oasis.economyx.types.asset.contract.note;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.Contract;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * A note can represent the unconditional delivery of any asset (even another note)
 */
public final class Note implements Contract {
    public Note() {
        this.uniqueId = UUID.randomUUID();
        this.counterparty = null;
        this.delivery = null;
        this.expiry = new DateTime();
    }

    public Note(@NonNull UUID uniqueId, @NonNull Actor counterparty, @NonNull AssetStack delivery, @Nullable DateTime expiry) {
        this.uniqueId = uniqueId;
        this.counterparty = counterparty;
        this.delivery = delivery;
        this.expiry = expiry;
    }

    public Note(Note other) {
        this.uniqueId = other.uniqueId;
        this.counterparty = other.counterparty;
        this.delivery = other.delivery;
        this.expiry = other.expiry;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;
    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Actor counterparty;

    @NonNull
    @JsonProperty
    private final AssetStack delivery;

    @Nullable
    @JsonProperty
    private final DateTime expiry;

    @NonNull
    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @JsonProperty
    private final Type type = Type.NOTE;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @NonNull
    @Override
    @JsonIgnore
    public AssetStack getDelivery() {
        return delivery;
    }

    @Nullable
    @Override
    @JsonIgnore
    public DateTime getExpiry() {
        return expiry;
    }

    @NonNull
    @Override
    @JsonIgnore
    public Actor getCounterparty() {
        return counterparty;
    }

    @Override
    @JsonIgnore
    public @NonNull Note copy() {
        return new Note(this);
    }

    @Override
    public void onExpired(Actor holder) {
        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                counterparty,
                holder,
                getDelivery(),
                PaymentEvent.Cause.NOTE_EXPIRED
        ));
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        delivery.initialize(state);

        for (Actor a : state.getActors()) {
            if (a.getUniqueId().equals(counterparty.getUniqueId())) {
                counterparty = a;
                break;
            }
        }
    }
}
