package oasis.economyx.types.asset.property;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.services.Protector;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

public final class PropertyMeta implements AssetMeta {
    public PropertyMeta(@Nullable Protector protector) {
        this.protector = protector;
    }

    public PropertyMeta() {
        this.protector = null;
    }

    public PropertyMeta(PropertyMeta other) {
        this.protector = other.protector;
    }

    @Nullable
    @JsonProperty
    @JsonIdentityReference
    private Protector protector;

    @Nullable
    @JsonIgnore
    public Protector getProtector() {
        return protector;
    }

    @JsonIgnore
    public void setProtector(@Nullable Protector protector) {
        this.protector = protector;
    }

    @NonNull
    @JsonProperty
    private final AssetType type = AssetType.PROPERTY;

    @NotNull
    @Override
    @JsonIgnore
    public AssetType getType() {
        return type;
    }
}
