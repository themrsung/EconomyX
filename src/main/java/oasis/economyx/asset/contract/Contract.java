package oasis.economyx.asset.contract;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.contract.collateral.Collateral;
import oasis.economyx.asset.contract.forward.Forward;
import oasis.economyx.asset.contract.note.Note;
import oasis.economyx.asset.contract.option.Option;
import oasis.economyx.asset.contract.swap.Swap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

/**
 * A contract denotes the mandatory/conditional delivery of an asset on expiry
 */

public interface Contract extends Asset {
    /**
     * The asset to be delivered upon expiry
     * @return Asset to be delivered
     */
    @NonNull
    AssetStack getDelivery();

    /**
     * Counterparty of this contract
     * @return Counterparty
     */
    @NonNull
    Actor getCounterparty();

    /**
     * Expiration date of this contract
     * @return Expiration
     */
    @Nullable
    DateTime getExpiry();
}
