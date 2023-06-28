package oasis.economyx.asset.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

/**
 * A contract denotes the mandatory/conditional delivery of an asset on expiry
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "contractType"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Forward.class, name = "forward"),
        @JsonSubTypes.Type(value = Note.class, name = "note"),
        @JsonSubTypes.Type(value = Option.class, name = "option"),
        @JsonSubTypes.Type(value = Swap.class, name = "swap"),
        @JsonSubTypes.Type(value = Collateral.class, name = "collateral")
})

public interface Contract extends Asset {
    /**
     * The asset to be delivered upon expiry
     * @return Asset to be delivered
     */
    @NonNull
    @JsonProperty("delivery")
    AssetStack getDelivery();

    /**
     * Counterparty of this contract
     * @return Counterparty
     */
    @NonNull
    @JsonIgnore
    Actor getCounterparty();

    /**
     * Expiration date of this contract
     * @return Expiration
     */
    @Nullable
    @JsonProperty("expiry")
    DateTime getExpiry();
}
