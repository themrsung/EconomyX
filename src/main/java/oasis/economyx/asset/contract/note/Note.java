package oasis.economyx.asset.contract.note;

import oasis.economyx.actor.types.Counterparty;
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

    public Note(@NonNull UUID uniqueId, @NonNull Counterparty counterparty, @NonNull AssetStack delivery, @Nullable DateTime expiry) {
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
    private final UUID uniqueId;
    @NonNull
    private final Counterparty counterparty;

    @NonNull
    private final AssetStack delivery;

    @Nullable
    private final DateTime expiry;

    @NonNull
    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public @NonNull AssetType getType() {
        return AssetType.NOTE;
    }

    @NonNull
    @Override
    public AssetStack getDelivery() {
        return delivery;
    }

    @Nullable
    @Override
    public DateTime getExpiry() {
        return expiry;
    }

    @NonNull
    @Override
    public Counterparty getCounterparty() {
        return counterparty;
    }
}
