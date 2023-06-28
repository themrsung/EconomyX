package oasis.economyx.classes;

import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.contract.collateral.CollateralStack;
import oasis.economyx.asset.contract.forward.ForwardStack;
import oasis.economyx.asset.contract.note.NoteStack;
import oasis.economyx.asset.contract.option.OptionStack;
import oasis.economyx.asset.contract.swap.SwapStack;
import oasis.economyx.portfolio.AssetPortfolio;
import oasis.economyx.portfolio.Portfolio;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * Base class for all actors
 */
public abstract class EconomicActor implements Actor {
    public EconomicActor() {
        this.uniqueId = UUID.randomUUID();
        this.name = null;
        this.portfolio = new AssetPortfolio();
    }

    public EconomicActor(EconomicActor other) {
        this.uniqueId = other.uniqueId;
        this.name = other.name;
        this.portfolio = other.portfolio;
    }

    private final UUID uniqueId;
    @Nullable
    private String name;
    private final Portfolio portfolio;

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
    public Portfolio getAssets() {
        return portfolio;
    }

    @Override
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
    public Portfolio getLiabilities(EconomyState state) {
        Portfolio liabilities = new AssetPortfolio();

        for (Actor a : state.getActors()) {
            for (AssetStack as : a.getAssets().get()) {
                if (as instanceof CollateralStack cs) {
                    if (cs.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(cs);
                    }
                } else if (as instanceof ForwardStack fs) {
                    if (fs.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(fs);
                    }
                } else if (as instanceof NoteStack ns) {
                    if (ns.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(ns);
                    }
                } else if (as instanceof OptionStack os) {
                    if (os.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(os);
                    }
                } else if (as instanceof SwapStack ss) {
                    if (ss.getAsset().getCounterparty().equals(this)) {
                        liabilities.add(ss);
                    }
                }
            }
        }

        return liabilities;
    }

    @Override
    public Portfolio getPayableAssets(EconomyState state) {
        Portfolio assets = new AssetPortfolio(getAssets());

        assets.remove(getOutstandingCollateral(state));

        return assets;
    }

    @Override
    public Portfolio getNetAssets(EconomyState state) {
        Portfolio assets = new AssetPortfolio(getAssets());

        assets.remove(getLiabilities(state));

        return assets;
    }
}
