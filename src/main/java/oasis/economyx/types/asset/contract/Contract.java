package oasis.economyx.types.asset.contract;

import com.google.errorprone.annotations.DoNotCall;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

/**
 * A contract denotes the mandatory/conditional delivery of an asset on expiry
 */

public interface Contract extends Asset, References {
    /**
     * The asset to be delivered upon expiry
     *
     * @return Asset to be delivered
     */
    @NonNull
    AssetStack getDelivery();

    /**
     * Counterparty of this contract
     *
     * @return Counterparty
     */
    @NonNull
    Actor getCounterparty();

    /**
     * Expiration date of this contract
     *
     * @return Expiration
     */
    @Nullable
    DateTime getExpiry();

    /**
     * Called when this contract has expired
     *
     * @param holder Current holder of the contract
     */
    void onExpired(Actor holder);

    @Override
    default @NonNull String getName() {
        return getType().toString();
    }

    @Override
    @DoNotCall
    default void setName(@NonNull String name) {

    }

    @Override
    void initialize(@NonNull EconomyState state);
}
