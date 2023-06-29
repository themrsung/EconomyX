package oasis.economyx.interfaces.actor.types.finance;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.types.institutional.InterestRateProvider;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.banking.Account;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A banker holds accounts on their clients' behalf
 * Accounts can contain any asset
 * Multiple accounts can be created for each client
 */
public interface Banker extends Corporation, InterestRateProvider, CardIssuer {
    /**
     * Gets every account held by this banker
     * @return Copied list of accounts
     */
    List<Account> getAccounts();

    /**
     * Gets an account by unique ID
     * @param uniqueId Unique ID of the account
     * @return Account if found, null if not
     */
    @Nullable
    default Account getAccount(UUID uniqueId) {
        for (Account a : getAccounts()) {
            if (a.getUniqueId().equals(uniqueId)) return a;
        }

        return null;
    }

    /**
     * Gets every account matching the given client
     * @param client Client to query for
     * @return List of found accounts
     */
    default List<Account> getAccount(Actor client) {
        List<Account> results = new ArrayList<>();

        for (Account a : getAccounts()) {
            if (a.getClient().equals(client)) results.add(a);
        }

        return results;
    }

    /**
     * Adds an account to this banker
     * @param account Account to add
     */
    void addAccount(Account account);

    /**
     * Remvoes an account from this banker
     * @param account Account to remove
     */
    void removeAccount(Account account);

    @Override
    @NonNegative
    float getInterestRate();

    /**
     * Commercial banks cannot have negative interest rates
     * @param rate Annual interest rate
     * @throws IllegalArgumentException When given rate is negative
     */
    @Override
    void setInterestRate(@NonNegative float rate) throws IllegalArgumentException;

    /**
     * Called every hour
     * Interest rate is converted to hourly rate so that the compounded interest over a year will equal the annual rate
     */
    default void payInterest() {
        for (Account a : getAccounts()) {
            AssetStack content = a.getContent();
            if (content instanceof CashStack c) try {
                CashStack i = c.multiply(getDailyInterestRate());
                Sponge.eventManager().post(new PaymentEvent(
                        this,
                        a.getClient(),
                        i,
                        null, // TODO
                        true
                ));

            } catch (IllegalArgumentException e) {
                // Account has foreign currency in it content
            }
        }
    }
}
