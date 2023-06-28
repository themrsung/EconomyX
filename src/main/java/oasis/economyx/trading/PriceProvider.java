package oasis.economyx.trading;

import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.cash.CashStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides fair value of an asset
 * Price is used for exercising options and settling swaps
 */
public interface PriceProvider {
    /**
     * The asset of which price is provided for
     * Contract size is determined by quantity of the unit asset
     * @return Unit asset
     */
    @NonNull
    AssetStack getAsset();

    /**
     * Shortcut getter to contract size
     * @return Contract size
     */
    @NonNegative
    default long getContractSize() {
        return getAsset().getQuantity();
    }

    /**
     * The current fair value of one unit asset
     * @return Price
     */
    @NonNull
    CashStack getPrice();

    /**
     * The cumulative trading volume of this trading day
     * @return Volume
     */
    @NonNegative
    long getVolume();
}
