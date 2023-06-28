package oasis.economyx.actor;

import com.fasterxml.jackson.annotation.*;
import oasis.economyx.classes.actor.Company;
import oasis.economyx.classes.actor.NaturalPerson;
import oasis.economyx.classes.actor.Sovereignty;
import oasis.economyx.classes.actor.Trust;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Company.class, name = "COMPANY"),
        @JsonSubTypes.Type(value = NaturalPerson.class, name = "NATURAL_PERSON"),
        @JsonSubTypes.Type(value = Sovereignty.class, name = "SOVEREIGNTY"),
        @JsonSubTypes.Type(value = Trust.class, name = "TRUST"),
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
    String getName();

    /**
     * Sets the semantic name of this actor
     * @param name Can be null and does not require uniqueness
     */
    void setName(@Nullable String name);

    /**
     * Gets the gross holdings of this actor
     * @return Assets
     */
    Portfolio getAssets();

    /**
     * Gets all outstanding collaterals of this actor
     * @param state Current running state
     * @return Collaterals
     */
    Portfolio getOutstandingCollateral(EconomyState state);

    /**
     * Gets all outstanding liabilities of this actor (is inclusive of collaterals)
     * @param state Current running state
     * @return Liabilities
     */
    Portfolio getLiabilities(EconomyState state);

    /**
     * Gets all payable assets
     * @param state Current running state
     * @return Gross assets - Outstanding collateral
     */
    Portfolio getPayableAssets(EconomyState state);

    /**
     * Gets the net assets of this actor (Assets subtracted by liabilities)
     * Use this portfolio to check for solvency
     * @param state Current running state
     * @return Net assets
     */
    Portfolio getNetAssets(EconomyState state);

    /**
     * Gets the type of this actor
     * @return Type
     */
    @JsonProperty("type")
    ActorType getType();
}
