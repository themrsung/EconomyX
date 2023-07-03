package oasis.economyx.commands;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
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

        // EconomyX does not support console commands.
        return false;
    }

    /**
     * Called when command is executed. (either by oneself or by sudo executor)
     * @param player Actual player who typed in the command
     * @param caller Actual person who called the command
     * @param actor Actor to execute the command as
     * @param params Command parameters
     * @param permission Permission level of the caller (see {@link AccessLevel})
     */
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
        SET_ADDRESS,
        PAY,
        MESSAGE,
        REPLY,
        PHYSICALIZE,
        DEPHYSICALIZE,
        SEND_ASSET,
        INFO,

        // Allows recursive sudo by default
        SUDO;

        private static final List<String> K_CREATE = Arrays.asList("create", "new", "추가", "신규", "생성");
        private static final List<String> K_BALANCE = Arrays.asList("bal", "balance", "잔고");
        private static final List<String> K_SET_ADDRESS = Arrays.asList("sethome", "setaddress", "집설정", "주소설정", "주소지설정");
        private static final List<String> K_PAY = Arrays.asList("pay", "송금");
        private static final List<String> K_MESSAGE = Arrays.asList("dm", "msg", "message", "디엠", "메시지");
        private static final List<String> K_REPLY = Arrays.asList("r", "reply", "답변", "답장");
        private static final List<String> K_PHYSICALIZE = Arrays.asList("physicalize", "실물화");
        private static final List<String> K_DEPHYSICALIZE = Arrays.asList("dephysicalize", "가상화");
        private static final List<String> K_SEND_ASSET = Arrays.asList("send", "sasset", "sendasset", "양도");
        private static final List<String> K_INFO = Arrays.asList("i", "info", "information", "정보");

        private static final List<String> K_SUDO = Arrays.asList("sudo", "as", "대신", "대변");

        @Nullable
        public static Keyword fromInput(@NonNull String input) {
            if (K_CREATE.contains(input.toLowerCase())) return CREATE;
            if (K_BALANCE.contains(input.toLowerCase())) return BALANCE;
            if (K_SET_ADDRESS.contains(input.toLowerCase())) return SET_ADDRESS;
            if (K_PAY.contains(input.toLowerCase())) return PAY;
            if (K_MESSAGE.contains(input.toLowerCase())) return MESSAGE;
            if (K_REPLY.contains(input.toLowerCase())) return REPLY;
            if (K_PHYSICALIZE.contains(input.toLowerCase())) return PHYSICALIZE;
            if (K_DEPHYSICALIZE.contains(input.toLowerCase())) return DEPHYSICALIZE;
            if (K_SEND_ASSET.contains(input.toLowerCase())) return SEND_ASSET;
            if (K_INFO.contains(input.toLowerCase())) return INFO;
            if (K_SUDO.contains(input.toLowerCase())) return SUDO;

            return null;
        }

        public List<String> toInput() {
            return switch (this) {
                case CREATE -> K_CREATE;
                case BALANCE -> K_BALANCE;
                case SET_ADDRESS -> K_SET_ADDRESS;
                case PAY -> K_PAY;
                case REPLY -> K_REPLY;
                case MESSAGE -> K_MESSAGE;
                case PHYSICALIZE -> K_PHYSICALIZE;
                case DEPHYSICALIZE -> K_DEPHYSICALIZE;
                case SEND_ASSET -> K_SEND_ASSET;
                case INFO -> K_INFO;
                case SUDO -> K_SUDO;
                default -> new ArrayList<>();
            };
        }
    }

    protected abstract static class Messages {
        public static String UNKNOWN_ERROR = ChatColor.RED + "알 수 없는 오류가 발생했습니다. 관리자에게 제보 부탁드립니다.";
        public static String INVALID_TYPE = ChatColor.RED + "유효하지 않은 유형입니다.";
        public static String INVALID_NUMBER = ChatColor.RED + "유효하지 않은 숫자입니다.";
        public static String INVALID_CURRENCY = ChatColor.RED + "유효하지 않은 통화입니다.";
        public static String INVALID_KEYWORD = ChatColor.RED + "유효하지 않은 키워드입니다.";
        public static String INVALID_ASSET = ChatColor.RED + "유효하지 않은 자산입니다.";

        public static String INSERT_NUMBER = "숫자를 입력하세요.";
        public static String INSERT_NAME = "이름을 입력하세요. (띄어쓰기 불가)";
        public static String INSERT_SHARE_COUNT = "발행할 주식수를 입력하세요. (정수)";
        public static String INSERT_CAPITAL = "자본금을 입력하세요. (정수)";
        public static String INSERT_MESSAGE = "메시지를 입력하세요.";
        public static String ALL_DONE = "필요한 항목을 전부 입력했습니다.";
        public static String INSERT_CURRENCY_TO_ISSUE = "발행할 통화의 이름을 입력하세요. (영문 3글자 이하, 중복 불가)";

        public static String NAME_TOO_LONG(int maxLength) {
            return ChatColor.RED + "이름은 " + NumberFormat.getIntegerInstance().format(maxLength) + "글자를 초과할 수 없습니다.";
        }
        public static String ACTOR_ONLY_CREATABLE_BY_SOVEREIGNS = ChatColor.RED + "국가만 설립 가능합니다.";
        public static String ACTOR_ONLY_CREATABLE_BY_CORPORATIONS = ChatColor.RED + "기업만 설립 가능합니다.";
        public static String ACTOR_ONLY_CREATABLE_BY_PERSONS = ChatColor.RED + "플레이어만 설립 가능합니다.";

        public static String NAME_TAKEN = ChatColor.RED + "이름이 이미 사용 중입니다.";

        public static String INSUFFICIENT_CASH = ChatColor.RED + "잔고가 부족합니다.";
        public static String INSUFFICIENT_ASSETS = ChatColor.RED + "자산이 부족합니다.";
        public static String INSUFFICIENT_PERMISSIONS = ChatColor.RED + "권한이 부족합니다.";
        public static String INSUFFICIENT_ARGS = ChatColor.RED + "입력이 부족합니다.";
        public static String ACTOR_NOT_FOUND = ChatColor.RED + "대상을 찾을 수 없습니다.";

        public static String CORPORATION_CREATED = ChatColor.GREEN + "기업이 설립되었습니다.";
        public static String FUND_CREATED = ChatColor.GREEN + "펀드가 설립되었습니다.";
        public static String INSTITUTION_CREATED = ChatColor.GREEN + "기관이 설립되었습니다.";
        public static String SOVEREIGNTY_CREATED = ChatColor.GREEN + "국가가 설립되었습니다.";
        public static String ORGANIZATION_CREATED = ChatColor.GREEN + "조직이 설립되었습니다.";

        public static String NO_MESSAGES_RECEIVED = ChatColor.RED + "수신한 메시지가 없습니다.";

        public static String ASSET_NOT_FOUND = ChatColor.RED + "자산을 찾지 못했습니다.";
        public static String NO_SPACE_IN_INVENTORY = ChatColor.RED + "인벤토리에 빈 공간이 없습니다.";
        public static String ASSET_PHYSICALIZED = ChatColor.GREEN + "자산을 실물화했습니다.";
        public static String ASSET_DEPHYSICALIZED = ChatColor.GREEN + "자산을 가상화했습니다.";

        public static List<String> ACTOR_INFORMATION_OUTSIDER(@NonNull Actor actor) {
            List<String> info = new ArrayList<>();

            info.add(actor.getName() + " 정보");
            info.add("유형: " + actor.getType().toString());

            return info;
        }

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

        @Nullable
        public static Cash searchCurrency(@NonNull String input, @NonNull EconomyState state) {
            for (Cash currency : state.getCurrencies()) {
                if (currency.getName().equalsIgnoreCase(input)) {
                    return currency;
                }
            }

            for (Cash currency : state.getCurrencies()) {
                if (currency.getName().toLowerCase().contains(input.toLowerCase())) {
                    return currency;
                }
            }

            for (Cash currency : state.getCurrencies()) {
                if (currency.getUniqueId().toString().contains(input)) {
                    return currency;
                }
            }

            return null;
        }

        @Nullable
        public static Asset searchAsset(@NonNull String input, @NonNull EconomyState state) {
            for (AssetStack as : state.getAssets()) {
                if (as.getAsset().getName().equalsIgnoreCase(input)) {
                    return as.getAsset();
                }
            }

            for (AssetStack as : state.getAssets()) {
                if (as.getAsset().getName().toLowerCase().contains(input)) {
                    return as.getAsset();
                }
            }

            for (AssetStack as : state.getAssets()) {
                if (as.getAsset().getUniqueId().toString().contains(input)) {
                    return as.getAsset();
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

        /**
         * May cause lag. Use with caution.
         */
        public static List<String> ASSET_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (AssetStack as : state.getAssets()) {
                String name = as.getAsset().getName();
                if (!results.contains(name)) results.add(name);
            }

            return results;
        }
    }

    /**
     * When multiple access levels are detected, the highest one will be set.
     */
    protected enum AccessLevel {
        /**
         * Oneself
         */
        SELF,

        /**
         * When the caller is the representative of the actor
         */
        DE_FACTO_SELF,

        /**
         * When the caller is a director of the actor
         */
        DIRECTOR,

        /**
         * When the caller is an employee of the actor
         */
        EMPLOYEE,

        /**
         * When the caller is an outsider
         */
        OUTSIDER;

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

                case OUTSIDER -> {
                    return isAtLeast(EMPLOYEE) || this == OUTSIDER;
                }
            }

            return false;
        }

        public static AccessLevel getPermission(@NonNull Actor actor, @NonNull Actor checkAs) {
            if (checkAs instanceof Person person) {
                if (actor instanceof Person p) {
                    if (p.equals(checkAs)) return SELF;
                }

                if (actor instanceof Representable r) {
                    if (Objects.equals(r.getRepresentative(), person)) return DE_FACTO_SELF;
                }

                if (actor instanceof Employer e) {
                    if (e.getDirectors().contains(person)) return DIRECTOR;
                    if (e.getEmployees().contains(person)) return EMPLOYEE;
                }
            }

            return OUTSIDER;
        }
    }
}
