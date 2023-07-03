package oasis.economyx.interfaces.actor.types.institutional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A currency issuer can issue currencies
 * Currency issuers must issue one currency
 */
public interface CurrencyIssuer extends Institutional {
    /**
     * Gets the currency this issuer has issued
     *
     * @return Currency
     */
    @NonNull
    @JsonIgnore
    Cash getIssuedCurrency();

    /**
     * Adds money to own account
     * This is automatically called when a currency issuer tries to pay more of its currency than it has
     *
     * @param amount Amount to print
     * @throws IllegalArgumentException When a different denotation is provided
     */
    @JsonIgnore
    void printCurrency(CashStack amount) throws IllegalArgumentException;

    /**
     * Burns money from own account
     *
     * @param amount Amount to burn
     * @throws IllegalArgumentException When a different denotation is provided
     */
    @JsonIgnore
    void burnCurrency(CashStack amount) throws IllegalArgumentException;
}
