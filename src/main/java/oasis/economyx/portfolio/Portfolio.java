package oasis.economyx.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * A list of assets
 * Ensures that only one instance of each asset type is stored along with their quantity
 */
@JsonSerialize(as = AssetPortfolio.class)
@JsonDeserialize(as = AssetPortfolio.class)

public interface Portfolio {
    /**
     * Gets every asset in this portfolio
     * @return A copied list of every asset
     */
    List<AssetStack> get();

    /**
     * Gets an asset by index
     * @param i Index
     * @return Asset if found, null if not found
     */
    @Nullable
    AssetStack get(int i);

    /**
     * Gets an asset by its type
     * @param asset Type
     * @return Asset if found, null if not found
     */
    @Nullable
    AssetStack get(Asset asset);

    /**
     * Gets the size of this portfolio
     * @return How many assets there are
     */
    int size();

    /**
     * Adds an asset to this portfolio
     * @param asset Asset to add
     */
    void add(@NonNull AssetStack asset);

    /**
     * Adds a portfolio to this portfolio
     * @param portfolio Portfolio to add
     */
    void add(@NonNull Portfolio portfolio);

    /**
     * Removes an asset from this portfolio
     * @param asset Asset to remove
     * @throws IllegalArgumentException When the resulting asset's quantity is negative
     */
    void remove(@NonNull AssetStack asset) throws IllegalArgumentException;

    /**
     * Removes a portfolio from this portfolio
     * @param portfolio Portfolio to remove
     * @throws IllegalArgumentException When the resulting portfolio has at least one negative quantity
     */
    void remove(@NonNull Portfolio portfolio) throws IllegalArgumentException;

    /**
     * Checks whether this portfolio contains an asset
     * Will check type and quantity
     * @param asset Asset to query
     * @return Whether this portfolio contains the asset
     */
    boolean contains(@NonNull AssetStack asset);

    /**
     * Checks whether this portfolio has at least one of an asset
     * Will only check type
     * @param asset Asset to query
     * @return Whether this portfolio has the asset
     */
    boolean has(@NonNull Asset asset);

    /**
     * Checks whether this portfolio has at least one of an asset
     * @param asset Asset to query
     * @param checkMeta Whether to check for matching metadata
     * @return Whether this portfolio has the asset
     */
    boolean has(@NonNull AssetStack asset, boolean checkMeta);
}
