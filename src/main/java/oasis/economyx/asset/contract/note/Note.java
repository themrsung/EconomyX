package oasis.economyx.asset.contract.note;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetType;
import oasis.economyx.asset.contract.Contract;
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
    private final Actor counterparty;

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
    private final AssetType type = AssetType.NOTE;

    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
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
}
