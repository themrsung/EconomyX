package oasis.economyx.types.asset.commodity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.meta.Purchasable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;
import org.spongepowered.api.entity.attribute.AttributeModifier;

import java.util.ArrayList;
import java.util.Collection;

public final class CommodityMeta implements AssetMeta, Purchasable {
    public CommodityMeta() {
        this.purchasePrice = null;
        this.purchaseDate = null;
        this.attributeModifiers = new ArrayList<>();
        this.expiry = null;
    }

    public CommodityMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate, @Nullable DateTime expiry) {
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.attributeModifiers = new ArrayList<>();
        this.expiry = expiry;
    }

    public CommodityMeta(CommodityMeta other) {
        this.purchasePrice = other.purchasePrice;
        this.purchaseDate = other.purchaseDate;
        this.attributeModifiers = other.attributeModifiers;
        this.expiry = other.expiry;
    }

    @Nullable
    @JsonProperty
    private CashStack purchasePrice;

    @Nullable
    @JsonProperty
    private DateTime purchaseDate;

    @NonNull
    @JsonProperty
    private Collection<AttributeModifier> attributeModifiers;

    /**
     * When this commodity expires and is deleted from the virtual inventory
     * Can be null
     */
    @Nullable
    @JsonProperty
    private DateTime expiry;


    @Override
    @JsonIgnore
    public @Nullable CashStack getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    @JsonIgnore
    public @Nullable DateTime getPurchaseDate() {
        return purchaseDate;
    }

    @JsonIgnore
    @NonNull
    public Collection<AttributeModifier> getAttributeModifiers() {
        return attributeModifiers;
    }

    @Nullable
    @JsonIgnore
    public DateTime getExpiry() {
        return expiry;
    }

    @JsonIgnore
    public void setAttributeModifiers(@NonNull Collection<AttributeModifier> attributeModifiers) {
        this.attributeModifiers = attributeModifiers;
    }

    @Override
    @JsonIgnore
    public void setPurchasePrice(@Nullable CashStack price) {
        this.purchasePrice = price;
    }

    @Override
    @JsonIgnore
    public void setPurchaseDate(@Nullable DateTime date) {
        this.purchaseDate = date;
    }

    @JsonIgnore
    public void setExpiry(@Nullable DateTime expiry) {
        this.expiry = expiry;
    }

    /**
     * Meta with the same attributes are considered equal, regardless of purchase other properties
     * @param meta Meta to compare to
     * @return Whether the data is equal
     */
    @JsonIgnore
    public boolean equals(CommodityMeta meta) {
        return this.attributeModifiers.equals(meta.attributeModifiers);
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.COMMODITY;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
