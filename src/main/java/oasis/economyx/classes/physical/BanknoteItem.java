package oasis.economyx.classes.physical;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.physical.Banknote;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * An instantiable class of Banknote
 */
public class BanknoteItem implements Banknote {
    public BanknoteItem(@NonNull UUID uniqueId, @NonNull CashStack denotation) {
        this.uniqueId = uniqueId;
        this.denotation = denotation;
    }

    public BanknoteItem() {
        this.uniqueId = null;
        this.denotation = null;
    }

    public BanknoteItem(BanknoteItem other) {
        this.uniqueId = other.uniqueId;
        this.denotation = other.denotation;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    private final CashStack denotation;

    @NotNull
    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @NotNull
    @Override
    @JsonIgnore
    public CashStack getDenotation() {
        return denotation;
    }
}
