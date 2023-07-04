package oasis.economyx.types.asset.property;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PropertyMeta implements AssetMeta, References {
    public PropertyMeta(@Nullable PropertyProtector protector) {
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
    private PropertyProtector protector;

    @Nullable
    @JsonIgnore
    public PropertyProtector getProtector() {
        return protector;
    }

    @JsonIgnore
    public void setProtector(@Nullable PropertyProtector protector) {
        this.protector = protector;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.PROPERTY;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        if (protector != null) {
            for (PropertyProtector orig : state.getProtectors()) {
                if (protector.getUniqueId().equals(orig.getUniqueId())) {
                    protector = orig;
                    break;
                }
            }
        }
    }
}
