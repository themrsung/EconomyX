package oasis.economyx.asset.contract.swap;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetType;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.asset.cash.CashMeta;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.asset.contract.Contract;
import oasis.economyx.interfaces.trading.PriceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

public final class Swap implements Contract {
    public Swap() {
        this.uniqueId = UUID.randomUUID();
        this.counterparty = null;
        this.expiry = new DateTime();
        this.denotation = new Cash();
        this.base = null;
        this.quote = null;
    }

    public Swap(@NonNull UUID uniqueId, @NonNull Actor counterparty, @NonNull Cash denotation, @NonNull PriceProvider base, @NonNull PriceProvider quote, @Nullable DateTime expiry) {
        this.uniqueId = uniqueId;
        this.counterparty = counterparty;
        this.denotation = denotation;
        this.base = base;
        this.quote = quote;
        this.expiry = expiry;
    }

    public Swap(Swap other) {
        this.uniqueId = other.uniqueId;
        this.counterparty = other.counterparty;
        this.denotation = other.denotation;
        this.base = other.base;
        this.quote = other.quote;
        this.expiry = other.expiry;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;
    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Actor counterparty;

    @NonNull
    @JsonProperty
    private final Cash denotation;

    /**
     * The base price of this swap
     */
    @NonNull
    @JsonProperty
    private final PriceProvider base;

    /**
     * The quote price of this swap
     */
    @NonNull
    @JsonProperty
    private final PriceProvider quote;

    @Nullable
    @JsonProperty
    private final DateTime expiry;

    @NonNull
    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @JsonProperty
    private final AssetType type = AssetType.SWAP;

    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
        return type;
    }

    /**
     * Gets the current value of this swap from the perspective of its holder
     * @return Expected delivery on expiry
     */
    @NonNull
    @Override
    @JsonIgnore
    public CashStack getDelivery() {
        CashStack delivery = new CashStack(denotation, 0, new CashMeta());

        CashStack basePrice = base.getPrice();
        CashStack quotePrice = quote.getPrice();

        return quotePrice.subtract(basePrice);
    }

    @NonNull
    @JsonIgnore
    public Cash getDenotation() {
        return denotation;
    }

    @NonNull
    @JsonIgnore
    public PriceProvider getBase() {
        return base;
    }

    @NonNull
    @JsonIgnore
    public PriceProvider getQuote() {
        return quote;
    }

    @Nullable
    @Override
    @JsonIgnore
    public DateTime getExpiry() {
        return expiry;
    }

    @NonNull
    @Override
    @JsonIgnore
    public Actor getCounterparty() {
        return counterparty;
    }

    @Override
    @JsonIgnore
    public @NonNull Swap copy() {
        return new Swap(this);
    }
}
