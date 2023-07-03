package oasis.economyx.interfaces.actor.types.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.guarantee.Guarantee;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * A credible actor can issue guarantees
 * <p>
 * Guarantees function as a line of credit,
 * but is applicable to any asset classification.
 * </p>
 */
public interface Credible {
    /**
     * Gets all guarantees issued by this credible
     *
     * @return Copied list of guarantees
     */
    @NonNull
    @JsonIgnore
    List<Guarantee> getGuarantees();

    /**
     * Adds a guarantee
     *
     * @param guarantee Guarantee to add
     */
    @JsonIgnore
    void addGuarantee(@NonNull Guarantee guarantee);

    /**
     * Revokes a guarantee
     *
     * @param guarantee Guarantee to revoke
     */
    @JsonIgnore
    void removeGuarantee(@NonNull Guarantee guarantee);
}
