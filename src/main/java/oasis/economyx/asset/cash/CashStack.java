package oasis.economyx.asset.cash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * A stack of cash
 */
public final class CashStack implements AssetStack {
    public CashStack(Cash asset, long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new CashMeta();
    }

    public CashStack(Cash asset, long quantity, CashMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public CashStack(CashStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    private final Cash asset;
    private long quantity;
    private CashMeta meta;

    @Override
    public @NonNull Asset getAsset() {
        return new Cash(asset);
    }

    @Override
    public @NonNegative long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(@NonNegative long quantity) {
        this.quantity = quantity;
    }

    @Override
    public void addQuantity(@NonNegative long delta) {
        this.quantity += delta;
    }

    @Override
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
    public CashStack add(CashStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        CashStack result = new CashStack(this);
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
    public CashStack subtract(CashStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        CashStack result = new CashStack(this);
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
    public boolean isSmallerThan(CashStack other) throws IllegalArgumentException {
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
    public boolean isGreaterThan(CashStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        return getQuantity() > other.getQuantity();
    }

    @Override
    public @NonNull AssetMeta getMeta() {
        return new CashMeta(meta);
    }

    @Override
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CashMeta)) throw new IllegalArgumentException();

        this.meta = (CashMeta) meta;
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    private CashStack() {
        this.asset = new Cash();
        this.quantity = 0L;
        this.meta = new CashMeta();
    }
}
