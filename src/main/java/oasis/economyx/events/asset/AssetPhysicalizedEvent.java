package oasis.economyx.events.asset;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.AssetStack;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AssetPhysicalizedEvent extends EconomyEvent {
    public AssetPhysicalizedEvent(@NonNull Actor holder, @NonNull AssetStack asset, @NonNull Player physicalizer) {
        this.holder = holder;
        this.asset = asset;
        this.physicalizer = physicalizer;
    }

    @NonNull
    private final Actor holder;

    @NonNull
    private final AssetStack asset;

    @NonNull
    private final Player physicalizer;

    @NonNull
    public Actor getHolder() {
        return holder;
    }

    @NonNull
    public AssetStack getAsset() {
        return asset;
    }

    @NonNull
    public Player getPhysicalizer() {
        return physicalizer;
    }
}
