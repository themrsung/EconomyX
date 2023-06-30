package oasis.economyx.interfaces.physical;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.classes.physical.BanknoteItem;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

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
    ItemType NOTE_ITEM = ItemTypes.PAPER.get();

    /**
     * Gets the unique ID of this banknote
     * @return Unique ID
     */
    @NonNull
    UUID getUniqueId();

    /**
     * Gets the amount of cash this banknote represents
     * @return Denotation
     */
    @NonNull
    CashStack getDenotation();
}
