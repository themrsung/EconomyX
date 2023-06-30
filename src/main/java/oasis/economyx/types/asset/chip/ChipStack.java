package oasis.economyx.types.asset.chip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * A stack of chips
 */
public final class ChipStack implements AssetStack {
    public ChipStack(Cash asset, long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new ChipMeta();
    }

    public ChipStack(Cash asset, long quantity, ChipMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public ChipStack(ChipStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @JsonProperty
    private final Cash asset;
    @JsonProperty
    private long quantity;
    @JsonProperty
    private ChipMeta meta;

    @Override
    @JsonIgnore
    public @NonNull Cash getAsset() {
        return new Cash(asset);
    }

    @Override
    @JsonIgnore
    public @NonNegative long getQuantity() {
        return quantity;
    }

    @Override
    @JsonIgnore
    public void setQuantity(@NonNegative long quantity) {
        this.quantity = quantity;
    }

    @Override
    @JsonIgnore
    public void addQuantity(@NonNegative long delta) {
        this.quantity += delta;
    }

    @Override
    @JsonIgnore
    public void removeQuantity(@NonNegative long delta) throws IllegalArgumentException {
        if (this.quantity - delta < 0L) throw new IllegalArgumentException();

        this.quantity -= delta;
    }

    /**
     * Adds two cash stacks
     * @param other Delta
     * @return The resulting cash stack
     * @throws IllegalArgumentException When a different denotation is given
     */
    @JsonIgnore
    public ChipStack add(ChipStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        ChipStack result = new ChipStack(this);
        result.addQuantity(other.getQuantity());

        return result;
    }

    /**
     * Subtracts cash stacks
     * @param other Delta
     * @return The resulting cash stack
     * @throws IllegalArgumentException When a different denotation is given, or the resulting cash stack has negative quantity
     */
    @JsonIgnore
    public ChipStack subtract(ChipStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        ChipStack result = new ChipStack(this);
        result.removeQuantity(other.getQuantity());

        return result;
    }

    /**
     * Whether this stack is bigger than the other
     * @param other Stack to compare against
     * @return Whether this is bigger
     * @throws IllegalArgumentException When a different denotation is given
     */
    @JsonIgnore
    public boolean isSmallerThan(ChipStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        return getQuantity() < other.getQuantity();
    }

    /**
     * Whether this stack is smaller than the other
     * @param other Stack to compare against
     * @return Whether this is bigger
     * @throws IllegalArgumentException When a different denotation is given
     */
    @JsonIgnore
    public boolean isGreaterThan(ChipStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        return getQuantity() > other.getQuantity();
    }

    public int compare(ChipStack asset) throws IllegalArgumentException {
        return this.equals(asset) ? 0 : (this.isSmallerThan(asset) ? -1 : 1);
    }

    @Override
    @JsonIgnore
    public @NonNull ChipMeta getMeta() {
        return new ChipMeta(meta);
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof ChipMeta)) throw new IllegalArgumentException();

        this.meta = (ChipMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.CASH;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull ChipStack copy() {
        return new ChipStack(this);
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    private ChipStack() {
        this.asset = new Cash();
        this.quantity = 0L;
        this.meta = new ChipMeta();
    }
}
