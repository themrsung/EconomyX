package oasis.economyx.types.asset.contract.collateral;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.Contract;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * A collateral is not an issued security, but a representation of a potential liability
 * Used for backing loans, backing orders, etc.
 * Unlike other contracts, any actor can be a counterparty
 * Expiry is almost always null, as collaterals are usually cancelled manually
 */
public final class Collateral implements Contract {
    public Collateral() {
        this.uniqueId = UUID.randomUUID();
        this.counterparty = null;
        this.delivery = null;
        this.expiry = new DateTime();
    }

    public Collateral(@NonNull UUID uniqueId, @NonNull Actor counterparty, @NonNull AssetStack delivery, @Nullable DateTime expiry) {
        this.uniqueId = uniqueId;
        this.counterparty = counterparty;
        this.delivery = delivery;
        this.expiry = expiry;
    }

    public Collateral(Collateral other) {
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
    private final Type type = Type.COMMODITY;

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
    public @NonNull Collateral copy() {
        return new Collateral();
    }

    @Override
    public void onExpired(Actor holder) {
        // Do nothing
    }
}
