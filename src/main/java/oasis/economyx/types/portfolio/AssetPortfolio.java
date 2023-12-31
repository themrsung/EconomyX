package oasis.economyx.types.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AssetPortfolio implements Portfolio {
    public AssetPortfolio() {
        this.assets = new ArrayList<>();
    }

    public AssetPortfolio(List<AssetStack> assets) {
        this.assets = assets;
    }

    public AssetPortfolio(Portfolio other) {
        this.assets = other.get();
    }

    @JsonProperty
    private final List<AssetStack> assets;

    @Override
    @JsonIgnore
    public List<AssetStack> get() {
        return new ArrayList<>(assets);
    }

    @Override
    @JsonIgnore
    public @Nullable AssetStack get(int i) {
        try {
            return assets.get(i);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    @JsonIgnore
    public @Nullable AssetStack get(Asset asset) {
        for (AssetStack stack : assets) {
            if (stack.getAsset().equals(asset)) {
                return stack;
            }
        }

        return null;
    }

    @Override
    @JsonIgnore
    public int size() {
        return assets.size();
    }

    @Override
    @JsonIgnore
    public void add(@NonNull AssetStack asset) {
        AssetStack existing = get(asset.getAsset());

        if (existing != null) {
            existing.addQuantity(asset.getQuantity());
        } else {
            assets.add(asset);
        }
    }

    @Override
    @JsonIgnore
    public void add(@NonNull Portfolio portfolio) {
        for (AssetStack stack : portfolio.get()) {
            add(stack);
        }
    }

    @Override
    @JsonIgnore
    public void remove(@NonNull AssetStack asset) throws IllegalArgumentException {
        AssetStack existing = get(asset.getAsset());
        if (existing == null) return;

        existing.removeQuantity(asset.getQuantity());
        clean();
    }

    @Override
    @JsonIgnore
    public void remove(@NonNull Portfolio portfolio) throws IllegalArgumentException {
        for (AssetStack stack : portfolio.get()) {
            remove(stack);
        }
    }

    /**
     * Deletes entries with 0 as their quantity
     */
    @JsonIgnore
    private void clean() {
        assets.removeIf(a -> a.getQuantity() == 0L);
    }

    @Override
    @JsonIgnore
    public boolean contains(@NonNull AssetStack asset) {
        if (asset.getQuantity() == 0L) return true;

        AssetStack existing = get(asset.getAsset());
        if (existing == null) return false;

        return existing.getQuantity() >= asset.getQuantity();
    }

    @Override
    @JsonIgnore
    public boolean has(@NonNull Asset asset) {
        for (AssetStack stack : get()) {
            if (stack.getAsset().equals(asset)) {
                return true;
            }
        }

        return false;
    }

    @Override
    @JsonIgnore
    public boolean has(@NonNull AssetStack asset, boolean checkMeta) {
        for (AssetStack stack : get()) {
            if (stack.getAsset().equals(asset.getAsset())) {
                return stack.getMeta().equals(asset.getMeta()) || !checkMeta;
            }
        }
        return false;
    }
}
