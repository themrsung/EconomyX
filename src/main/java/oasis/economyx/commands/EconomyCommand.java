package oasis.economyx.commands;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A better command executor.
 */
public abstract class EconomyCommand implements CommandExecutor, TabCompleter {
    public EconomyCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        this.EX = EX;
        this.state = state;
    }

    @NonNull
    private final EconomyX EX;

    @NonNull
    private final EconomyState state;

    @NonNull
    protected final EconomyX getEX() {
        return EX;
    }

    @NonNull
    protected final EconomyState getState() {
        return state;
    }

    @Override
    public final boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if (sender instanceof Player player) {
            Person caller = state.getPerson(player.getUniqueId());
            onEconomyCommand(player, caller, caller, args, AccessLevel.SELF);
            return true;
        }

        return false;
    }

    public abstract void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission);

    @Override
    public final List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        List<String> list = new ArrayList<>();

        onEconomyComplete(list, args);

        return list;
    }

    public abstract void onEconomyComplete(final @NonNull List<String> list, @NonNull String[] params);

    protected enum Keyword {
        CREATE,
        BALANCE,
        SET_ADDRESS;

        private static final List<String> K_CREATE = Arrays.asList("create", "new", "추가", "신규", "생성");
        private static final List<String> K_BALANCE = Arrays.asList("bal", "balance", "잔고");
        private static final List<String> K_SET_ADDRESS = Arrays.asList("sethome", "setaddress", "집설정", "주소설정", "주소지설정");

        @Nullable
        public static Keyword fromInput(@NonNull String input) {
            if (K_CREATE.contains(input.toLowerCase())) return CREATE;
            if (K_BALANCE.contains(input.toLowerCase())) return BALANCE;
            if (K_SET_ADDRESS.contains(input.toLowerCase())) return SET_ADDRESS;

            return null;
        }

        public List<String> toInput() {
            List<String> results = new ArrayList<>();

            switch (this) {
                case CREATE -> {
                    results.addAll(K_CREATE);
                }
                case BALANCE -> {
                    results.addAll(K_BALANCE);
                }
                case SET_ADDRESS -> {
                    results.addAll(K_SET_ADDRESS);
                }
            }

            return results;
        }
    }

    protected abstract static class Messages {
        public static String INVALID_NUMBER = ChatColor.RED + "유효하지 않은 숫자입니다.";
        public static String INVALID_KEYWORD = ChatColor.RED + "유효하지 않은 키워드입니다.";

        public static String INSUFFICIENT_PERMISSIONS = ChatColor.RED + "권한이 부족합니다.";
        public static String INSUFFICIENT_ARGS = ChatColor.RED + "입력이 부족합니다.";
        public static String ACTOR_NOT_FOUND = ChatColor.RED + "대상을 찾을 수 없습니다.";

        public static String CASH_BALANCE(@NonNull CashStack cash) {
            return "[현금] " + NumberFormat.getIntegerInstance().format(cash.getQuantity()) + " " + cash.getAsset().getName();
        }
    }

    protected abstract static class Inputs {
        public static long fromNumber(@NonNull String input) {
            String[] koreanDenotations = {"경", "조", "억", "만", "천", "백", "십"};
            long[] koreanUnits = {10000000000000000L, 1000000000000L, 100000000L, 10000L, 1000L, 100L, 10L};

            for (int i = 0; i < koreanDenotations.length; i++) {
                if (input.contains(koreanDenotations[i])) {
                    String raw = input.replaceAll(koreanDenotations[i], "");
                    try {
                        return Long.parseLong(raw) * koreanUnits[i];
                    } catch (NumberFormatException e) {
                        return -1L;
                    }
                }
            }

            String[] englishDenotations = {"t", "b", "m", "k"};
            long[] englishUnits = {1000000000000L, 1000000000L, 1000000L, 1000L};

            for (int i = 0; i < englishDenotations.length; i++) {
                if (input.contains(englishDenotations[i])) {
                    String raw = input.replaceAll(englishDenotations[i], "");
                    try {
                        return Long.parseLong(raw) * englishUnits[i];
                    } catch (NumberFormatException e) {
                        return -1L;
                    }
                }
            }

            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                return -1L;
            }
        }

        @Nullable
        public static Actor searchActor(@NonNull String input, @NonNull EconomyState state) {
            for (Actor a : state.getActors()) {
                if (Objects.equals(a.getName(), input)) {
                    return a;
                }
            }

            for (Actor a : state.getActors()) {
                String name = a.getName();
                if (name != null) {
                    if (name.toLowerCase().contains(input.toLowerCase())) {
                        return a;
                    }
                }
            }

            for (Actor a : state.getActors()) {
                if (a.getUniqueId().toString().contains(input)) {
                    return a;
                }
            }

            return null;
        }
    }

    protected abstract static class Lists {
        public static List<String> ACTOR_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (Actor a : state.getActors()) {
                results.add(a.getName());
            }

            return results;
        }

        public static List<String> CURRENCY_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (Cash currency : state.getCurrencies()) {
                results.add(currency.getName());
            }

            return results;
        }
    }

    protected enum AccessLevel {
        SELF,
        DE_FACTO_SELF,
        DIRECTOR,
        EMPLOYEE;

        public boolean isAtLeast(@NonNull AccessLevel other) {
            switch (other) {
                case SELF -> {
                    return this == SELF;
                }

                case DE_FACTO_SELF -> {
                    return isAtLeast(SELF) || this == DE_FACTO_SELF;
                }

                case DIRECTOR -> {
                    return isAtLeast(DE_FACTO_SELF) || this == DIRECTOR;
                }

                case EMPLOYEE -> {
                    return isAtLeast(DIRECTOR) || this == EMPLOYEE;
                }
            }

            return false;
        }
    }
}
