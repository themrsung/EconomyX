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
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * An instantiable class of Account
 */
public final class AssetAccount implements Account {
    /**
     * Creates a new asset account. Collateral is automatically handled
     *
     * @param uniqueId    Unique ID of this account
     * @param institution Institution holding this account
     * @param client      Client of this account
     * @param content     Initial contents of this account
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

    @NonNull
    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @NonNull
    @Override
    @JsonIgnore
    public Banker getInstitution() {
        return institution;
    }

    @NonNull
    @Override
    @JsonIgnore
    public Actor getClient() {
        return client;
    }

    @NonNull
    @Override
    @JsonIgnore
    public AssetStack getContent() {
        return content;
    }

    @NonNull
    @Override
    @JsonIgnore
    public CollateralStack getCollateral() {
        return collateral;
    }

    @Override
    public void deposit(@NonNull AssetStack asset) throws IllegalArgumentException {
        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                client,
                institution,
                asset,
                PaymentEvent.Cause.BANK_DEPOSIT
        ));

        content.addQuantity(asset.getQuantity());
        collateral.addQuantity(asset.getQuantity());
    }

    @Override
    public void withdraw(@NonNull AssetStack asset) throws IllegalArgumentException {
        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                institution,
                client,
                asset,
                PaymentEvent.Cause.BANK_WITHDRAWAL
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
