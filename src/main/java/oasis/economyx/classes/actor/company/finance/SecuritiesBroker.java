package oasis.economyx.classes.actor.company.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Brokerage;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SecuritiesBroker extends Company implements Brokerage {
    /**
     * Creates a new securities broker
     *
     * @param uniqueId   Unique ID of this broker
     * @param name       Name of this broker (not unique)
     * @param stockId    ID of this broker's stock
     * @param shareCount Initial share count
     * @param currency   Currency to use
     */
    public SecuritiesBroker(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.brokerageFeeRate = 0f;
        this.accounts = new ArrayList<>();
    }

    public SecuritiesBroker() {
        super();

        this.brokerageFeeRate = 0f;
        this.accounts = new ArrayList<>();
    }

    public SecuritiesBroker(SecuritiesBroker other) {
        super(other);

        this.accounts = other.accounts;
        this.brokerageFeeRate = other.brokerageFeeRate;
    }

    @NonNull
    @JsonProperty
    private final List<Account> accounts;

    /**
     * Brokerages do not pay interest
     * Do NOT inline this to getInterestRate()
     */
    @JsonProperty
    private final float interestRate = 0f;

    @Override
    @JsonIgnore
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public void addAccount(Account account) {
        this.accounts.add(account);
        account.onOpened(this);
    }

    @Override
    public void removeAccount(Account account) {
        if (accounts.remove(account)) account.onClosed(this);
    }

    @Override
    public float getInterestRate() {
        return interestRate;
    }

    @Override
    public void setInterestRate(float rate) {
        // Do nothing
    }

    @NonNegative
    @JsonProperty
    private float brokerageFeeRate;

    @Override
    @JsonIgnore
    public float getBrokerageFeeRate() {
        return brokerageFeeRate;
    }

    @Override
    @JsonIgnore
    public void setBrokerageFeeRate(float brokerageFeeRate) {
        this.brokerageFeeRate = brokerageFeeRate;
    }

    @JsonProperty
    private final Type type = Type.SECURITIES_BROKER;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
