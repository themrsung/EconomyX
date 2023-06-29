package oasis.economyx.interfaces.actor.types.trading;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.interfaces.trading.market.Marketplace;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * A market host can open markets and handle orders
 */
public interface MarketHost extends Actor {
    /**
     * Gets all open markets
     * @return A copied list of markets
     */
    List<Marketplace> getMarkets();

    /**
     * Gets a market by asset being traded (only one market per asset is allowed)
     * @param asset Asset to query for
     * @return Market if found, null if not
     */
    @Nullable
    default Marketplace getMarket(Asset asset) {
        for (Marketplace m : getMarkets()) {
            if (m.getAsset().getAsset().equals(asset)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Opens a market to the public
     * @param market Market to open
     * @throws IllegalArgumentException When a market with the same asset is already open
     */
    void listMarket(@NonNull Marketplace market) throws IllegalArgumentException;

    /**
     * Closes a market safely
     * @param market Market to close
     */
    void delistMarket(@NonNull Marketplace market);

    /**
     * Gets the fee rate of this market host
     * @return Fee rate (e.g. 1.2% -> 0.012f)
     */
    @NonNegative
    float getMarketFeeRate();

    /**
     * Sets the fee rate of this market host
     * @param rate Fee rate (e.g. 1.2% -> 0.012f)
     */
    void setMarketFeeRate(@NonNegative float rate);
}
