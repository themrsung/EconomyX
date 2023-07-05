package oasis.economyx.commands.management;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.trading.market.Market;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.trading.listing.AssetDelistedEvent;
import oasis.economyx.events.trading.listing.AssetListedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.commodity.Commodity;
import oasis.economyx.types.asset.commodity.CommodityStack;
import oasis.economyx.types.asset.contract.collateral.Collateral;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import oasis.economyx.types.asset.contract.forward.Forward;
import oasis.economyx.types.asset.contract.forward.ForwardStack;
import oasis.economyx.types.asset.contract.note.Note;
import oasis.economyx.types.asset.contract.note.NoteStack;
import oasis.economyx.types.asset.contract.option.Option;
import oasis.economyx.types.asset.contract.option.OptionStack;
import oasis.economyx.types.asset.contract.swap.Swap;
import oasis.economyx.types.asset.contract.swap.SwapStack;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.List;

public final class AssetListingCommand extends EconomyCommand {
    public AssetListingCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!(actor instanceof Exchange exchange)) {
            player.sendRawMessage(Messages.ACTOR_NOT_EXCHANGE);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        Action action = Action.fromInput(params[0]);
        if (action == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        switch (action) {
            case SHOW_LISTED_ASSETS -> {
                for (Marketplace market : exchange.getMarkets()) {
                    player.sendRawMessage(Messages.MARKET_INFORMATION(market, getState()));
                }
            }
            case LIST_ASSET, DELIST_ASSET -> {
                if (!permission.isAtLeast(AccessLevel.DIRECTOR)) {
                    player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
                    return;
                }

                if (params.length < 4) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }

                Asset asset = Inputs.searchAsset(params[1], getState());
                if (asset == null) {
                    player.sendRawMessage(Messages.ASSET_NOT_FOUND);
                    return;
                }

                switch (action) {
                    case LIST_ASSET -> {
                        long contractSize = Inputs.fromNumber(params[2]);
                        if (contractSize <= 0L) {
                            player.sendRawMessage(Messages.INVALID_NUMBER);
                            return;
                        }

                        Cash currency = Inputs.searchCurrency(params[3], getState());
                        if (currency == null) {
                            player.sendRawMessage(Messages.INVALID_CURRENCY);
                            return;
                        }

                        AssetStack stack = switch (asset.getType()) {
                            case CASH -> new CashStack((Cash) asset, contractSize);
                            case STOCK -> new StockStack((Stock) asset, contractSize);
                            case COMMODITY -> new CommodityStack((Commodity) asset, contractSize);
                            case NOTE -> new NoteStack((Note) asset, contractSize);
                            case COLLATERAL -> new CollateralStack((Collateral) asset, contractSize);
                            case FORWARD -> new ForwardStack((Forward) asset, contractSize);
                            case OPTION -> new OptionStack((Option) asset, contractSize);
                            case SWAP -> new SwapStack((Swap) asset, contractSize);
                            default -> null;
                        };

                        if (stack == null) {
                            player.sendRawMessage(Messages.INVALID_ASSET);
                            return;
                        }

                        for (Marketplace existing : exchange.getMarkets()) {
                            if (existing.getAsset().getAsset().equals(asset)) {
                                player.sendRawMessage(Messages.ASSET_ALREADY_LISTED);
                                return;
                            }
                        }

                        Marketplace listing = new Market(stack, currency);
                        Bukkit.getPluginManager().callEvent(new AssetListedEvent(exchange, listing));
                        player.sendRawMessage(Messages.ASSET_LISTED);
                    }
                    case DELIST_ASSET -> {
                        for (Marketplace market : exchange.getMarkets()) {
                            if (market.getAsset().getAsset().equals(asset)) {
                                Bukkit.getPluginManager().callEvent(new AssetDelistedEvent(exchange, market));
                                player.sendRawMessage(Messages.ASSET_DELISTED);
                                return;
                            }
                        }

                        player.sendRawMessage(Messages.ASSET_NOT_LISTED);
                    }
                }
            }
        }
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            for (Action a : Action.values()) list.addAll(a.toInput());
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else {
            Action action = Action.fromInput(params[0]);
            if (action != null) {
                switch (action) {
                    case LIST_ASSET -> {
                        if (params.length < 3) {
                            list.addAll(Lists.ASSET_NAMES(getState()));
                            if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
                        } else if (params.length < 4) {
                            list.add(Messages.INSERT_NUMBER);
                        } else if (params.length < 5) {
                            list.addAll(Lists.CURRENCY_NAMES(getState()));
                            if (!params[3].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[3].toLowerCase()));
                        } else {
                            list.add(Messages.ALL_DONE);
                        }
                    }
                    case DELIST_ASSET -> {
                        if (params.length < 3) {
                            list.addAll(Lists.ASSET_NAMES(getState()));
                        } else {
                            list.add(Messages.ALL_DONE);
                        }
                    }
                    case SHOW_LISTED_ASSETS -> {
                        list.add(Messages.ALL_DONE);
                    }
                }
            }
        }
    }

    private enum Action {
        LIST_ASSET,
        DELIST_ASSET,
        SHOW_LISTED_ASSETS;

        private static final List<String> K_LIST_ASSET = Arrays.asList("listasset", "상장");
        private static final List<String> K_DELIST_ASSET = Arrays.asList("delist", "delistasset", "상장폐지");
        private static final List<String> K_SHOW_LISTED_ASSETS = Arrays.asList("listed", "showlisted", "상장자산", "목록");

        @Nullable
        public static Action fromInput(@NonNull String input) {
            if (K_LIST_ASSET.contains(input.toLowerCase())) return LIST_ASSET;
            if (K_DELIST_ASSET.contains(input.toLowerCase())) return DELIST_ASSET;
            if (K_SHOW_LISTED_ASSETS.contains(input.toLowerCase())) return SHOW_LISTED_ASSETS;
            return null;
        }

        @NonNull
        public List<String> toInput() {
            return switch (this) {
                case LIST_ASSET -> K_LIST_ASSET;
                case DELIST_ASSET -> K_DELIST_ASSET;
                case SHOW_LISTED_ASSETS -> K_SHOW_LISTED_ASSETS;
            };
        }
    }
}
