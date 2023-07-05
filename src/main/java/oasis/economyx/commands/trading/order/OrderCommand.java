package oasis.economyx.commands.trading.order;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.trading.market.AssetOrder;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.trading.order.OrderCancelledEvent;
import oasis.economyx.events.trading.order.OrderPlacedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class OrderCommand extends EconomyCommand {
    public OrderCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DIRECTOR)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Action action = Action.fromInput(params[0]);
        if (action == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        switch (action) {
            case MY_ORDERS -> {
                for (Order o : getState().getOrders()) {
                    if (o.getSender().equals(actor)) {
                        player.sendRawMessage(Messages.ORDER_INFORMATION(o, getState()));
                    }
                }
            }
            case CANCEL_ORDER -> {
                if (params.length < 2) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }

                String partialId = params[1].toLowerCase();
                for (Exchange e : getState().getExchanges()) {
                    for (Marketplace l : e.getMarkets()) {
                        for (Order o : l.getOrders()) {
                            if (o.getUniqueId().toString().contains(partialId)) {
                                if (!o.getSender().equals(actor)) {
                                    player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
                                    return;
                                }

                                Bukkit.getPluginManager().callEvent(new OrderCancelledEvent(e, l, o));
                                player.sendRawMessage(Messages.ORDER_CANCELLED);
                                return;
                            }
                        }
                    }
                }

                player.sendRawMessage(Messages.ORDER_NOT_FOUND);
            }
            case PLACE_ORDER -> {
                if (params.length < 6) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }


                Order.Type type;

                try {
                    type = Order.Type.valueOf(params[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    player.sendRawMessage(Messages.INVALID_KEYWORD);
                    return;
                }

                Asset asset = Inputs.searchAsset(params[2], getState());
                if (asset == null) {
                    player.sendRawMessage(Messages.ASSET_NOT_FOUND);
                    return;
                }

                Exchange exchange = Inputs.searchExchange(params[3], getState());
                if (exchange == null) {
                    player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                    return;
                }

                Marketplace market = null;
                for (Marketplace m : exchange.getMarkets()) {
                    if (m.getAsset().getAsset().equals(asset)) {
                        market = m;
                        break;
                    }
                }

                if (market == null) {
                    player.sendRawMessage(Messages.ASSET_NOT_LISTED);
                    return;
                }


                long price = Inputs.fromNumber(params[4]);
                if (price < 0L) {
                    player.sendRawMessage(Messages.INVALID_NUMBER);
                    return;
                }

                long quantity = Inputs.fromNumber(params[5]);
                if (quantity <= 0L) {
                    player.sendRawMessage(Messages.INVALID_NUMBER);
                    return;
                }

                // TODO add collateral

                Order order = new AssetOrder(
                        UUID.randomUUID(),
                        market,
                        EconomyState.TEMP_BROKER,
                        actor,
                        type,
                        new CashStack(market.getCurrency(), price),
                        quantity,
                        null
                );

                Bukkit.getPluginManager().callEvent(new OrderPlacedEvent(exchange, market, order));
                player.sendRawMessage(Messages.ORDER_SUBMITTED);
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
                    case MY_ORDERS -> {
                        list.add(Messages.ALL_DONE);
                    }
                    case CANCEL_ORDER -> {
                        if (params.length < 3) {
                            list.add(Messages.INSERT_ORDER_NUMBER);
                        } else {
                            list.add(Messages.ALL_DONE);
                        }
                    }
                    case PLACE_ORDER -> {
                        if (params.length < 3) {
                            for (Order.Type ot : Order.Type.values()) list.add(ot.toString()); // TODO localize this
                            if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
                        } else if (params.length < 4) {
                            list.addAll(Lists.ASSET_NAMES(getState()));
                            if (!params[2].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[2].toLowerCase()));
                        } else if (params.length < 5) {
                            list.addAll(Lists.EXCHANGE_NAMES(getState()));
                            if (!params[3].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[3].toLowerCase()));
                        } else if (params.length < 6) {
                            list.add(Messages.INSERT_PRICE);
                            if (!params[4].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[4].toLowerCase()));
                        } else if (params.length < 7) {
                            list.add(Messages.INSERT_QUANTITY);
                        } else {
                            list.add(Messages.ALL_DONE);
                        }
                    }
                }
            }
        }
    }

    private enum Action {
        PLACE_ORDER,
        MY_ORDERS,
        CANCEL_ORDER;

        private static final List<String> K_PLACE_ORDER = Arrays.asList("place", "placeorder", "new", "neworder", "신규", "접수");
        private static final List<String> K_MY_ORDERS = Arrays.asList("my", "orders", "list", "outstanding", "미체결", "내주문", "주문목록");
        private static final List<String> K_CANCEL_ORDER = Arrays.asList("c", "cancel", "k", "kill", "취소");

        @Nullable
        public static Action fromInput(@NonNull String input) {
            if (K_PLACE_ORDER.contains(input.toLowerCase())) return PLACE_ORDER;
            if (K_MY_ORDERS.contains(input.toLowerCase())) return MY_ORDERS;
            if (K_CANCEL_ORDER.contains(input.toLowerCase())) return CANCEL_ORDER;
            return null;
        }

        @NonNull
        public List<String> toInput() {
            return switch (this) {
                case PLACE_ORDER -> K_PLACE_ORDER;
                case MY_ORDERS -> K_MY_ORDERS;
                case CANCEL_ORDER -> K_CANCEL_ORDER;
            };
        }
    }
}
