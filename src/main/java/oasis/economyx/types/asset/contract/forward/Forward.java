package oasis.economyx.types.asset.contract.forward;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.Contract;
import org.bukkit.Bukkit;
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

    @Override
    @JsonIgnore
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }

    @JsonProperty
    private final Type type = Type.FORWARD;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull AssetStack getDelivery() {
        return delivery;
    }

    @Override
    @JsonIgnore
    public @Nullable DateTime getExpiry() {
        return expiry;
    }

    @Override
    @JsonIgnore
    public @NonNull Actor getCounterparty() {
        return counterparty;
    }

    @Override
    @JsonIgnore
    public @NonNull Forward copy() {
        return new Forward(this);
    }

    @Override
    @JsonIgnore
    public void onExpired(Actor holder) {
        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                counterparty,
                holder,
                getDelivery(),
                PaymentEvent.Cause.FORWARD_EXPIRED
        ));
    }
}
