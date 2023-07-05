package oasis.economyx.state;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.company.finance.Guarantor;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.fund.Fund;
import oasis.economyx.interfaces.actor.organization.Organization;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.finance.Brokerage;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.interfaces.actor.types.finance.Credible;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.institutional.*;
import oasis.economyx.interfaces.actor.types.manufacturing.Distiller;
import oasis.economyx.interfaces.actor.types.manufacturing.Minter;
import oasis.economyx.interfaces.actor.types.manufacturing.Producer;
import oasis.economyx.interfaces.actor.types.manufacturing.Scientific;
import oasis.economyx.interfaces.actor.types.ownership.Private;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.actor.types.services.*;
import oasis.economyx.interfaces.actor.types.sovereign.Federal;
import oasis.economyx.interfaces.actor.types.trading.AuctionHouse;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.guarantee.Guarantee;
import oasis.economyx.interfaces.physical.Banknote;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.interfaces.terminal.CardTerminal;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.interfaces.voting.Candidate;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.PhysicalAsset;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.message.Message;
import oasis.economyx.types.offer.Offer;
import oasis.economyx.types.portfolio.AssetPortfolio;
import oasis.economyx.types.portfolio.Portfolio;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class EconomyXState implements EconomyState {
    /**
     * EconomyX instance
     */
    @NonNull
    private final EconomyX EX;

    @Override
    @NonNull
    public EconomyX getEX() {
        return EX;
    }

    /**
     * A list of every actor within this state
     */
    @JsonProperty
    private final List<Actor> actors;

    @Override
    public List<Actor> getActors() {
        return new ArrayList<>(actors);
    }

    @Override
    public boolean isActor(UUID uniqueId) {
        for (Actor a : getActors()) {
            if (a.getUniqueId().equals(uniqueId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public @NonNull Actor getActor(UUID uniqueId) throws IllegalArgumentException {
        for (Actor a : getActors()) {
            if (a.getUniqueId().equals(uniqueId)) {
                return a;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void addActor(@NonNull Actor actor) {
        actors.add(actor);
    }

    @Override
    public void removeActor(@NonNull Actor actor) {
        actors.remove(actor);
    }

    @Override
    public boolean isPerson(UUID uniqueId) {
        try {
            return getActor(uniqueId) instanceof Person;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public @NonNull Person getPerson(UUID uniqueId) throws IllegalArgumentException {
        Actor a = getActor(uniqueId);

        if (!(a instanceof Person)) throw new IllegalArgumentException();
        return (Person) a;
    }

    @NonNull
    private final List<PhysicalAsset> physicalizedAssets;

    @NonNull
    @Override
    public List<PhysicalAsset> getPhysicalizedAssets() {
        return new ArrayList<>(physicalizedAssets);
    }

    @Override
    public void addPhysicalizedAsset(@NonNull PhysicalAsset asset) {
        physicalizedAssets.add(asset);
    }

    @Override
    public void removePhysicalizedAsset(@NonNull PhysicalAsset asset) {
        physicalizedAssets.remove(asset);
    }

    @NonNull
    private final Portfolio burntAssets;

    @Override
    @NonNull
    public Portfolio getBurntAssets() {
        return burntAssets;
    }

    @Override
    public List<Corporation> getCorporations() {
        List<Corporation> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Corporation c) list.add(c);
        }

        return list;
    }

    @NonNull
    private final List<Message> messages;

    @NonNull
    @Override
    public List<Message> getMessages() {
        messages.sort((m1, m2) -> m1.getTime().compareTo(m2.getTime()));
        return new ArrayList<>(messages);
    }

    @Override
    public @NonNull List<Message> getMessagesByRecipient(@NonNull Actor recipient) {
        List<Message> messages = getMessages();
        messages.removeIf(m -> !Objects.equals(m.getRecipient(), recipient));
        messages.sort((m1, m2) -> m1.getTime().compareTo(m2.getTime()));

        return messages;
    }

    @Override
    public @NonNull List<Message> getMessagesBySender(@NonNull Actor sender) {
        List<Message> messages = getMessages();
        messages.removeIf(m -> !Objects.equals(m.getSender(), sender));
        messages.sort((m1, m2) -> m1.getTime().compareTo(m2.getTime()));

        return messages;
    }

    @Override
    public void addMessage(@NonNull Message message) {
        messages.add(message);
    }

    @Override
    public void removeMessage(@NonNull Message message) {
        messages.remove(message);
    }

    @NonNull
    private final List<Offer> offers;

    @Override
    public @NonNull List<Offer> getOffers() {
        return new ArrayList<>(offers);
    }

    @Override
    public @NonNull List<Offer> getOffersByRecipient(@NonNull Actor recipient) {
        List<Offer> results = getOffers();
        results.removeIf(r -> !r.getRecipient().equals(recipient));

        return results;
    }

    @Override
    public @NonNull List<Offer> getOffersBySender(@NonNull Actor sender) {
        List<Offer> results = getOffers();
        results.removeIf(r -> !r.getSender().equals(sender));

        return results;
    }

    @Override
    public void addOffer(@NonNull Offer offer) {
        offers.add(offer);
    }

    @Override
    public void removeOffer(@NonNull Offer offer) {
        offers.remove(offer);
    }

    @Override
    public List<Fund> getFunds() {
        List<Fund> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Fund f) list.add(f);
        }

        return list;
    }

    @Override
    public List<Organization<?>> getOrganizations() {
        List<Organization<?>> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Organization<?> o) list.add(o);
        }

        return list;
    }

    @Override
    public List<Person> getPersons() {
        List<Person> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Person p) list.add(p);
        }

        return list;
    }

    @Override
    public List<Employer> getEmployers() {
        List<Employer> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Employer e) list.add(e);
        }

        return list;
    }

    @Override
    public List<Banker> getBankers() {
        List<Banker> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Banker b) list.add(b);
        }

        return list;
    }

    @Override
    public List<Brokerage> getBrokerages() {
        List<Brokerage> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Brokerage b) list.add(b);
        }

        return list;
    }

    @Override
    public List<Credible> getCredibles() {
        List<Credible> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Credible c) list.add(c);
        }

        return list;
    }

    @Override
    public List<Representable> getRepresentables() {
        List<Representable> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Representable r) list.add(r);
        }

        return list;
    }

    @Override
    public List<Administrative> getAdministratives() {
        List<Administrative> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Administrative ad) list.add(ad);
        }

        return list;
    }

    @Override
    public List<Sovereign> getSovereigns() {
        List<Sovereign> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Sovereign s) list.add(s);
        }

        return list;
    }

    @Override
    public List<BanknoteIssuer> getBanknoteIssuers() {
        List<BanknoteIssuer> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof BanknoteIssuer bi) list.add(bi);
        }

        return list;
    }

    @Override
    public List<CurrencyIssuer> getCurrencyIssuers() {
        List<CurrencyIssuer> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof CurrencyIssuer ci) list.add(ci);
        }

        return list;
    }

    @Override
    public List<Institutional> getInstitutionals() {
        List<Institutional> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Institutional i) list.add(i);
        }

        return list;
    }

    @Override
    public List<InterestRateProvider> getInterestRateProviders() {
        List<InterestRateProvider> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof InterestRateProvider i) list.add(i);
        }

        return list;
    }

    @Override
    public List<Judicial> getJudicials() {
        List<Judicial> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Judicial j) list.add(j);
        }

        return list;
    }

    @Override
    public List<Legislative> getLegislatives() {
        List<Legislative> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Legislative l) list.add(l);
        }

        return list;
    }

    @Override
    public List<Minter> getBillCreators() {
        List<Minter> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Minter b) list.add(b);
        }

        return list;
    }

    @Override
    public List<Distiller> getDistillers() {
        List<Distiller> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Distiller d) list.add(d);
        }

        return list;
    }

    @Override
    public List<Producer> getProducers() {
        List<Producer> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Producer p) list.add(p);
        }

        return list;
    }

    @Override
    public List<Scientific> getScientifics() {
        List<Scientific> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Scientific s) list.add(s);
        }

        return list;
    }

    @Override
    public List<Private> getPrivates() {
        List<Private> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Private p) list.add(p);
        }

        return list;
    }

    @Override
    public List<Shared> getShareds() {
        List<Shared> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Shared s) list.add(s);
        }

        return list;
    }

    @Override
    public List<Builder> getBuilders() {
        List<Builder> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Builder b) list.add(b);
        }

        return list;
    }

    @Override
    public List<CardAcceptor> getCardAccpetors() {
        List<CardAcceptor> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof CardAcceptor c) list.add(c);
        }

        return list;
    }

    @Override
    public List<Faction> getFactions() {
        List<Faction> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Faction f) list.add(f);
        }

        return list;
    }

    @Override
    public List<Legal> getLegals() {
        List<Legal> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Legal l) list.add(l);
        }

        return list;
    }

    @Override
    public List<PropertyProtector> getProtectors() {
        List<PropertyProtector> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof PropertyProtector p) list.add(p);
        }

        return list;
    }

    @Override
    public List<VaultKeeper> getVaultKeepers() {
        List<VaultKeeper> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof VaultKeeper v) list.add(v);
        }

        return list;
    }

    @Override
    public List<Federal> getFederals() {
        List<Federal> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Federal f) list.add(f);
        }

        return list;
    }

    @Override
    public List<AuctionHouse> getAuctionHosts() {
        List<AuctionHouse> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof AuctionHouse ah) list.add(ah);
        }

        return list;
    }

    @Override
    public List<Exchange> getExchanges() {
        List<Exchange> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Exchange m) list.add(m);
        }

        return list;
    }

    @Override
    public List<CardIssuer> getCardIssuers() {
        List<CardIssuer> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof CardIssuer ci) list.add(ci);
        }

        return list;
    }

    @Override
    public List<CardTerminal> getCardTerminals() {
        List<CardTerminal> terminals = new ArrayList<>();

        for (CardAcceptor ca : getCardAccpetors()) {
            terminals.addAll(ca.getCardTerminals());
        }

        return terminals;
    }

    @Override
    public List<Democratic> getDemocratics() {
        List<Democratic> list = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Democratic d) list.add(d);
        }

        return list;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();

        for (Banker b : getBankers()) {
            accounts.addAll(b.getAccounts());
        }

        return accounts;
    }

    @Override
    public List<Guarantee> getGuarantees() {
        List<Guarantee> guarantees = new ArrayList<>();

        for (Actor a : getActors()) {
            if (a instanceof Guarantor g) {
                guarantees.addAll(g.getGuarantees());
            }
        }

        return guarantees;
    }

    @Override
    public List<Banknote> getBanknotes() {
        List<Banknote> notes = new ArrayList<>();

        for (BanknoteIssuer bi : getBanknoteIssuers()) {
            notes.addAll(bi.getIssuedBanknotes());
        }

        return notes;
    }

    @Override
    public List<Auctioneer> getAuctioneers() {
        List<Auctioneer> auctioneers = new ArrayList<>();

        for (AuctionHouse ah : getAuctionHosts()) {
            auctioneers.addAll(ah.getAuctions());
        }

        return auctioneers;
    }

    @Override
    public List<Bid> getBids() {
        List<Bid> bids = new ArrayList<>();

        for (Auctioneer a : getAuctioneers()) {
            bids.addAll(a.getBids());
        }

        return bids;
    }

    @Override
    public List<Marketplace> getMarketplaces() {
        List<Marketplace> markets = new ArrayList<>();

        for (Exchange h : getExchanges()) {
            markets.addAll(h.getMarkets());
        }

        return markets;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();

        for (Marketplace m : getMarketplaces()) {
            orders.addAll(m.getOrders());
        }

        return orders;
    }

    @Override
    public List<PriceProvider> getPriceProviders() {
        List<PriceProvider> providers = new ArrayList<>();

        for (Exchange h : getExchanges()) {
            providers.addAll(h.getMarkets());
        }

        for (AuctionHouse h : getAuctionHosts()) {
            providers.addAll(h.getAuctions());
        }

        return providers;
    }

    @Override
    public List<VaultBlock> getVaultBlocks() {
        List<VaultBlock> vaults = new ArrayList<>();

        for (VaultKeeper k : getVaultKeepers()) {
            vaults.addAll(k.getVaults());
        }

        return vaults;
    }

    @Override
    public List<Portfolio> getPortfolios() {
        List<Portfolio> portfolios = new ArrayList<>();

        for (Actor a : getActors()) {
            portfolios.add(a.getAssets());
        }

        return portfolios;
    }

    /**
     * Searching within contracts is not necessary.
     */
    @Override
    public List<AssetStack> getAssets() {
        List<AssetStack> stacks = new ArrayList<>();

        for (Portfolio p : getPortfolios()) {
            stacks.addAll(p.get());
        }

        for (PhysicalAsset pa : getPhysicalizedAssets()) {
            stacks.add(pa.getAsset());
        }

        return stacks;
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();

        for (CardIssuer ci : getCardIssuers()) {
            cards.addAll(ci.getIssuedCards());
        }

        return cards;
    }

    @Override
    public List<Vote> getVotes() {
        List<Vote> votes = new ArrayList<>();

        for (Democratic d : getDemocratics()) {
            votes.addAll(d.getOpenVotes());
        }

        return votes;
    }

    @Override
    public List<Candidate> getCandidates() {
        List<Candidate> candidates = new ArrayList<>();

        for (Vote v : getVotes()) {
            candidates.addAll(v.getCandidates());
        }

        return candidates;
    }

    @Override
    public List<Voter> getVoters() {
        List<Voter> voters = new ArrayList<>();

        for (Vote v : getVotes()) {
            voters.addAll(v.getVoters());
        }

        return voters;
    }

    @Override
    public List<Cash> getCurrencies() {
        List<Cash> currencies = new ArrayList<>();

        for (AssetStack as : getAssets()) {
            if (as instanceof CashStack cs) currencies.add(cs.getAsset());
        }

        return currencies;
    }

    /**
     * Creates an empty state
     */
    public EconomyXState(@NonNull EconomyX EX) {
        this.EX = EX;

        this.actors = new ArrayList<>();
        this.physicalizedAssets = new ArrayList<>();
        this.burntAssets = new AssetPortfolio();
        this.messages = new ArrayList<>();
        this.offers = new ArrayList<>();
    }

    private EconomyXState(@NonNull EconomyX EX, @NonNull List<Actor> actors, @NonNull List<PhysicalAsset> physicalizedAssets, @NonNull Portfolio burntAssets, @NonNull List<Message> messages, @NonNull List<Offer> offers) {
        this.EX = EX;

        this.actors = actors;
        this.physicalizedAssets = physicalizedAssets;
        this.burntAssets = burntAssets;
        this.messages = messages;
        this.offers = offers;

        for (Actor a : this.actors) {
            if (a instanceof References r) {
                r.initialize(this);
            }
        }

        for (PhysicalAsset pa : physicalizedAssets) {
            if (pa instanceof References r) {
                r.initialize(this);
            }
        }

        this.burntAssets.initialize(this);

        for (Message msg : messages) {
            msg.initialize(this);
        }

        for (Offer o : offers) {
            o.initialize(this);
        }

        for (AssetStack as : getAssets()) {
            as.initialize(this);
        }
    }

    public static final String PATH = "oasis/economy";
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
            .registerModule(new JodaModule());

    public static EconomyXState load(@NonNull EconomyX EX) {
        File path = new File(PATH);
        if (!path.exists()) {
            EX.getLogger().info("Plugin folder not found. Loading empty state.");
            return new EconomyXState(EX);
        }

        File actorsFolder = new File(PATH + "/actors");
        if (!actorsFolder.exists()) {
            EX.getLogger().info("Actor data not found. Loading empty state.");
            return new EconomyXState(EX);
        }

        File[] actorFiles = actorsFolder.listFiles();
        if (actorFiles == null) {
            EX.getLogger().info("No actor save data found. Loading empty state.");
            return new EconomyXState(EX);
        }

        List<Actor> actors = new ArrayList<>();
        List<PhysicalAsset> physicalizedAssets = new ArrayList<>();
        Portfolio burntAssets = new AssetPortfolio();
        List<Message> messages = new ArrayList<>();
        List<Offer> offers = new ArrayList<>();

        for (File f : actorFiles) {
            try {
                actors.add(mapper.readValue(f, Actor.class));
            } catch (IOException e) {
                EX.getLogger().info("Error loading actor: " + e.getMessage());
            }
        }

        File physicalizedAssetsFolder = new File(PATH + "/physicalized-assets");
        if (!physicalizedAssetsFolder.exists()) {
            EX.getLogger().info("Physicalized asset data not found. Ignoring data.");
        }

        File[] physicalizedAssetsFiles = physicalizedAssetsFolder.listFiles();
        if (physicalizedAssetsFiles == null) {
            EX.getLogger().info("No physicalized assets found. Ignoring data.");
        } else {
            for (File f : physicalizedAssetsFiles) {
                try {
                    physicalizedAssets.add(mapper.readValue(f, PhysicalAsset.class));
                } catch (IOException e) {
                    EX.getLogger().info("Error loading physicalized asset: " + e.getMessage());
                }
            }
        }

        try {
            File f = new File(PATH + "/burnt-assets.yml");
            burntAssets = mapper.readValue(f, Portfolio.class);
        } catch (IOException e) {
            burntAssets = new AssetPortfolio();
            EX.getLogger().info("Burnt asset data not found. Contract expiry may cause issues.");
        }

        File messagesFolder = new File(PATH + "/messages");
        if (!messagesFolder.exists()) {
            EX.getLogger().info("Messages folder not found. Ignoring data.");
        }

        File[] messageFiles = messagesFolder.listFiles();
        if (messageFiles == null) {
            EX.getLogger().info("No messages found. Ignoring data.");
        } else {
            for (File f : messageFiles) {
                try {
                    messages.add(mapper.readValue(f, Message.class));
                } catch (IOException e) {
                    EX.getLogger().info("Error loading message: " + e.getMessage());
                }
            }
        }

        File offersFolder = new File(PATH + "/offers");
        if (!offersFolder.exists()) {
            EX.getLogger().info("Offers folder not found. Ignoring data.");
        }

        File[] offerFiles = offersFolder.listFiles();
        if (offerFiles == null) {
            EX.getLogger().info("No offers found. Ignoring data.");
        } else {
            for (File f : offerFiles) {
                try {
                    offers.add(mapper.readValue(f, Offer.class));
                } catch (IOException e) {
                    EX.getLogger().info("Error loading offer: " + e.getMessage());
                }
            }
        }

        return new EconomyXState(EX, actors, physicalizedAssets, burntAssets, messages, offers);
    }

    @Override
    public void save() {
        File path = new File(PATH);

        if (!path.exists() && !path.mkdirs()) {
            getEX().getLogger().info("Failed to initialize plugin directory. Save halted");
            return;
        }

        File actorsFolder = new File(PATH + "/actors");
        if (!actorsFolder.exists() && !actorsFolder.mkdirs()) {
            getEX().getLogger().info("Failed to initialize actors directory. Save halted.");
            return;
        }

        try {
            FileUtils.cleanDirectory(actorsFolder);
        } catch (IOException e) {
            getEX().getLogger().info("Failed to clean actors directory. Save halted.");
            return;
        }

        for (Actor a : getActors()) {
            try {
                File f = new File(PATH + "/actors/" + a.getUniqueId().toString() + ".yml");
                mapper.writeValue(f, a);

            } catch (IOException e) {
                getEX().getLogger().info("Error saving actor " + a.getUniqueId().toString().substring(0, 10) + " (" + e.getMessage() + ")");
                getEX().getLogger().info("Save is continuing, but data is faulty. Please report this issue if you have not edited the base code.");
            }
        }

        File physicalizedAssetsFolder = new File(PATH + "/physicalized-assets");
        if (!physicalizedAssetsFolder.exists() && !physicalizedAssetsFolder.mkdirs()) {
            getEX().getLogger().info("Failed to initialize physicalized assets directory.");
            getEX().getLogger().info("Save is continuing, but data is faulty.");
        }

        try {
            FileUtils.cleanDirectory(physicalizedAssetsFolder);
        } catch (IOException e) {
            getEX().getLogger().info("Failed to clean physicalized assets directory.");
            getEX().getLogger().info("Save is continuing, but data is faulty.");
        }

        for (PhysicalAsset a : getPhysicalizedAssets()) {
            try {
                File f = new File(PATH + "/physicalized-assets/" + a.getUniqueId().toString() + ".yml");
                mapper.writeValue(f, a);
            } catch (IOException e) {
                getEX().getLogger().info("Error saving physical asset " + a.getUniqueId().toString().substring(0, 10) + " (" + e.getMessage() + ")");
                getEX().getLogger().info("Save is continuing, but data is faulty. Please report this issue if you have not edited the base code.");
            }
        }

        File burntAssetsFile = new File(PATH + "/burnt-assets.yml");
        try {
            mapper.writeValue(burntAssetsFile, getBurntAssets());
        } catch (IOException e) {
            getEX().getLogger().info("Error saving burnt assets.");
            getEX().getLogger().info("Save is continuing, but data is faulty.");
        }

        File messagesFolder = new File(PATH + "/messages");
        if (!messagesFolder.exists() && !messagesFolder.mkdirs()) {
            getEX().getLogger().info("Failed to initialize messages directory.");
            getEX().getLogger().info("Save is continuing, but data is faulty.");
        }

        // Do not clean messages directory. Messages are write-only.

        for (Message m : getMessages()) {
            try {
                File f = new File(PATH + "/messages/" + m.getUniqueId().toString() + ".yml");
                mapper.writeValue(f, m);
            } catch (IOException e) {
                getEX().getLogger().info("Error saving message " + m.getUniqueId().toString().substring(0, 10) + "(" + e.getMessage() + ")");
                getEX().getLogger().info("Save is continuing, but data is faulty. Please report this issue if you have not edited the base code.");
            }
        }

        File offersFolder = new File(PATH + "/offers");
        if (!offersFolder.exists() && !offersFolder.mkdirs()) {
            getEX().getLogger().info("Failed to initialize offers directory.");
            getEX().getLogger().info("Save is continuing, but data is faulty.");
        }

        try {
            FileUtils.cleanDirectory(offersFolder);
        } catch (IOException e) {
            getEX().getLogger().info("Failed to clean offers directory.");
            getEX().getLogger().info("Save is continuing, but data is faulty.");
        }

        for (Offer o : getOffers()) {
            try {
                File f = new File(PATH + "/offers/" + o.getUniqueId().toString() + ".yml");
                mapper.writeValue(f, o);
            } catch (IOException e) {
                getEX().getLogger().info("Error saving offer " + o.getUniqueId().toString().substring(0, 10) + "(" + e.getMessage() + ")");
                getEX().getLogger().info("Save is continuing, but data is faulty. Please report this issue if you have not edited the base code.");
            }
        }
    }
}
