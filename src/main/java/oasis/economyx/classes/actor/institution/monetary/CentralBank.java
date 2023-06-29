package oasis.economyx.classes.actor.institution.monetary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.CurrencyIssuer;
import oasis.economyx.interfaces.actor.types.institutional.InterestRateProvider;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.classes.actor.institution.Institution;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A sovereign can only have one central bank
 */
public final class CentralBank extends Institution implements InterestRateProvider, CurrencyIssuer {
    /**
     * Creates a new central bank, and issues a new currency
     * @param parent The sovereign founding this central bank
     * @param uniqueId Unique ID of this central bank
     * @param name Mame of this central bank (not unique)
     * @param currency Currency to issue (cannot be null)
     */
    public CentralBank(@NonNull Sovereign parent, @NonNull UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);

        this.issuedCurrency = currency;
        this.interestRate = 0f;
    }

    public CentralBank() {
        super();

        this.issuedCurrency = null;
        this.interestRate = 0f;
    }

    public CentralBank(CentralBank other) {
        super(other);
        this.issuedCurrency = other.issuedCurrency;
        this.interestRate = other.interestRate;
    }

    @NonNull
    private final Cash issuedCurrency;

    private float interestRate;

    @NotNull
    @Override
    public Cash getIssuedCurrency() {
        return new Cash(issuedCurrency);
    }

    @Override
    public void printCurrency(CashStack amount) throws IllegalArgumentException {
        if (!amount.getAsset().equals(getIssuedCurrency())) throw new IllegalArgumentException();

        getAssets().add(amount);
    }

    @Override
    public void burnCurrency(CashStack amount) throws IllegalArgumentException {
        if (!amount.getAsset().equals(getIssuedCurrency())) throw new IllegalArgumentException();

        getAssets().remove(amount); // This can also throw an IllegalArgumentException
    }

    @Override
    public float getInterestRate() {
        return interestRate;
    }

    @Override
    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @JsonProperty
    private final ActorType type = ActorType.CENTRAL_BANK;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
