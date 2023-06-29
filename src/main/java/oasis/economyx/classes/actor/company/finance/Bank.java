package oasis.economyx.classes.actor.company.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.actor.types.finance.Banker;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.banking.Account;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Bank extends Company implements Banker {
    /**
     * Creates a new bank
     * @param uniqueId Unique ID of this bank
     * @param name Name of this bank (not unique)
     * @param stockId ID of this bank's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public Bank(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.accounts = new ArrayList<>();
    }

    public Bank() {
        super();

        this.accounts = new ArrayList<>();
    }

    public Bank(Bank other) {
        super(other);
        this.accounts = other.accounts;
    }

    private final List<Account> accounts;

    @Override
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
        if (this.accounts.remove(account)) account.onClosed(this);
    }

    @JsonProperty
    private final ActorType type = ActorType.BANK;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
