package oasis.economyx.classes.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.card.CreditCard;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import oasis.economyx.types.asset.contract.forward.ForwardStack;
import oasis.economyx.types.asset.contract.note.NoteStack;
import oasis.economyx.types.asset.contract.option.OptionStack;
import oasis.economyx.types.asset.contract.swap.SwapStack;
import oasis.economyx.types.portfolio.AssetPortfolio;
import oasis.economyx.types.portfolio.Portfolio;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * Base class for all actors
 */
public abstract class EconomicActor implements Actor {
    public EconomicActor(UUID uniqueId, @Nullable String name) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.portfolio = new AssetPortfolio();
        this.address = null;
    }

    public EconomicActor() {
        this.uniqueId = UUID.randomUUID();
        this.name = null;
        this.portfolio = new AssetPortfolio();
        this.address = null;
    }

    public EconomicActor(EconomicActor other) {
        this.uniqueId = other.uniqueId;
        this.name = other.name;
        this.portfolio = other.portfolio;
        this.address = other.address;
    }

    @JsonProperty
    private final UUID uniqueId;
    @Nullable
    @JsonProperty
    private String name;
    @JsonProperty
    private final Portfolio portfolio;

    @Nullable
    @JsonProperty
    private Address address;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public @Nullable String getName() {
        return name;
    }

    @Override
    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Override
    @JsonIgnore
    public Portfolio getAssets() {
        return portfolio;
    }

    @Override
    @JsonIgnore
    public Portfolio getOutstandingCollateral(EconomyState state) {
        Portfolio collateral = new AssetPortfolio();

        for (Actor a : state.getActors()) {
            for (AssetStack as : a.getAssets().get()) {
                if (as instanceof CollateralStack cs) {
                    if (cs.getAsset().getCounterparty().equals(this)) {
                        collateral.add(cs);
                    }
                }
            }
        }

        return collateral;
    }

    @Override
    @JsonIgnore
    public Portfolio getLiabilities(EconomyState state) {
        Portfolio liabilities = new AssetPortfolio();

        for (Actor a : state.getActors()) {
            for (AssetStack as : a.getAssets().get()) {

                // Outstanding collateral
                if (as instanceof CollateralStack cs) {
                    if (cs.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(cs);
                    }

                    // Undelivered forwards
                } else if (as instanceof ForwardStack fs) {
                    if (fs.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(fs);
                    }

                    // Undelivered notes
                } else if (as instanceof NoteStack ns) {
                    if (ns.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(ns);
                    }

                    // Premature options (where this actor is the writer)
                } else if (as instanceof OptionStack os) {
                    if (os.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(os);
                    }

                    // Premature swaps (this can be an asset depending on the base and quote asset's prices)
                } else if (as instanceof SwapStack ss) {
                    if (ss.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(ss);
                    }
                }
            }

            // Outstanding credit card balance
            for (Card c : state.getCards()) {
                if (c.getHolder().equals(this)) {
                    if (c instanceof CreditCard cc) {
                        liabilities.add(cc.getBalance());
                    }
                }
            }
        }

        return liabilities;
    }

    @Override
    @JsonIgnore
    public Portfolio getPayableAssets(EconomyState state) {
        Portfolio assets = new AssetPortfolio(getAssets());

        assets.remove(getOutstandingCollateral(state));

        return assets;
    }

    @Override
    @JsonIgnore
    public Portfolio getNetAssets(EconomyState state) {
        Portfolio assets = new AssetPortfolio(getAssets());

        assets.remove(getLiabilities(state));

        return assets;
    }

    @Nullable
    @Override
    @JsonIgnore
    public Address getAddress() {
        return address;
    }

    @Override
    @JsonIgnore
    public void setAddress(@Nullable Address address) {
        this.address = address;
    }
}
