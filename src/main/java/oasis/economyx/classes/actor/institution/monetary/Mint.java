package oasis.economyx.classes.actor.institution.monetary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.institution.Institution;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.BanknoteIssuer;
import oasis.economyx.interfaces.actor.types.institutional.CurrencyIssuer;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.interfaces.physical.Banknote;
import oasis.economyx.types.asset.cash.Cash;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A sovereign can have multiple mints
 */
public final class Mint extends Institution implements BanknoteIssuer {
    /**
     * Creates a new mint
     *
     * @param parent   The sovereign founding this mint
     * @param uniqueId Unique ID of this mint
     * @param name     Name of this mint (not unique)
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

    @JsonProperty
    private final List<Banknote> issuedBanknotes;

    @Override
    @JsonIgnore
    public List<Banknote> getIssuedBanknotes() {
        return new ArrayList<>(issuedBanknotes);
    }

    @Override
    @JsonIgnore
    public ItemStack issueBanknote(Banknote note) throws IllegalArgumentException {
        Sovereign parent = getParent();
        for (Institutional i : parent.getInstitutions()) {
            if (i instanceof CurrencyIssuer ci) {
                if (!ci.getIssuedCurrency().equals(note.getDenotation().getAsset()))
                    throw new IllegalArgumentException();

                ItemStack banknote = new ItemStack(Banknote.NOTE_ITEM);

                ItemMeta meta = banknote.getItemMeta();
                if (meta == null) throw new RuntimeException();

                meta.setDisplayName("Banknote");

                List<String> lore = new ArrayList<>();
                lore.add(note.getUniqueId().toString());

                meta.setLore(lore);

                meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);

                banknote.setItemMeta(meta);

                return banknote;
            }
        }

        throw new IllegalArgumentException();
    }

    @JsonProperty
    private final Type type = Type.MINT;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
