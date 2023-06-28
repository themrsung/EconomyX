package oasis.economyx.asset.contract.forward;

import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetType;
import oasis.economyx.asset.contract.Contract;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * A forward can represent the future delivery of any asset in exchange for cash (even another forward contract)
 */
public final class Forward implements Contract {
    public Forward() {
        this.uniqueId = UUID.randomUUID();
        this.counterparty = null;
        this.delivery = null;
        this.expiry = new DateTime();
    }

    public Forward(@NonNull UUID uniqueId, @NonNull Actor counterparty, @NonNull AssetStack delivery, @Nullable DateTime expiry) {
        this.uniqueId = uniqueId;
        this.counterparty = counterparty;
        this.delivery = delivery;
        this.expiry = expiry;
    }

    public Forward(Forward other) {
        this.uniqueId = other.uniqueId;
        this.counterparty = other.counterparty;
        this.delivery = other.delivery;
        this.expiry = other.expiry;
    }

    @NonNull
    private final UUID uniqueId;
    @NonNull
    private final Actor counterparty;
    @NonNull
    private final AssetStack delivery;
    @Nullable
    private final DateTime expiry;

    @Override
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public @NonNull AssetType getType() {
        return AssetType.FORWARD;
    }

    @Override
    public @NonNull AssetStack getDelivery() {
        return delivery;
    }

    @Override
    public @Nullable DateTime getExpiry() {
        return expiry;
    }

    @Override
    public @NonNull Actor getCounterparty() {
        return counterparty;
    }
}
