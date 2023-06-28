package oasis.economyx.asset.contract.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.types.Counterparty;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetType;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.asset.contract.Contract;
import oasis.economyx.trading.PriceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

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

    public Option(@NonNull UUID uniqueId, @NonNull Counterparty counterparty, @NonNull AssetStack delivery, @NonNull DateTime expiry, @NonNull PriceProvider market, @NonNull OptionType optionType, @NonNull CashStack exercisePrice) {
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
    private final UUID uniqueId;
    @NonNull
    private final Counterparty counterparty;

    @NonNull
    private final AssetStack delivery;

    @NonNull
    private final DateTime expiry;

    @NonNull
    @JsonProperty("market")
    private final PriceProvider market;

    @NonNull
    @JsonProperty("optionType")
    private final OptionType optionType;

    @NonNull
    @JsonProperty("exercisePrice")
    private final CashStack exercisePrice;

    @NonNull
    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public @NonNull AssetType getType() {
        return AssetType.OPTION;
    }

    @NonNull
    @Override
    public AssetStack getDelivery() {
        return delivery;
    }

    @NonNull
    @Override
    public DateTime getExpiry() {
        return expiry;
    }

    @NonNull
    @Override
    public Counterparty getCounterparty() {
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
     * @throws IllegalArgumentException When exercise price is in a different denotation than the market price
     */
    @JsonIgnore
    public boolean isExercisable() throws IllegalArgumentException {
        CashStack e = exercisePrice;
        CashStack p = market.getPrice();

        final boolean outOfTheMoney = isCall() ? e.isSmallerThan(p) : e.isGreaterThan(p);
        final boolean exercisableNow = isAmerican() || getExpiry().isBeforeNow();

        return !outOfTheMoney && exercisableNow;
    }
}
