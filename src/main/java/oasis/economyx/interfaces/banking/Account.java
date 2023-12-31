package oasis.economyx.interfaces.banking;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.classes.banking.AssetAccount;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.reference.References;
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
public interface Account extends References {
    /**
     * Gets the unique ID of this account
     *
     * @return Unique ID
     */
    @NonNull
    @JsonIgnore
    UUID getUniqueId();

    /**
     * Gets the institution holding this account
     *
     * @return Banker
     */
    @NonNull
    @JsonIgnore
    Banker getInstitution();

    /**
     * Gets the client of this account
     * The client holds the collateral which marks the ownership of this account
     *
     * @return Client
     */
    @NonNull
    @JsonIgnore
    Actor getClient();

    /**
     * Gets the asset stored in this account
     *
     * @return Asset stored in this account
     */
    @NonNull
    @JsonIgnore
    AssetStack getContent();

    /**
     * Gets the collateral which marks the ownership of this account
     *
     * @return Collateral
     */
    @NonNull
    @JsonIgnore
    CollateralStack getCollateral();

    /**
     * Handles deposits to this account
     * Also updates collateral
     *
     * @param asset Asset to be added to this account
     * @throws IllegalArgumentException When an incompatible is given
     */
    @JsonIgnore
    void deposit(@NonNull AssetStack asset) throws IllegalArgumentException;

    /**
     * Handles withdrawals to this account
     * Also updates collateral
     *
     * @param asset Asset to be removed from this account
     * @throws IllegalArgumentException When the account does not hold the asset
     */
    @JsonIgnore
    void withdraw(@NonNull AssetStack asset) throws IllegalArgumentException;

    /**
     * Called when account is opened
     * Registers collateral
     *
     * @param institution Institution holding this account
     */
    @JsonIgnore
    void onOpened(@NonNull Banker institution);

    /**
     * Called when account is closed
     * Withdraws content and unregisters collateral
     *
     * @param institution Institution holding this account
     */
    @JsonIgnore
    void onClosed(@NonNull Banker institution);
}
