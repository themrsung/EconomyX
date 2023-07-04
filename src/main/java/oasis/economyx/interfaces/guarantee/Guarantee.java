package oasis.economyx.interfaces.guarantee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Credible;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

/**
 * A guarantee is issued by a credible actor
 * <p>
 * Guarantees have a limit and can have or not have an expiration.
 * Properties cannot be changed after creation.
 * A collateral is not registered for a guarantee; It is backed by the guarantor's credit.
 * </p>
 *
 * </p>
 * The warrantee can only use a guarantee to prevent insolvency.
 * Initiating payment using a guarantee is not supported.
 * </p>
 */
public interface Guarantee extends References {
    /**
     * Gets the guarantor of this guarantee.
     *
     * @return Guarantor who issued this guarantee
     */
    @NonNull
    @JsonIgnore
    Credible getGuarantor();

    /**
     * Gets the warrantee of this guarantee.
     *
     * @return Warrantee who received this guarantee
     */
    @NonNull
    @JsonIgnore
    Actor getWarrantee();

    /**
     * The expiration date of this guarantee, if there is one.
     *
     * @return Expiry
     */
    @Nullable
    @JsonIgnore
    DateTime getExpiry();

    /**
     * The limit of this guarantee.
     * This is also used to determine which asset to guarantee payment of: It cannot be null.
     *
     * @return Limit
     */
    @NonNull
    @JsonIgnore
    AssetStack getLimit();

    /**
     * Uses this guarantee.
     *
     * @param amount Amount used
     * @throws IllegalArgumentException When amount is over limit
     */
    @JsonIgnore
    void onUsed(@NonNull AssetStack amount) throws IllegalArgumentException;
}
