package oasis.economyx.types.asset.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

/**
 * A type of asset that can be purchased with cash
 */
public interface Purchasable {
    @JsonProperty("purchasePrice")
    @Nullable
    CashStack getPurchasePrice();

    @JsonProperty("purchaseDate")
    @Nullable
    DateTime getPurchaseDate();

    @JsonIgnore
    void setPurchasePrice(@Nullable CashStack price);

    @JsonIgnore
    void setPurchaseDate(@Nullable DateTime date);
}
