package oasis.economyx.types.asset.contract.option;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.AssetType;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.Contract;
import oasis.economyx.interfaces.trading.PriceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;
import org.spongepowered.api.Sponge;

import java.util.UUID;

/**
 * An options contract
 * Perpetual options are not supported (Expiry must be designated)
 */
public final class Option implements Contract {
    public Option() {
        this.uniqueId = UUID.randomUUID();
        this.counterparty = null;
        this.delivery = null;
        this.expiry = new DateTime();
        this.market = null;
        this.optionType = null;
        this.exercisePrice = null;
    }

    public Option(@NonNull UUID uniqueId, @NonNull Actor counterparty, @NonNull AssetStack delivery, @NonNull DateTime expiry, @NonNull PriceProvider market, @NonNull OptionType optionType, @NonNull CashStack exercisePrice) {
        this.uniqueId = uniqueId;
        this.counterparty = counterparty;
        this.delivery = delivery;
        this.expiry = expiry;
        this.market = market;
        this.optionType = optionType;
        this.exercisePrice = exercisePrice;
    }

    public Option(Option other) {
        this.uniqueId = other.uniqueId;
        this.counterparty = other.counterparty;
        this.delivery = other.delivery;
        this.expiry = other.expiry;
        this.market = other.market;
        this.optionType = other.optionType;
        this.exercisePrice = other.exercisePrice;
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
    private final AssetStack delivery;

    @NonNull
    @JsonProperty
    private final DateTime expiry;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final PriceProvider market;

    @NonNull
    @JsonProperty
    private final OptionType optionType;

    @NonNull
    @JsonProperty
    private final CashStack exercisePrice;

    @NonNull
    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @JsonProperty
    private final AssetType type = AssetType.OPTION;

    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
        return type;
    }

    @NonNull
    @Override
    @JsonIgnore
    public AssetStack getDelivery() {
        return delivery;
    }

    @NonNull
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

    @NonNull
    @JsonIgnore
    public OptionType getOptionType() {
        return optionType;
    }

    @JsonIgnore
    public boolean isCall() {
        return getOptionType().isCall();
    }

    @JsonIgnore
    public boolean isAmerican() {
        return getOptionType().isAmerican();
    }

    @NonNull
    @JsonIgnore
    public CashStack getExercisePrice() {
        return exercisePrice;
    }

    /**
     * Checks if option is exercisable
     * @return Whether it is exercisable or not
     * @throws IllegalArgumentException When exercise price is in a different denotation than the trading price
     */
    @JsonIgnore
    public boolean isExercisable() throws IllegalArgumentException {
        CashStack e = exercisePrice;
        CashStack p = market.getPrice();

        final boolean outOfTheMoney = isCall() ? e.isSmallerThan(p) : e.isGreaterThan(p);
        final boolean exercisableNow = isAmerican() || getExpiry().isBeforeNow();

        return !outOfTheMoney && exercisableNow;
    }

    @Override
    @JsonIgnore
    public @NonNull Option copy() {
        return new Option(this);
    }

    /**
     * Called when this option is exercised.
     * Does NOT check is this option is exercisable.
     * @param holder Holder of this contract
     */
    @JsonIgnore
    public void onExercised(Actor holder) {
        Sponge.eventManager().post(new PaymentEvent(
                holder,
                counterparty,
                getExercisePrice(),
                null, // TODO
                false
        ));

        Sponge.eventManager().post(new PaymentEvent(
                counterparty,
                holder,
                getDelivery(),
                null, // TODO
                false
        ));
    }

    @Override
    @JsonIgnore
    public void onExpired(Actor holder) {
        if (isExercisable()) onExercised(holder);
    }
}