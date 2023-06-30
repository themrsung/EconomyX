package oasis.economyx.classes.banking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.collateral.Collateral;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;

import java.util.UUID;

/**
 * An instantiable class of Account
 */
public final class AssetAccount implements Account {
    /**
     * Creates a new asset account. Collateral is automatically handled
     * @param uniqueId Unique ID of this account
     * @param institution Institution holding this account
     * @param client Client of this account
     * @param content Initial contents of this account
     */
    public AssetAccount(@NonNull UUID uniqueId, @NonNull Banker institution, @NonNull Actor client, @NonNull AssetStack content) {
        this.uniqueId = uniqueId;
        this.institution = institution;
        this.client = client;
        this.content = content;

        AssetStack unit = content.copy();
        unit.setQuantity(1L);

        Collateral c = new Collateral(UUID.randomUUID(), institution, unit, null);
        this.collateral = new CollateralStack(c, content.getQuantity());
    }

    public AssetAccount() {
        this.uniqueId = null;
        this.institution = null;
        this.client = null;
        this.content = null;
        this.collateral = null;
    }

    public AssetAccount(AssetAccount other) {
        this.uniqueId = other.uniqueId;
        this.institution = other.institution;
        this.client = other.client;
        this.content = other.content;
        this.collateral = other.collateral;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    private final Banker institution;

    @NonNull
    @JsonProperty
    private final Actor client;

    @NonNull
    @JsonProperty
    private final AssetStack content;

    @NonNull
    @JsonProperty
    private final CollateralStack collateral;

    @NotNull
    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @NotNull
    @Override
    @JsonIgnore
    public Banker getInstitution() {
        return institution;
    }

    @NotNull
    @Override
    @JsonIgnore
    public Actor getClient() {
        return client;
    }

    @NotNull
    @Override
    @JsonIgnore
    public AssetStack getContent() {
        return content;
    }

    @NotNull
    @Override
    @JsonIgnore
    public CollateralStack getCollateral() {
        return collateral;
    }

    @Override
    public void deposit(@NonNull AssetStack asset) throws IllegalArgumentException {
        Sponge.eventManager().post(new PaymentEvent(
                client,
                institution,
                asset,
                null // TODO
        )); // IAE will be thrown here if client is insolvent

        content.addQuantity(asset.getQuantity());
        collateral.addQuantity(asset.getQuantity());
    }

    @Override
    public void withdraw(@NonNull AssetStack asset) throws IllegalArgumentException {
        Sponge.eventManager().post(new PaymentEvent(
                institution,
                client,
                asset,
                null // TODO
        )); // IAE will be thrown here if banker is insolvent

        content.removeQuantity(asset.getQuantity());
        collateral.removeQuantity(asset.getQuantity());
    }

    @Override
    public void onOpened(@NonNull Banker institution) {
        client.getAssets().add(collateral);
    }

    @Override
    public void onClosed(@NonNull Banker institution) {
        client.getAssets().remove(collateral);
    }
}
