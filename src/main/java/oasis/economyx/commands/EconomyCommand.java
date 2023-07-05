package oasis.economyx.commands;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import oasis.economyx.interfaces.voting.Candidate;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
     *
     * @param player     Actual player who typed in the command
     * @param caller     Actual person who called the command
     * @param actor      Actor to execute the command as
     * @param params     Command parameters
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
        ADDRESS,
        PAY,
        MESSAGE,
        REPLY,
        PHYSICALIZE,
        DEPHYSICALIZE,
        SEND_ASSET,
        INFO,
        OFFER,
        ACCEPT,
        DENY,
        RETIRE,
        CLAIM_PROPERTY,
        ABANDON_PROPERTY,
        SET_PROPERTY_PROTECTOR,
        VOTE,
        PROPERTY_PROTECTION,
        MANAGE_INSTITUTION,
        ISSUE_CURRENCY,
        CHANGE_TAX_RATE,
        JOIN,
        HOSTILTIY,

        // Allows recursive sudo by default
        SUDO;

        private static final List<String> K_CREATE = Arrays.asList("create", "new", "추가", "신규", "생성");
        private static final List<String> K_BALANCE = Arrays.asList("bal", "balance", "잔고");
        private static final List<String> K_SET_ADDRESS = Arrays.asList("sethome", "setaddress", "집설정", "주소설정", "주소지설정");
        private static final List<String> K_ADDRESS = Arrays.asList("home", "address", "홈", "주소", "주소지");
        private static final List<String> K_PAY = Arrays.asList("pay", "송금");
        private static final List<String> K_MESSAGE = Arrays.asList("dm", "msg", "m", "message", "디엠", "메시지");
        private static final List<String> K_REPLY = Arrays.asList("r", "reply", "답변", "답장");
        private static final List<String> K_PHYSICALIZE = Arrays.asList("physicalize", "실물화");
        private static final List<String> K_DEPHYSICALIZE = Arrays.asList("dephysicalize", "가상화");
        private static final List<String> K_SEND_ASSET = Arrays.asList("send", "sasset", "sendasset", "양도");
        private static final List<String> K_INFO = Arrays.asList("i", "info", "information", "정보");
        private static final List<String> K_OFFER = Arrays.asList("o", "offer", "offers", "invite", "invites", "제안", "초대");
        private static final List<String> K_ACCEPT = Arrays.asList("a", "accept", "y", "yes", "수락", "동의");
        private static final List<String> K_DENY = Arrays.asList("d", "deny", "n", "no", "거부", "거절");
        private static final List<String> K_RETIRE = Arrays.asList("retire", "leave", "resign", "은퇴", "사직", "탈퇴");
        private static final List<String> K_CLAIM_PROPERTY = Arrays.asList("claim", "pclaim", "claimproperty", "propertyclaim", "클레임");
        private static final List<String> K_ABANDON_PROPERTY = Arrays.asList("unclaim", "punclaim", "abandon", "abandonproperty", "unclaimproperty", "propertyunclaim", "언클레임", "클레임포기");
        private static final List<String> K_SET_PROPERTY_PROTECTOR = Arrays.asList("sp", "spp", "setprotector", "setpropertyprotector", "보호자설정", "보호인설정");
        private static final List<String> K_VOTE = Arrays.asList("v", "vote", "voting", "voting", "e", "elect", "election", "meeting", "투표", "의결", "주총", "주주총회", "선거");
        private static final List<String> K_PROPERTY_PROTECTION = Arrays.asList("pp", "pprotect", "pprotection", "propertyprotection", "지역보호", "지보호");
        private static final List<String> K_MANAGE_INSTITUTION = Arrays.asList("mi", "inst", "institution", "manageinstitution", "기관", "기관관리", "기관설정");
        private static final List<String> K_ISSUE_CURRENCY = Arrays.asList("ic", "icurrency", "issuecurrency", "pm", "printmoney", "통화발행");
        private static final List<String> K_CHANGE_TAX_RATE = Arrays.asList("ctr", "ctaxrate", "taxrate", "changetaxrate", "str", "staxrate", "settaxrate", "세율변경", "세율설정");
        private static final List<String> K_JOIN = Arrays.asList("j", "join", "가입");
        private static final List<String> K_HOSTILITY = Arrays.asList("hostile", "hostility", "hostilities", "enemy", "enemies", "적", "전투", "전쟁");

        private static final List<String> K_SUDO = Arrays.asList("sudo", "as", "대신", "대변");

        @Nullable
        public static Keyword fromInput(@NonNull String input) {
            if (K_CREATE.contains(input.toLowerCase())) return CREATE;
            if (K_BALANCE.contains(input.toLowerCase())) return BALANCE;
            if (K_SET_ADDRESS.contains(input.toLowerCase())) return SET_ADDRESS;
            if (K_ADDRESS.contains(input.toLowerCase())) return ADDRESS;
            if (K_PAY.contains(input.toLowerCase())) return PAY;
            if (K_MESSAGE.contains(input.toLowerCase())) return MESSAGE;
            if (K_REPLY.contains(input.toLowerCase())) return REPLY;
            if (K_PHYSICALIZE.contains(input.toLowerCase())) return PHYSICALIZE;
            if (K_DEPHYSICALIZE.contains(input.toLowerCase())) return DEPHYSICALIZE;
            if (K_SEND_ASSET.contains(input.toLowerCase())) return SEND_ASSET;
            if (K_INFO.contains(input.toLowerCase())) return INFO;
            if (K_OFFER.contains(input.toLowerCase())) return OFFER;
            if (K_ACCEPT.contains(input.toLowerCase())) return ACCEPT;
            if (K_DENY.contains(input.toLowerCase())) return DENY;
            if (K_RETIRE.contains(input.toLowerCase())) return RETIRE;
            if (K_CLAIM_PROPERTY.contains(input.toLowerCase())) return CLAIM_PROPERTY;
            if (K_ABANDON_PROPERTY.contains(input.toLowerCase())) return ABANDON_PROPERTY;
            if (K_VOTE.contains(input.toLowerCase())) return VOTE;
            if (K_SET_PROPERTY_PROTECTOR.contains(input.toLowerCase())) return SET_PROPERTY_PROTECTOR;
            if (K_PROPERTY_PROTECTION.contains(input.toLowerCase())) return PROPERTY_PROTECTION;
            if (K_MANAGE_INSTITUTION.contains(input.toLowerCase())) return MANAGE_INSTITUTION;
            if (K_ISSUE_CURRENCY.contains(input.toLowerCase())) return ISSUE_CURRENCY;
            if (K_CHANGE_TAX_RATE.contains(input.toLowerCase())) return CHANGE_TAX_RATE;
            if (K_JOIN.contains(input.toLowerCase())) return JOIN;
            if (K_HOSTILITY.contains(input.toLowerCase())) return HOSTILTIY;

            if (K_SUDO.contains(input.toLowerCase())) return SUDO;

            return null;
        }

        public List<String> toInput() {
            return switch (this) {
                case CREATE -> K_CREATE;
                case BALANCE -> K_BALANCE;
                case SET_ADDRESS -> K_SET_ADDRESS;
                case ADDRESS -> K_ADDRESS;
                case PAY -> K_PAY;
                case REPLY -> K_REPLY;
                case MESSAGE -> K_MESSAGE;
                case PHYSICALIZE -> K_PHYSICALIZE;
                case DEPHYSICALIZE -> K_DEPHYSICALIZE;
                case SEND_ASSET -> K_SEND_ASSET;
                case INFO -> K_INFO;
                case OFFER -> K_OFFER;
                case ACCEPT -> K_ACCEPT;
                case DENY -> K_DENY;
                case RETIRE -> K_RETIRE;
                case CLAIM_PROPERTY -> K_CLAIM_PROPERTY;
                case ABANDON_PROPERTY -> K_ABANDON_PROPERTY;
                case SET_PROPERTY_PROTECTOR -> K_SET_PROPERTY_PROTECTOR;
                case VOTE -> K_VOTE;
                case PROPERTY_PROTECTION -> K_PROPERTY_PROTECTION;
                case MANAGE_INSTITUTION -> K_MANAGE_INSTITUTION;
                case ISSUE_CURRENCY -> K_ISSUE_CURRENCY;
                case CHANGE_TAX_RATE -> K_CHANGE_TAX_RATE;
                case JOIN -> K_JOIN;
                case HOSTILTIY -> K_HOSTILITY;

                case SUDO -> K_SUDO;
            };
        }
    }

    protected abstract static class Messages {
        public static final String UNKNOWN_ERROR = ChatColor.RED + "알 수 없는 오류가 발생했습니다. 관리자에게 제보 부탁드립니다.";
        public static final String INVALID_TYPE = ChatColor.RED + "유효하지 않은 유형입니다.";
        public static final String INVALID_NUMBER = ChatColor.RED + "유효하지 않은 숫자입니다.";
        public static final String INVALID_CURRENCY = ChatColor.RED + "유효하지 않은 통화입니다.";
        public static final String INVALID_KEYWORD = ChatColor.RED + "유효하지 않은 키워드입니다.";
        public static final String INVALID_ASSET = ChatColor.RED + "유효하지 않은 자산입니다.";

        public static final String INSERT_NUMBER = "숫자를 입력하세요.";
        public static final String INSERT_PERCENT = "퍼센트를 입력하세요. (예: 23 -> 23%)";
        public static final String INSERT_NAME = "이름을 입력하세요. (띄어쓰기 불가)";
        public static final String INSERT_SHARE_COUNT = "발행할 주식수를 입력하세요. (정수)";
        public static final String INSERT_CAPITAL = "자본금을 입력하세요. (정수)";
        public static final String INSERT_MESSAGE = "메시지를 입력하세요.";
        public static final String ALL_DONE = "필요한 항목을 전부 입력했습니다.";
        public static final String INSERT_CURRENCY_TO_ISSUE = "발행할 통화의 이름을 입력하세요. (영문 3글자 이하, 중복 불가)";
        public static final String INSERT_CANDIDATES = "후보 명단을 입력하세요. (본명, 띄어쓰기로 구분)";

        public static String NAME_TOO_LONG(int maxLength) {
            return ChatColor.RED + "이름은 " + NumberFormat.getIntegerInstance().format(maxLength) + "글자를 초과할 수 없습니다.";
        }

        public static final String ACTOR_ONLY_CREATABLE_BY_SOVEREIGNS = ChatColor.RED + "국가만 설립 가능합니다.";
        public static final String ACTOR_ONLY_CREATABLE_BY_CORPORATIONS = ChatColor.RED + "기업만 설립 가능합니다.";
        public static final String ACTOR_ONLY_CREATABLE_BY_PERSONS = ChatColor.RED + "플레이어만 설립 가능합니다.";

        public static final String NAME_TAKEN = ChatColor.RED + "이름이 이미 사용 중입니다.";

        public static final String INSUFFICIENT_CASH = ChatColor.RED + "잔고가 부족합니다.";
        public static final String INSUFFICIENT_ASSETS = ChatColor.RED + "자산이 부족합니다.";
        public static final String INSUFFICIENT_PERMISSIONS = ChatColor.RED + "권한이 부족합니다.";
        public static final String INSUFFICIENT_ARGS = ChatColor.RED + "입력이 부족합니다.";
        public static final String ACTOR_NOT_FOUND = ChatColor.RED + "대상을 찾을 수 없습니다.";

        public static final String CORPORATION_CREATED = ChatColor.GREEN + "기업이 설립되었습니다.";
        public static final String FUND_CREATED = ChatColor.GREEN + "펀드가 설립되었습니다.";
        public static final String INSTITUTION_CREATED = ChatColor.GREEN + "기관이 설립되었습니다.";
        public static final String SOVEREIGNTY_CREATED = ChatColor.GREEN + "국가가 설립되었습니다.";
        public static final String ORGANIZATION_CREATED = ChatColor.GREEN + "조직이 설립되었습니다.";

        public static final String ONLY_ONE_CENTRAL_BANK_ALLOWED_PER_SOVEREIGN = ChatColor.RED + "국가당 1개의 중앙은행만 설립 가능합니다.";

        public static final String NO_MESSAGES_RECEIVED = ChatColor.RED + "수신한 메시지가 없습니다.";

        public static final String ASSET_NOT_FOUND = ChatColor.RED + "자산을 찾지 못했습니다.";
        public static final String NO_SPACE_IN_INVENTORY = ChatColor.RED + "인벤토리에 빈 공간이 없습니다.";
        public static final String ASSET_PHYSICALIZED = ChatColor.GREEN + "자산을 실물화했습니다.";
        public static final String ASSET_DEPHYSICALIZED = ChatColor.GREEN + "자산을 가상화했습니다.";

        public static final String NO_OFFERS_RECEIVED = ChatColor.RED + "수신한 제안이 없습니디.";
        public static final String OFFER_ALREADY_SENT = ChatColor.RED + "이미 발신한 제안이 있습니다.";
        public static final String ILLEGAL_OFFER_SIGNATURE = ChatColor.RED + "빌신인 또는 수신인의 유형이 유효하지 않습니다.";
        public static final String RETIRED_FROM_EMPLOYER = ChatColor.GREEN + "사직이 완료되었습니다.";
        public static final String RETIRED_FROM_REPRESENTABLE = ChatColor.GREEN + "대표직에서 은퇴했습니다.";
        public static final String RETIRED_FROM_ORGANIZATION = ChatColor.GREEN + "조직에서 탈퇴했습니다.";
        public static final String RETIRED_FROM_FEDERAL = ChatColor.GREEN + "연방국에서 탈퇴했습니다.";
        public static final String ILLEGAL_RETIRE_SIGNATURE = ChatColor.RED + "회원 또는 조직의 유형이 유효하지 않습니다.";

        public static final String PROPERTY_OVERLAPS_ANOTHER = ChatColor.RED + "범위가 다른 부동산을 침범합니다.";
        public static final String PROPERTY_CLAIM_OVER_LIMIT = ChatColor.RED + "범위가 1회 설정한도를 초과합니다.";
        public static final String PROPERTY_CLAIMED = ChatColor.GREEN + "부동산이 설정되었습니다. 보호인을 설정하려면 /spp를 실행해주세요. 보호인 없이는 부동산이 보호되지 않습니다.";
        public static final String PROPERTY_ABANDONED = ChatColor.GREEN + "부동산을 포기했습니다.";
        public static final String PROPERTY_PROTECTOR_NOT_FOUND = ChatColor.RED + "부동산 보호인을 찾을 수 없습니다.";
        public static final String PROPERTY_PROTECTOR_CHANGED = ChatColor.GREEN + "부동산 보호인이 변경되었습니다.";
        public static final String ADDRESS_NOT_FOUND = ChatColor.RED + "주소지를 찾을 수 없습니다.";
        public static final String ADDRESS_CHANGED = ChatColor.GREEN + "주소지가 변경되었습니다.";

        public static final String ACTOR_CANNOT_HOST_VOTES = ChatColor.RED + "투표를 주최할 수 없습니다.";
        public static final String VOTE_NOT_FOUND = ChatColor.RED + "투표를 찾을 수 없습니다.";
        public static final String INVALID_VOTE_TYPE = ChatColor.RED + "유효하지 않은 투표 유형입니다.";
        public static final String CANDIDATE_NOT_FOUND = ChatColor.RED + "후보를 찾을 수 없습니다.";
        public static final String NO_REMAINING_VOTES = ChatColor.RED + "의결권이 부족합니다.";

        public static final String REPRESENTATIVE_ALREADY_NULL = ChatColor.RED + "대표자가 이미 공석입니다.";
        public static final String REPRESENTATIVE_NOT_NULL = ChatColor.RED + "대표자가 공석이 아닙니다.";
        public static final String SAME_VOTE_ALREADY_OPEN = ChatColor.RED + "같은 투표가 이미 진행 중입니다.";
        public static final String ACTOR_CANNOT_ISSUE_SHARES = ChatColor.RED + "주식을 발행할 수 없습니다.";
        public static final String ACTOR_CANNOT_HAVE_PRESIDENT = ChatColor.RED + "대통령을 선임할 수 없습니다.";
        public static final String ACTOR_NOT_LEGISLATURE = ChatColor.RED + "의회가 아닙니다.";
        public static final String VOTE_PROPOSED = ChatColor.GREEN + "투표가 시작되었습니다.";
        public static final String VOTE_CAST = ChatColor.GREEN + "의결이 완료되었습니다.";

        public static final String ACTOR_NOT_PROPERTY_PROTECTOR = ChatColor.RED + "지역보호인이 아닙니다.";
        public static final String PROPERTY_PROTECTION_FEE_CHANGED = ChatColor.GREEN + "지역보호비가 변경되었습니다.";

        public static String OWNER_OF_ADDRESS(@Nullable Actor owner, @NonNull Address address) {
            return address.format() + "의 소유주는 " + (owner != null ? owner.getName() : "익명의 소유주") + "입니다.";
        }

        public static String OWNER_NOT_FOUND = ChatColor.RED + "소유주를 찾을 수 없습니다.";

        public static String ACTOR_NOT_SOVEREIGN = ChatColor.RED + "국가가 아닙니다.";
        public static String REPRESENTATIVE_OF_LEGISLATURE_MUST_BE_ELECTED = ChatColor.RED + "입법부의 대표는 선출직입니다.";
        public static String REPRESENTATIVE_CHANGED = ChatColor.GREEN + "대표가 변경되었습니다.";

        public static String ACTOR_NOT_CURRENCY_ISSUER = ChatColor.RED + "통화를 발행할 수 없습니다.";
        public static String CURRENCY_ISSUED = ChatColor.GREEN + "통화가 발행되었습니다.";

        public static String TAX_RATE_CHANGED = ChatColor.GREEN + "세율이 변경되었습니다.";

        public static String ALREADY_MEMBER_OF_SOVEREIGN = ChatColor.RED + "이미 국가에 가입되어있습니다.";
        public static String JOINED_SOVEREIGN = ChatColor.GREEN + "국가에 가입했습니다.";

        public static String ACTOR_NOT_FACTION = ChatColor.RED + "참전이 불가능합니다.";

        public static String HOSTILITY_DECLARED = ChatColor.GREEN + "전쟁이 선포되었습니다.";
        public static String HOSTILITY_REVOKED = ChatColor.GREEN + "전쟁을 종료했습니다. 상대측에서도 종료해야 전쟁이 종결됩니다.";
        public static String CANNOT_DECLARE_WAR_ON_ONESELF = ChatColor.RED + "스스로에게 전쟁을 선포할 수 없습니다.";

        public static String HOSTILITY_INFORMATION(@NonNull Faction f1, @NonNull Faction f2) {
            return f1.getName() + " vs " + f2.getName();
        }

        public static String ADDRESS_OF_ACTOR(@NonNull Actor actor, @NonNull Address address) {
            return actor.getName() + "의 주소지: " + address.format();
        }


        public static List<String> ACTOR_INFORMATION_OUTSIDER(@NonNull Actor actor) {
            List<String> info = new ArrayList<>();

            info.add(actor.getName() + " 정보");
            info.add("유형: " + actor.getType().toString());

            return info;
        }

        public static String CASH_BALANCE(@NonNull CashStack cash) {
            return "[현금] " + NumberFormat.getIntegerInstance().format(cash.getQuantity()) + " " + cash.getAsset().getName();
        }

        public static List<String> VOTE_INFO(@NonNull Vote vote) {
            List<String> info = new ArrayList<>();

            info.add(vote.getName() + " 정보");
            info.add("후보 목록:");
            for (Candidate c : vote.getCandidates()) {
                info.add("- " + c.getName() + " (" + c.getAgenda().getDescription() + ")");
            }

            return info;
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
        public static Material searchMaterial(@NonNull String input) {
            try {
                return Material.valueOf(input);
            } catch (IllegalArgumentException e) {
                return null;
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

        @Nullable
        public static Vote searchVote(@NonNull String input, @NonNull EconomyState state) {
            for (Vote v : state.getVotes()) {
                if (v.getName().equalsIgnoreCase(input)) {
                    return v;
                }
            }

            for (Vote v : state.getVotes()) {
                if (v.getName().toLowerCase().contains(input.toLowerCase())) {
                    return v;
                }
            }

            for (Vote v : state.getVotes()) {
                if (v.getUniqueId().toString().contains(input)) {
                    return v;
                }
            }

            return null;
        }

        @Nullable
        public static Person searchPerson(@NonNull String input, @NonNull EconomyState state) {
            for (Person p : state.getPersons()) {
                if (Objects.equals(p.getName(), input)) {
                    return p;
                }
            }

            for (Person p : state.getPersons()) {
                String name = p.getName();
                if (name != null) {
                    if (name.toLowerCase().contains(input.toLowerCase())) {
                        return p;
                    }
                }
            }

            for (Person p : state.getPersons()) {
                if (p.getUniqueId().toString().contains(input)) {
                    return p;
                }
            }

            return null;
        }

        @Nullable
        public static Institutional searchInstitution(@NonNull String input, @NonNull EconomyState state) {
            for (Institutional i : state.getInstitutionals()) {
                if (Objects.equals(i.getName(), input)) {
                    return i;
                }
            }

            for (Institutional i : state.getInstitutionals()) {
                String name = i.getName();
                if (name != null) {
                    if (name.toLowerCase().contains(input.toLowerCase())) {
                        return i;
                    }
                }
            }

            for (Institutional i : state.getInstitutionals()) {
                if (i.getUniqueId().toString().contains(input)) {
                    return i;
                }
            }

            return null;
        }

        @Nullable
        public static Sovereign searchSovereign(@NonNull String input, @NonNull EconomyState state) {
            for (Sovereign s : state.getSovereigns()) {
                if (Objects.equals(s.getName(), input)) {
                    return s;
                }
            }

            for (Sovereign s : state.getSovereigns()) {
                String name = s.getName();
                if (name != null) {
                    if (name.toLowerCase().contains(input.toLowerCase())) {
                        return s;
                    }
                }
            }

            for (Sovereign s : state.getSovereigns()) {
                if (s.getUniqueId().toString().contains(input)) {
                    return s;
                }
            }

            return null;
        }

        @Nullable
        public static Faction searchFaction(@NonNull String input, @NonNull EconomyState state) {
            for (Faction f : state.getFactions()) {
                if (Objects.equals(f.getName(), input)) {
                    return f;
                }
            }

            for (Faction f : state.getFactions()) {
                String name = f.getName();
                if (name != null) {
                    if (name.toLowerCase().contains(input.toLowerCase())) {
                        return f;
                    }
                }
            }

            for (Faction f : state.getFactions()) {
                if (f.getUniqueId().toString().contains(input)) {
                    return f;
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

        public static List<String> PROPERTY_PROTECTOR_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (PropertyProtector pp : state.getProtectors()) {
                results.add(pp.getName());
            }

            return results;
        }

        public static List<String> VOTE_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (Vote vote : state.getVotes()) {
                results.add(vote.getName());
            }

            return results;
        }

        public static List<String> INSTITUTION_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (Institutional i : state.getInstitutionals()) {
                results.add(i.getName());
            }

            return results;
        }

        public static List<String> SOVEREIGN_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (Sovereign s : state.getSovereigns()) {
                results.add(s.getName());
            }

            return results;
        }

        public static List<String> FACTION_NAMES(@NonNull EconomyState state) {
            List<String> results = new ArrayList<>();

            for (Faction f : state.getFactions()) {
                results.add(f.getName());
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
