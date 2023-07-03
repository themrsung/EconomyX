package oasis.economyx.events.asset;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.PhysicalAsset;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AssetDephysicalizedEvent extends EconomyEvent {
    public AssetDephysicalizedEvent(@NonNull PhysicalAsset asset, @NonNull Actor dephysicalizer) {
        this.asset = asset;
        this.dephysicalizer = dephysicalizer;
    }

    @NonNull
    private final PhysicalAsset asset;

    @NonNull
    private final Actor dephysicalizer;

    @NonNull
    public PhysicalAsset getAsset() {
        return asset;
    }

    @NonNull
    public Actor getDephysicalizer() {
        return dephysicalizer;
    }
}
