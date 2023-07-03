package oasis.economyx.interfaces.actor.types.trading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.types.asset.Asset;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * A market host can open markets and handle orders
 */
public interface Exchange extends Actor {
    /**
     * Gets all open markets
     *
     * @return A copied list of markets
     */
    @JsonIgnore
    List<Marketplace> getMarkets();

    /**
     * Gets a market by asset being traded (only one market per asset is allowed)
     *
     * @param asset Asset to query for
     * @return Market if found, null if not
     */
    @Nullable
    @JsonIgnore
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
     *
     * @param market Market to open
     * @throws IllegalArgumentException When a market with the same asset is already open
     */
    @JsonIgnore
    void listMarket(@NonNull Marketplace market) throws IllegalArgumentException;

    /**
     * Closes a market safely
     *
     * @param market Market to close
     */
    @JsonIgnore
    void delistMarket(@NonNull Marketplace market);

    /**
     * Gets the fee rate of this market host
     *
     * @return Fee rate (e.g. 1.2% -> 0.012f)
     */
    @NonNegative
    @JsonIgnore
    float getMarketFeeRate();

    /**
     * Sets the fee rate of this market host
     *
     * @param rate Fee rate (e.g. 1.2% -> 0.012f)
     */
    @JsonIgnore
    void setMarketFeeRate(@NonNegative float rate);
}
