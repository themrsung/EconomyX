package oasis.economyx.classes.actor.company.vaulting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.services.VaultKeeper;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class VaultCompany extends Company implements VaultKeeper {
    /**
     * Creates a new vault company
     *
     * @param uniqueId   Unique ID of this vault company
     * @param name       Name of this vault company
     * @param stockId    ID of this vault company's stock
     * @param shareCount Initial share count
     * @param currency   Currency to use
     */
    public VaultCompany(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.vaults = new ArrayList<>();
        this.hourlyVaultFee = new CashStack(currency, 0L);
    }

    public VaultCompany() {
        super();

        this.vaults = new ArrayList<>();
        this.hourlyVaultFee = null;
    }

    public VaultCompany(VaultCompany other) {
        super(other);
        this.vaults = other.vaults;
        this.hourlyVaultFee = other.hourlyVaultFee;
    }

    @NonNull
    @JsonProperty
    private final List<VaultBlock> vaults;

    @NonNull
    @JsonProperty
    private CashStack hourlyVaultFee;

    @NonNull
    @Override
    @JsonIgnore
    public List<VaultBlock> getVaults() {
        return new ArrayList<>(vaults);
    }

    @Override
    @JsonIgnore
    public void addVault(@NonNull VaultBlock vault) {
        vaults.add(vault);
    }

    @Override
    @JsonIgnore
    public void removeVault(@NonNull VaultBlock vault) {
        vaults.remove(vault);
    }

    @NonNull
    @Override
    @JsonIgnore
    public CashStack getHourlyVaultFee() {
        return new CashStack(hourlyVaultFee);
    }

    @Override
    @JsonIgnore
    public void setHourlyVaultFee(@NonNull CashStack fee) {
        this.hourlyVaultFee = fee;
    }

    @JsonProperty
    private final Type type = Type.VAULT_COMPANY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
