package oasis.economyx.interfaces.physical;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.classes.physical.BanknoteItem;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A physical item denoting a certain amount of currency
 * It will be assumed that banknotes hold its untouched UUID string as the first item in its lore
 */

@JsonSerialize(as = BanknoteItem.class)
@JsonDeserialize(as = BanknoteItem.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)

public interface Banknote {
    /**
     * Do not change this after deployment
     */
    @JsonIgnore
    Material NOTE_ITEM = Material.PAPER;

    /**
     * Gets the unique ID of this banknote
     *
     * @return Unique ID
     */
    @NonNull
    @JsonIgnore
    UUID getUniqueId();

    /**
     * Gets the amount of cash this banknote represents
     *
     * @return Denotation
     */
    @NonNull
    @JsonIgnore
    CashStack getDenotation();
}
