package oasis.economyx.interfaces.banking;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.classes.banking.AssetAccount;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * An account is owned by an actor and held by a bank/broker
 * Accounts can hold any asset
 * <p>
 * Assets held in an account are stored in the institution's asset portfolio.
 * A collateral with the debtor as the institution is issued per account, and is updated evey deposit/withdrawal.
 * </p>
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonSerialize(as = AssetAccount.class)
@JsonDeserialize(as = AssetAccount.class)
public interface Account {
    /**
     * Gets the unique ID of this account
     *
     * @return Unique ID
     */
    @NonNull
    UUID getUniqueId();

    /**
     * Gets the institution holding this account
     *
     * @return Banker
     */
    @NonNull
    Banker getInstitution();

    /**
     * Gets the client of this account
     * The client holds the collateral which marks the ownership of this account
     *
     * @return Client
     */
    @NonNull
    Actor getClient();

    /**
     * Gets the asset stored in this account
     *
     * @return Asset stored in this account
     */
    @NonNull
    AssetStack getContent();

    /**
     * Gets the collateral which marks the ownership of this account
     *
     * @return Collateral
     */
    @NonNull
    CollateralStack getCollateral();

    /**
     * Handles deposits to this account
     * Also updates collateral
     *
     * @param asset Asset to be added to this account
     * @throws IllegalArgumentException When an incompatible is given
     */
    void deposit(@NonNull AssetStack asset) throws IllegalArgumentException;

    /**
     * Handles withdrawals to this account
     * Also updates collateral
     *
     * @param asset Asset to be removed from this account
     * @throws IllegalArgumentException When the account does not hold the asset
     */
    void withdraw(@NonNull AssetStack asset) throws IllegalArgumentException;

    /**
     * Called when account is opened
     * Registers collateral
     *
     * @param institution Institution holding this account
     */
    void onOpened(@NonNull Banker institution);

    /**
     * Called when account is closed
     * Withdraws content and unregisters collateral
     *
     * @param institution Institution holding this account
     */
    void onClosed(@NonNull Banker institution);
}
