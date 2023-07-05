package oasis.economyx.commands.create;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.company.common.Merchant;
import oasis.economyx.classes.actor.company.finance.Bank;
import oasis.economyx.classes.actor.company.finance.SecuritiesBroker;
import oasis.economyx.classes.actor.company.special.HoldingsCompany;
import oasis.economyx.classes.actor.company.special.Mercenary;
import oasis.economyx.classes.actor.company.trading.AuctionCompany;
import oasis.economyx.classes.actor.company.trading.ExchangeCompany;
import oasis.economyx.classes.actor.company.vaulting.VaultCompany;
import oasis.economyx.classes.actor.institution.monetary.CentralBank;
import oasis.economyx.classes.actor.institution.tripartite.Administration;
import oasis.economyx.classes.actor.institution.tripartite.Judiciary;
import oasis.economyx.classes.actor.institution.tripartite.Legislature;
import oasis.economyx.classes.actor.institution.warfare.Military;
import oasis.economyx.classes.actor.organization.corporate.Cartel;
import oasis.economyx.classes.actor.organization.international.Alliance;
import oasis.economyx.classes.actor.organization.personal.Party;
import oasis.economyx.classes.actor.sovereignty.federal.Empire;
import oasis.economyx.classes.actor.sovereignty.federal.Federation;
import oasis.economyx.classes.actor.sovereignty.singular.Principality;
import oasis.economyx.classes.actor.sovereignty.singular.Republic;
import oasis.economyx.classes.actor.trust.Trust;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.actor.ActorCreatedEvent;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.fund.Fund;
import oasis.economyx.interfaces.actor.organization.Organization;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

public final class CreateCommand extends EconomyCommand {
    public static final int MAX_NAME_LENGTH = 20;

    public CreateCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 2) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Type type = Type.fromInput(params[0]);

        if (type == null) {
            player.sendRawMessage(Messages.INVALID_TYPE);
            return;
        }

        String name = params[1];

        if (name.length() > MAX_NAME_LENGTH) {
            player.sendRawMessage(Messages.NAME_TOO_LONG(MAX_NAME_LENGTH));
            return;
        }

        for (Actor a : getState().getActors()) {
            if (Objects.equals(a.getName(), name)) {
                player.sendRawMessage(Messages.NAME_TAKEN);
                return;
            }
        }

        if (type.isCorporation()) {
            if (params.length < 5) {
                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                return;
            }

            long shareCount = Inputs.fromNumber(params[2]);
            if (shareCount < 0L) {
                player.sendRawMessage(Messages.INVALID_NUMBER);
                return;
            }

            Cash currency = Inputs.searchCurrency(params[3], getState());
            if (currency == null) {
                player.sendRawMessage(Messages.INVALID_CURRENCY);
                return;
            }

            long money = Inputs.fromNumber(params[4]);
            if (money < 0L) {
                player.sendRawMessage(Messages.INVALID_NUMBER);
                return;
            }

            CashStack capital = new CashStack(currency, money);
            if (!actor.getPayableAssets(getState()).contains(capital)) {
                player.sendRawMessage(Messages.INSUFFICIENT_CASH);
                return;
            }

            UUID stockId = UUID.randomUUID();

            Corporation corporation = switch (type) {
                case HOLDINGS_COMPANY -> new HoldingsCompany(UUID.randomUUID(), name, stockId, shareCount, currency);
                case BANK -> new Bank(UUID.randomUUID(), name, stockId, shareCount, currency);
                case SECURITIES_BROKER -> new SecuritiesBroker(UUID.randomUUID(), name, stockId, shareCount, currency);
                case MERCHANT -> new Merchant(UUID.randomUUID(), name, stockId, shareCount, currency);
                case MERCENARY -> new Mercenary(UUID.randomUUID(), name, stockId, shareCount, currency);
                case AUCTION_COMPANY -> new AuctionCompany(UUID.randomUUID(), name, stockId, shareCount, currency);
                case EXCHANGE_COMPANY -> new ExchangeCompany(UUID.randomUUID(), name, stockId, shareCount, currency);
                case VAULT_COMPANY -> new VaultCompany(UUID.randomUUID(), name, stockId, shareCount, currency);
                default -> null;
            };

            if (corporation == null) {
                player.sendRawMessage(Messages.UNKNOWN_ERROR);
                return;
            }

            actor.getAssets().add(new StockStack(new Stock(stockId, name), shareCount));

            Bukkit.getPluginManager().callEvent(new ActorCreatedEvent(corporation, actor, ActorCreatedEvent.Type.CREATED_BY_PLAYER));
            Bukkit.getPluginManager().callEvent(new PaymentEvent(actor, corporation, capital, PaymentEvent.Cause.ACTOR_INITIAL_CAPITAL));

            player.sendRawMessage(Messages.CORPORATION_CREATED);
            return;
        } else if (type.isFund()) {
            if (params.length < 5) {
                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                return;
            }

            long shareCount = Inputs.fromNumber(params[2]);
            if (shareCount < 0L) {
                player.sendRawMessage(Messages.INVALID_NUMBER);
                return;
            }

            Cash currency = Inputs.searchCurrency(params[3], getState());
            if (currency == null) {
                player.sendRawMessage(Messages.INVALID_CURRENCY);
                return;
            }

            long money = Inputs.fromNumber(params[4]);
            if (money < 0L) {
                player.sendRawMessage(Messages.INVALID_NUMBER);
                return;
            }

            CashStack capital = new CashStack(currency, money);
            if (!actor.getPayableAssets(getState()).contains(capital)) {
                player.sendRawMessage(Messages.INSUFFICIENT_CASH);
                return;
            }

            UUID stockId = UUID.randomUUID();

            Fund fund = switch (type) {
                case TRUST -> new Trust(UUID.randomUUID(), name, stockId, shareCount, currency);
                default -> null;
            };

            if (fund == null) {
                player.sendRawMessage(Messages.UNKNOWN_ERROR);
                return;
            }

            fund.setRepresentative(caller);

            actor.getAssets().add(new StockStack(new Stock(stockId, name), shareCount));

            Bukkit.getPluginManager().callEvent(new ActorCreatedEvent(fund, actor, ActorCreatedEvent.Type.CREATED_BY_PLAYER));
            Bukkit.getPluginManager().callEvent(new PaymentEvent(actor, fund, capital, PaymentEvent.Cause.ACTOR_INITIAL_CAPITAL));

            player.sendRawMessage(Messages.FUND_CREATED);
            return;
        } else if (type.isInstitution()) {
            if (!(actor instanceof Sovereign parent)) {
                player.sendRawMessage(Messages.ACTOR_ONLY_CREATABLE_BY_SOVEREIGNS);
                return;
            }

            if (params.length < 3) {
                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                return;
            }

            if (type == Type.CENTRAL_BANK) {
                if (!params[2].matches("^[A-Z]*$")) {
                    player.sendRawMessage(Messages.INVALID_CURRENCY);
                    return;
                }

                for (Cash currency : getState().getCurrencies()) {
                    if (currency.getName().equalsIgnoreCase(params[2])) {
                        player.sendRawMessage(Messages.NAME_TAKEN);
                        return;
                    }
                }
            }

            Cash currency = switch (type) {
                case CENTRAL_BANK -> new Cash(UUID.randomUUID(), params[2].toUpperCase());
                default -> Inputs.searchCurrency(params[2], getState());
            };

            if (currency == null) {
                player.sendRawMessage(Messages.INVALID_CURRENCY);
                return;
            }

            Institutional institution = switch (type) {
                case ADMINISTRATION -> new Administration(parent, UUID.randomUUID(), name, currency);
                case LEGISLATURE -> new Legislature(parent, UUID.randomUUID(), name, currency);
                case JUDICIARY -> new Judiciary(parent, UUID.randomUUID(), name, currency);
                case CENTRAL_BANK -> new CentralBank(parent, UUID.randomUUID(), name, currency);
                case MILITARY -> new Military(parent, UUID.randomUUID(), name, currency);
                default -> null;
            };

            if (institution == null) {
                player.sendRawMessage(Messages.UNKNOWN_ERROR);
                return;
            }

            if (institution.getType() == Actor.Type.CENTRAL_BANK) {
                for (Institutional i : parent.getInstitutions()) {
                    if (i.getType() == Actor.Type.CENTRAL_BANK) {
                        player.sendRawMessage(Messages.ONLY_ONE_CENTRAL_BANK_ALLOWED_PER_SOVEREIGN);
                        return;
                    }
                }
            }

            Bukkit.getPluginManager().callEvent(new ActorCreatedEvent(institution, actor, ActorCreatedEvent.Type.CREATED_BY_PLAYER));

            player.sendRawMessage(Messages.INSTITUTION_CREATED);
            return;
        } else if (type.isSingularSovereignty()) {
            if (!(actor instanceof Person person)) {
                player.sendRawMessage(Messages.ACTOR_ONLY_CREATABLE_BY_PERSONS);
                return;
            }

            if (params.length < 4) {
                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                return;
            }

            Cash currency = Inputs.searchCurrency(params[2], getState());
            if (currency == null) {
                player.sendRawMessage(Messages.INVALID_CURRENCY);
                return;
            }

            long money = Inputs.fromNumber(params[3]);
            if (money < 0L) {
                player.sendRawMessage(Messages.INVALID_NUMBER);
                return;
            }

            CashStack capital = new CashStack(currency, money);
            if (!actor.getPayableAssets(getState()).contains(capital)) {
                player.sendRawMessage(Messages.INSUFFICIENT_CASH);
                return;
            }

            Sovereign sovereign = switch (type) {
                case REPUBLIC -> new Republic(UUID.randomUUID(), name, currency, person);
                case PRINCIPALITY -> new Principality(UUID.randomUUID(), name, currency, person);
                default -> null;
            };

            if (sovereign == null) {
                player.sendRawMessage(Messages.UNKNOWN_ERROR);
                return;
            }

            sovereign.setRepresentative(caller);

            Bukkit.getPluginManager().callEvent(new ActorCreatedEvent(sovereign, person, ActorCreatedEvent.Type.CREATED_BY_PLAYER));
            Bukkit.getPluginManager().callEvent(new PaymentEvent(person, sovereign, capital, PaymentEvent.Cause.ACTOR_INITIAL_CAPITAL));

            player.sendRawMessage(Messages.SOVEREIGNTY_CREATED);
            return;
        } else if (type.isFederalSovereignty()) {
            if (!(actor instanceof Sovereign foundingState)) {
                player.sendRawMessage(Messages.ACTOR_ONLY_CREATABLE_BY_SOVEREIGNS);
                return;
            }

            if (params.length < 4) {
                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                return;
            }

            Cash currency = Inputs.searchCurrency(params[2], getState());
            if (currency == null) {
                player.sendRawMessage(Messages.INVALID_CURRENCY);
                return;
            }

            long money = Inputs.fromNumber(params[3]);
            if (money < 0L) {
                player.sendRawMessage(Messages.INVALID_NUMBER);
                return;
            }

            CashStack capital = new CashStack(currency, money);
            if (!actor.getPayableAssets(getState()).contains(capital)) {
                player.sendRawMessage(Messages.INSUFFICIENT_CASH);
                return;
            }

            Sovereign sovereign = switch (type) {
                case EMPIRE -> new Empire(UUID.randomUUID(), name, currency, foundingState);
                case FEDERATION -> new Federation(UUID.randomUUID(), name, currency, foundingState);
                default -> null;
            };

            if (sovereign == null) {
                player.sendRawMessage(Messages.UNKNOWN_ERROR);
                return;
            }

            Bukkit.getPluginManager().callEvent(new ActorCreatedEvent(sovereign, foundingState, ActorCreatedEvent.Type.CREATED_BY_PLAYER));
            Bukkit.getPluginManager().callEvent(new PaymentEvent(foundingState, sovereign, capital, PaymentEvent.Cause.ACTOR_INITIAL_CAPITAL));

            player.sendRawMessage(Messages.SOVEREIGNTY_CREATED);
            return;
        } else if (type.isOrganization()) {
            if (params.length < 3) {
                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                return;
            }

            Cash currency = Inputs.searchCurrency(params[2], getState());
            if (currency == null) {
                player.sendRawMessage(Messages.INVALID_CURRENCY);
                return;
            }

            switch (type) {
                case ALLIANCE -> {
                    if (!(actor instanceof Sovereign)) {
                        player.sendRawMessage(Messages.ACTOR_ONLY_CREATABLE_BY_SOVEREIGNS);
                        return;
                    }
                }
                case CARTEL -> {
                    if (!(actor instanceof Corporation)) {
                        player.sendRawMessage(Messages.ACTOR_ONLY_CREATABLE_BY_CORPORATIONS);
                        return;
                    }
                }
                case PARTY -> {
                    if (!(actor instanceof Person)) {
                        player.sendRawMessage(Messages.ACTOR_ONLY_CREATABLE_BY_PERSONS);
                        return;
                    }
                }
                default -> {
                    player.sendRawMessage(Messages.UNKNOWN_ERROR);
                    return;
                }
            }

            Organization<?> organization = switch (type) {
                case ALLIANCE -> new Alliance(UUID.randomUUID(), name, currency, (Sovereign) actor);
                case CARTEL -> new Cartel(UUID.randomUUID(), name, currency, (Corporation) actor);
                case PARTY -> new Party(UUID.randomUUID(), name, currency, (Person) actor);
                default -> null;
            };

            if (organization == null) {
                player.sendRawMessage(Messages.UNKNOWN_ERROR);
                return;
            }

            Bukkit.getPluginManager().callEvent(new ActorCreatedEvent(organization, actor, ActorCreatedEvent.Type.CREATED_BY_PLAYER));

            player.sendRawMessage(Messages.ORGANIZATION_CREATED);
            return;
        }

        player.sendRawMessage(Messages.UNKNOWN_ERROR);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            for (Type t : Type.values()) list.addAll(t.toInput());
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            // Name
            list.add(Messages.INSERT_NAME);
        } else {
            final Type type = Type.fromInput(params[0]);
            if (type == null) return;

            if (type.isCorporation() || type.isFund()) {
                if (params.length < 4) {
                    list.add(Messages.INSERT_SHARE_COUNT);
                } else if (params.length < 5) {
                    list.addAll(Lists.CURRENCY_NAMES(getState()));
                    if (!params[3].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[3].toLowerCase()));
                } else if (params.length < 6) {
                    list.add(Messages.INSERT_CAPITAL);
                } else {
                    list.add(Messages.ALL_DONE);
                }
            } else if (type.isInstitution()) {
                if (params.length < 4) {
                    switch (type) {
                        case CENTRAL_BANK -> {
                            list.add(Messages.INSERT_CURRENCY_TO_ISSUE);
                        }
                        default -> {
                            list.addAll(Lists.CURRENCY_NAMES(getState()));
                            if (!params[2].equals(""))
                                list.removeIf(s -> !s.toLowerCase().startsWith(params[2].toLowerCase()));
                        }
                    }
                } else {
                    list.add(Messages.ALL_DONE);
                }
            } else if (type.isSovereignty()) {
                if (params.length < 4) {
                    list.addAll(Lists.CURRENCY_NAMES(getState()));
                    if (!params[2].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[2].toLowerCase()));
                } else if (params.length < 5) {
                    list.add(Messages.INSERT_CAPITAL);
                } else {
                    list.add(Messages.ALL_DONE);
                }
            } else if (type.isOrganization()) {
                if (params.length < 4) {
                    list.addAll(Lists.CURRENCY_NAMES(getState()));
                    if (!params[2].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[2].toLowerCase()));
                } else {
                    list.add(Messages.ALL_DONE);
                }
            }
        }
    }

    enum Type {
        // SOVEREIGNTIES
        REPUBLIC,
        PRINCIPALITY,
        EMPIRE,
        FEDERATION,

        // INSTITUTIONS
        ADMINISTRATION,
        LEGISLATURE,
        JUDICIARY,
        CENTRAL_BANK,
        MILITARY,

        // FUNDS
        TRUST,

        // ORGANIZATIONS
        ALLIANCE,
        CARTEL,
        PARTY,

        // CORPORATIONS
        HOLDINGS_COMPANY,
        BANK,
        SECURITIES_BROKER,
        MERCHANT,
        MERCENARY,
        AUCTION_COMPANY,
        EXCHANGE_COMPANY,
        VAULT_COMPANY;

        private static final List<String> K_REPUBLIC = Arrays.asList("republic", "공화국");
        private static final List<String> K_PRINCIPALITY = Arrays.asList("principality", "공국");
        private static final List<String> K_EMPIRE = Arrays.asList("empire", "제국");
        private static final List<String> K_FEDERATION = Arrays.asList("federation", "연합국");

        private static final List<String> K_ADMINISTRATION = Arrays.asList("administration", "행정부");
        private static final List<String> K_LEGISLATURE = Arrays.asList("legislature", "congress", "senate", "parliament", "행정부", "국회", "의회");
        private static final List<String> K_JUDICIARY = Arrays.asList("judiciary", "court", "사법부", "법원");
        private static final List<String> K_MILITARY = Arrays.asList("military", "army", "airforce", "navy", "군대", "육군", "공군", "해군");
        private static final List<String> K_CENTRAL_BANK = Arrays.asList("centralbank", "cbank", "중앙은행");


        private static final List<String> K_TRUST = Arrays.asList("trust", "fund", "신탁", "펀드");

        private static final List<String> K_ALLIANCE = Arrays.asList("alliance", "연맹");
        private static final List<String> K_CARTEL = Arrays.asList("cartel", "카르텔");
        private static final List<String> K_PARTY = Arrays.asList("party", "파티", "정당");

        private static final List<String> K_HOLDINGS_COMPANY = Arrays.asList("holdingscompany", "hcompany", "지주사", "지주회사");
        private static final List<String> K_BANK = Arrays.asList("bank", "은행");
        private static final List<String> K_SECURITIES_BROKER = Arrays.asList("securitiesbroker", "brokerage", "브로커리지", "증권사");
        private static final List<String> K_MERCHANT = Arrays.asList("merchant", "소매회사");
        private static final List<String> K_MERCENARY = Arrays.asList("mercenary", "privatemilitary", "pmilitary", "pmc", "용병", "용병회사", "민간군사기업");
        private static final List<String> K_AUCTION_COMPANY = Arrays.asList("auctioneer", "auctionhouse", "auctioncompany", "경매회사", "경매소");
        private static final List<String> K_EXCHANGE_COMPANY = Arrays.asList("exchange", "거래소");
        private static final List<String> K_VAULT_COMPANY = Arrays.asList("vaulting", "vaultcompany", "vcompany", "금고업", "금고회사", "금고사");

        public List<String> toInput() {
            return switch (this) {
                case REPUBLIC -> K_REPUBLIC;
                case PRINCIPALITY -> K_PRINCIPALITY;
                case EMPIRE -> K_EMPIRE;
                case FEDERATION -> K_FEDERATION;
                case ADMINISTRATION -> K_ADMINISTRATION;
                case LEGISLATURE -> K_LEGISLATURE;
                case JUDICIARY -> K_JUDICIARY;
                case MILITARY -> K_MILITARY;
                case CENTRAL_BANK -> K_CENTRAL_BANK;
                case TRUST -> K_TRUST;
                case ALLIANCE -> K_ALLIANCE;
                case CARTEL -> K_CARTEL;
                case PARTY -> K_PARTY;
                case HOLDINGS_COMPANY -> K_HOLDINGS_COMPANY;
                case BANK -> K_BANK;
                case SECURITIES_BROKER -> K_SECURITIES_BROKER;
                case MERCHANT -> K_MERCHANT;
                case MERCENARY -> K_MERCENARY;
                case AUCTION_COMPANY -> K_AUCTION_COMPANY;
                case EXCHANGE_COMPANY -> K_EXCHANGE_COMPANY;
                case VAULT_COMPANY -> K_VAULT_COMPANY;
                default -> new ArrayList<>();
            };
        }

        @Nullable
        public static Type fromInput(@NonNull String input) {
            if (K_REPUBLIC.contains(input.toLowerCase())) return REPUBLIC;
            if (K_PRINCIPALITY.contains(input.toLowerCase())) return PRINCIPALITY;
            if (K_EMPIRE.contains(input.toLowerCase())) return EMPIRE;
            if (K_FEDERATION.contains(input.toLowerCase())) return FEDERATION;
            if (K_ADMINISTRATION.contains(input.toLowerCase())) return ADMINISTRATION;
            if (K_LEGISLATURE.contains(input.toLowerCase())) return LEGISLATURE;
            if (K_JUDICIARY.contains(input.toLowerCase())) return JUDICIARY;
            if (K_MILITARY.contains(input.toLowerCase())) return MILITARY;
            if (K_CENTRAL_BANK.contains(input.toLowerCase())) return CENTRAL_BANK;
            if (K_TRUST.contains(input.toLowerCase())) return TRUST;
            if (K_ALLIANCE.contains(input.toLowerCase())) return ALLIANCE;
            if (K_CARTEL.contains(input.toLowerCase())) return CARTEL;
            if (K_PARTY.contains(input.toLowerCase())) return PARTY;
            if (K_HOLDINGS_COMPANY.contains(input.toLowerCase())) return HOLDINGS_COMPANY;
            if (K_BANK.contains(input.toLowerCase())) return BANK;
            if (K_SECURITIES_BROKER.contains(input.toLowerCase())) return SECURITIES_BROKER;
            if (K_MERCHANT.contains(input.toLowerCase())) return MERCHANT;
            if (K_MERCENARY.contains(input.toLowerCase())) return MERCENARY;
            if (K_AUCTION_COMPANY.contains(input.toLowerCase())) return AUCTION_COMPANY;
            if (K_EXCHANGE_COMPANY.contains(input.toLowerCase())) return EXCHANGE_COMPANY;
            if (K_VAULT_COMPANY.contains(input.toLowerCase())) return VAULT_COMPANY;

            return null;
        }

        public boolean isSovereignty() {
            return isSingularSovereignty() || isFederalSovereignty();
        }

        public boolean isSingularSovereignty() {
            return switch (this) {
                case REPUBLIC, PRINCIPALITY -> true;
                default -> false;
            };
        }

        public boolean isFederalSovereignty() {
            return switch (this) {
                case EMPIRE, FEDERATION -> true;
                default -> false;
            };
        }

        public boolean isInstitution() {
            return switch (this) {
                case ADMINISTRATION, LEGISLATURE, JUDICIARY, MILITARY, CENTRAL_BANK -> true;
                default -> false;
            };
        }

        public boolean isOrganization() {
            return switch (this) {
                case ALLIANCE -> true;
                default -> false;
            };
        }

        public boolean isFund() {
            return switch (this) {
                case TRUST -> true;
                default -> false;
            };
        }

        public boolean isCorporation() {
            return switch (this) {
                case HOLDINGS_COMPANY, BANK, MERCHANT, MERCENARY, AUCTION_COMPANY, EXCHANGE_COMPANY, VAULT_COMPANY ->
                        true;
                default -> false;
            };
        }
    }
}
