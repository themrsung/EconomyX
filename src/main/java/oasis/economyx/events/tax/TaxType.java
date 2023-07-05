package oasis.economyx.events.tax;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.List;

public enum TaxType {
    INCOME_TAX,
    INTEREST_TAX,
    DIVIDEND_TAX;

    private static final List<String> K_INCOME_TAX = Arrays.asList("incometax", "소득세");
    private static final List<String> K_INTEREST_TAX = Arrays.asList("interesttax", "이자소득세");
    private static final List<String> K_DIVIDEND_TAX = Arrays.asList("dividendtax", "배당소득세");

    @Nullable
    public static TaxType fromInput(@NonNull String input) {
        if (K_INCOME_TAX.contains(input.toLowerCase())) return INCOME_TAX;
        if (K_INTEREST_TAX.contains(input.toLowerCase())) return INTEREST_TAX;
        if (K_DIVIDEND_TAX.contains(input.toLowerCase())) return DIVIDEND_TAX;
        return null;
    }

    @NonNull
    public List<String> toInput() {
        return switch (this) {
            case INCOME_TAX -> K_INCOME_TAX;
            case INTEREST_TAX -> K_INTEREST_TAX;
            case DIVIDEND_TAX -> K_DIVIDEND_TAX;
        };
    }
}
