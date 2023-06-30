package oasis.economyx.types.asset.contract.collateral;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

public final class CollateralStack extends ContractStack {
    public CollateralStack(@NonNull Collateral asset, @NonNegative long quantity) {
        super(asset, quantity, new CollateralMeta());
    }

    public CollateralStack(@NonNull Collateral asset, @NonNegative long quantity, @NonNull CollateralMeta meta) {
        super(asset, quantity, meta);
    }

    public CollateralStack(CollateralStack other) {
        super(other);
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CollateralMeta)) throw new IllegalArgumentException();
    }

    @Override
    @JsonIgnore
    public @NonNull Collateral getAsset() {
        return (Collateral) super.getAsset();
    }

    @Override
    @JsonIgnore
    public @NonNull CollateralMeta getMeta() {
        return (CollateralMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.COLLATERAL;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull CollateralStack copy() {
        return new CollateralStack(this);
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public CollateralStack() {
        super();
    }
}
