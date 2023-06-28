package oasis.economyx.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.classes.EconomicActor;
import oasis.economyx.portfolio.Portfolio;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * An actor is capable of holding assets and executing economic actions(e.g. placing orders or bids)
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
//        @JsonSubTypes.Type(value = EconomicActor.class, name = "economic_actor")
})

public interface Actor {
    /**
     * Gets the unique ID of this actor
     * @return Unique ID
     */
    @JsonProperty("uniqueId")
    UUID getUniqueId();

    /**
     * Gets the semantic name of this actor
     * Can be null and is not unique
     * @return Name
     */
    @Nullable
    @JsonProperty("name")
    String getName();

    /**
     * Sets the semantic name of this actor
     * @param name Can be null and does not require uniqueness
     */
    @JsonIgnore
    void setName(@Nullable String name);

    /**
     * Gets the gross holdings of this actor
     * @return Assets
     */
    @JsonProperty("assets")
    Portfolio getAssets();

    /**
     * Gets all outstanding collaterals of this actor
     * @param state Current running state
     * @return Collaterals
     */
    @JsonIgnore
    Portfolio getOutstandingCollateral(EconomyState state);

    /**
     * Gets all outstanding liabilities of this actor (is inclusive of collaterals)
     * @param state Current running state
     * @return Liabilities
     */
    @JsonIgnore
    Portfolio getLiabilities(EconomyState state);

    /**
     * Gets all payable assets
     * @param state Current running state
     * @return Gross assets - Outstanding collateral
     */
    @JsonIgnore
    Portfolio getPayableAssets(EconomyState state);

    /**
     * Gets the net assets of this actor (Assets subtracted by liabilities)
     * Use this portfolio to check for solvency
     * @param state Current running state
     * @return Net assets
     */
    @JsonIgnore
    Portfolio getNetAssets(EconomyState state);
}
