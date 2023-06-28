package oasis.economyx.asset.stock;

import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Right to partial ownership of a corporation
 */
public final class Stock implements Asset {
    public Stock() {
        this.uniqueId = UUID.randomUUID();
    }

    public Stock(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Stock(Stock other) {
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
        return AssetType.STOCK;
    }
}
