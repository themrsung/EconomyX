package oasis.economyx.types.asset.cash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;
import java.text.NumberFormat;

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

    @JsonProperty
    private final Cash asset;
    @JsonProperty
    private long quantity;
    @JsonProperty
    private CashMeta meta;

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
     *
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
     *
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
     * Multiplies two cash stacks
     *
     * @param other Other stack
     * @return The resulting cash stack
     * @throws IllegalArgumentException When a different denotation is given
     */
    @JsonIgnore
    public CashStack multiply(CashStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        CashStack result = new CashStack(this);
        result.setQuantity(getQuantity() * other.getQuantity());

        return result;
    }

    /**
     * Multiplies this cash stack by a given modifier
     *
     * @param modifier modifier
     * @return The resulting cash stack
     */
    @JsonIgnore
    public CashStack multiply(float modifier) {
        CashStack result = new CashStack(this);
        result.setQuantity(Math.round(getQuantity() * modifier));

        return result;
    }

    /**
     * Divides this cash stack by another
     *
     * @param other Other stack
     * @return The resulting stack
     * @throws IllegalArgumentException When a different denotation is given
     * @throws ArithmeticException      When the other stack has 0 as its quantity
     */
    @JsonIgnore
    public CashStack divide(CashStack other) throws IllegalArgumentException, ArithmeticException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        CashStack result = new CashStack(this);
        try {
            result.setQuantity(getQuantity() / other.getQuantity());
            return result;
        } catch (ArithmeticException e) {
            throw new ArithmeticException();
        }
    }

    /**
     * Divides this cash stack by a given denominator
     *
     * @param denominator denominator
     * @return The resulting cash stack
     */
    @JsonIgnore
    public CashStack divide(float denominator) throws ArithmeticException {
        CashStack result = new CashStack(this);
        try {
            result.setQuantity(Math.round(getQuantity() / denominator));
        } catch (ArithmeticException e) {
            throw new ArithmeticException();
        }

        return result;
    }

    /**
     * Whether this stack is bigger than the other
     *
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
     *
     * @param other Stack to compare against
     * @return Whether this is bigger
     * @throws IllegalArgumentException When a different denotation is given
     */
    @JsonIgnore
    public boolean isGreaterThan(CashStack other) throws IllegalArgumentException {
        if (!getAsset().equals(other.getAsset())) throw new IllegalArgumentException();

        return getQuantity() > other.getQuantity();
    }

    public int compare(CashStack asset) throws IllegalArgumentException {
        return this.equals(asset) ? 0 : (this.isSmallerThan(asset) ? -1 : 1);
    }

    @Override
    @JsonIgnore
    public @NonNull AssetMeta getMeta() {
        return new CashMeta(meta);
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CashMeta)) throw new IllegalArgumentException();

        this.meta = (CashMeta) meta;
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
    public @NonNull CashStack copy() {
        return new CashStack(this);
    }

    @Override
    public @NonNull String format(@NonNull EconomyState state) {
        return NumberFormat.getIntegerInstance().format(getQuantity()) + " " + getAsset().getName();
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
