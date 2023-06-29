package oasis.economyx.classes.actor.institution.monetary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.BanknoteIssuer;
import oasis.economyx.interfaces.actor.types.institutional.CurrencyIssuer;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.institution.Institution;
import oasis.economyx.interfaces.physical.Banknote;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A sovereign can have multiple mints
 */
public final class Mint extends Institution implements BanknoteIssuer {
    /**
     * Creates a new mint
     * @param parent The sovereign founding this mint
     * @param uniqueId Unique ID of this mint
     * @param name Name of this mint (not unique)
     * @param currency Currency of this mint
     */
    public Mint(@NonNull Sovereign parent, @NonNull UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);

        this.issuedBanknotes = new ArrayList<>();
    }

    public Mint() {
        super();

        this.issuedBanknotes = new ArrayList<>();
    }

    public Mint(Mint other) {
        super(other);
        this.issuedBanknotes = other.issuedBanknotes;
    }

    private final List<Banknote> issuedBanknotes;

    @Override
    public List<Banknote> getIssuedBanknotes() {
        return new ArrayList<>(issuedBanknotes);
    }

    @Override
    public ItemStack issueBanknote(Banknote note) throws IllegalArgumentException {
        Sovereign parent = getParent();
        for (Institutional i : parent.getInstitutions()) {
            if (i instanceof CurrencyIssuer ci) {
                if (!ci.getIssuedCurrency().equals(note.getDenotation().getAsset())) throw new IllegalArgumentException();

                ItemStack banknote = ItemStack.builder().itemType(Banknote.NOTE_ITEM).build();

                // TODO add enchantments

                return banknote;
            }
        }

        throw new IllegalArgumentException();
    }

    @JsonProperty
    private final ActorType type = ActorType.MINT;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
