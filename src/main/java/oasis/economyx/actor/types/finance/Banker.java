package oasis.economyx.actor.types.finance;

import oasis.economyx.actor.Actor;
import oasis.economyx.interfaces.banking.Account;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A banker holds accounts on their clients' behalf
 * Accounts can contain any asset
 * Multiple accounts can be created for each client
 */
public interface Banker {
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
}
