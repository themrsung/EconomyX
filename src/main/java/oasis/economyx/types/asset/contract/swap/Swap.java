package oasis.economyx.types.asset.contract.swap;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.Contract;
import oasis.economyx.interfaces.trading.PriceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;
import org.spongepowered.api.Sponge;

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
    private final Type type = Type.SWAP;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    /**
     * Gets the current value of this swap from the perspective of its holder
     * @return Expected delivery on expiry
     * @throws IllegalArgumentException When the delivery is negative (this is a liability)
     */
    @NonNull
    @Override
    @JsonIgnore
    public CashStack getDelivery() throws IllegalArgumentException {
        CashStack delivery = new CashStack(denotation, 0L);

        CashStack basePrice = base.getPrice();
        CashStack quotePrice = quote.getPrice();

        return quotePrice.subtract(basePrice);
    }

    /**
     * Gets the current value of this swap from the perspective of its counterparty
     * @return Expected delivery on expiry negated
     * @throws IllegalArgumentException When the negated delivery is negative (this is an asset)
     */
    @NonNull
    @JsonIgnore
    public CashStack getNegatedDelivery() throws IllegalArgumentException {
        CashStack negated = new CashStack(denotation, 0L);

        CashStack basePrice = base.getPrice();
        CashStack quotePrice = quote.getPrice();

        return basePrice.subtract(quotePrice);
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

    @Override
    @JsonIgnore
    public void onExpired(Actor holder) {
        try {
            CashStack delivery = getDelivery();
            Sponge.eventManager().post(new PaymentEvent(
                    counterparty,
                    holder,
                    delivery,
                    null // TODO
            ));
        } catch (IllegalArgumentException e) {
            CashStack negated = getNegatedDelivery();
            Sponge.eventManager().post(new PaymentEvent(
                    holder,
                    counterparty,
                    negated,
                    null // TODO
            ));
        }
    }
}
