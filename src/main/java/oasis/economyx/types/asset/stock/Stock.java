package oasis.economyx.types.asset.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Right to partial ownership of a corporation
 */
public final class Stock implements Asset {
    public Stock() {
        this.uniqueId = UUID.randomUUID();
    }

    public Stock(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Stock(Stock other) {
        this.uniqueId = other.uniqueId;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @Override
    @JsonIgnore
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }


    @JsonProperty
    private final Type type = Type.STOCK;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Stock copy() {
        return new Stock(this);
    }
}
