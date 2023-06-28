package oasis.economyx.asset.cash;

import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Cash is a fungible, stackable asset
 */
public final class Cash implements Asset {
    public Cash() {
        this.uniqueId = UUID.randomUUID();
    }

    public Cash(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Cash(Cash other) {
        this.uniqueId = other.uniqueId;
    }

    @NonNull
    private final UUID uniqueId;

    @Override
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @NonNull
    public AssetType getType() {
        return AssetType.CASH;
    }
}
