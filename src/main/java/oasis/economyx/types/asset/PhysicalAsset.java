package oasis.economyx.types.asset;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a physically issued asset.
 * Assets can be withdrawn from an actor's account to a physical item for safekeeping.
 */
@JsonSerialize(as = PhysicalAsset.PhysicalAssetItem.class)
@JsonDeserialize(as = PhysicalAsset.PhysicalAssetItem.class)
public interface PhysicalAsset {
    /**
     * Do not change after deployment.
     */
    @JsonIgnore
    Material ASSET_ITEM = Material.PAPER;

    /**
     * Gets a new physical asset instance
     *
     * @param asset Asset to physicalize
     * @return Phsycialized asset
     */
    @JsonIgnore
    static PhysicalAsset physicalizeAsset(@NonNull AssetStack asset) {
        return new PhysicalAssetItem(UUID.randomUUID(), asset);
    }

    /**
     * Gets the unique ID of this physically issued asset.
     *
     * @return Unique ID
     */
    @NonNull
    @JsonIgnore
    UUID getUniqueId();

    /**
     * Gets the asset this represents.
     *
     * @return Asset
     */
    @NonNull
    @JsonIgnore
    AssetStack getAsset();

    /**
     * Gets the item to give to an in-game player.
     *
     * @return Physical item
     */
    @JsonIgnore
    @NonNull
    ItemStack getPhysicalItem();

    record PhysicalAssetItem(
            @NonNull @JsonProperty UUID uniqueId,
            @NonNull @JsonProperty @JsonIdentityReference AssetStack asset
    ) implements PhysicalAsset {
        @Override
        @JsonIgnore
        public @NonNull UUID getUniqueId() {
            return uniqueId;
        }

        @Override
        @JsonIgnore
        public @NonNull AssetStack getAsset() {
            return asset;
        }

        @Override
        @JsonIgnore
        public @NonNull ItemStack getPhysicalItem() {
            ItemStack stack = new ItemStack(PhysicalAsset.ASSET_ITEM, 1);

            ItemMeta meta = stack.getItemMeta();
            if (meta == null) throw new RuntimeException();

            meta.setDisplayName(getAsset().getAsset().getName() + " " + NumberFormat.getIntegerInstance().format(getAsset().getQuantity()));

            List<String> lore = new ArrayList<>();
            lore.add(getUniqueId().toString());
            meta.setLore(lore);

            meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true);

            stack.setItemMeta(meta);

            return stack;
        }
    }
}
