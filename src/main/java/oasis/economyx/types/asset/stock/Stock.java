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
        this.name = null;
    }

    public Stock(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.name = null;
    }

    public Stock(@NonNull UUID uniqueId, @NonNull String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public Stock(Stock other) {
        this.uniqueId = other.uniqueId;
        this.name = other.name;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    private String name;


    @Override
    @JsonIgnore
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }

    @NonNull
    @Override
    @JsonIgnore
    public String getName() {
        return name;
    }

    @Override
    @JsonIgnore
    public void setName(@NonNull String name) {
        this.name = name;
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
