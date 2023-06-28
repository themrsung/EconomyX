package oasis.economyx.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

/**
 * A type of asset that can be purchased with cash
 */
public interface Purchasable {
    @JsonIgnore
    @Nullable
    CashStack getPurchasePrice();

    @JsonIgnore
    @Nullable
    DateTime getPurchaseDate();

    @JsonIgnore
    void setPurchasePrice(@Nullable CashStack price);

    @JsonIgnore
    void setPurchaseDate(@Nullable DateTime date);
}
