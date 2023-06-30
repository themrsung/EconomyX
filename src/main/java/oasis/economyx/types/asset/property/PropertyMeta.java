package oasis.economyx.types.asset.property;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.services.Protector;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import org.checkerframework.checker.nullness.qual.Nullable;

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
    
    @JsonProperty
    private final Asset.Type type = Asset.Type.PROPERTY;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
