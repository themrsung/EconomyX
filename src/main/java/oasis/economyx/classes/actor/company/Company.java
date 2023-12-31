package oasis.economyx.classes.actor.company;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.EconomicActor;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The instantiable class of Corporation
 */
public abstract class Company extends EconomicActor implements Corporation {
    public Company(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name);
        this.currency = currency;

        this.stockId = stockId;
        this.shareCount = shareCount;

        this.employees = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.ceo = null;

        this.employeePay = new CashStack(currency, 0L);
        this.directorPay = new CashStack(currency, 0L);
        this.ceoPay = new CashStack(currency, 0L);

        this.openVotes = new ArrayList<>();
    }

    public Company() {
        super();

        this.currency = null;

        this.stockId = UUID.randomUUID();
        this.shareCount = 0L;
        this.employees = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.ceo = null;

        this.employeePay = null;
        this.directorPay = null;
        this.ceoPay = null;

        this.openVotes = new ArrayList<>();
    }

    public Company(Company other) {
        super(other);
        this.currency = other.currency;

        this.stockId = other.stockId;
        this.shareCount = other.shareCount;
        this.employees = other.employees;
        this.directors = other.directors;
        this.ceo = other.ceo;

        this.employeePay = other.employeePay;
        this.directorPay = other.directorPay;
        this.ceoPay = other.ceoPay;

        this.openVotes = other.openVotes;
    }

    @JsonProperty
    private final Cash currency;

    @JsonProperty
    private final UUID stockId;

    @JsonProperty
    private long shareCount;

    @JsonProperty
    @JsonIdentityReference
    private final List<Person> employees;

    @JsonProperty
    @JsonIdentityReference
    private final List<Person> directors;

    @Nullable
    @JsonProperty
    @JsonIdentityReference
    private Person ceo;

    @NonNull
    @JsonProperty
    private final List<Vote> openVotes;

    @Override
    @JsonIgnore
    public Cash getCurrency() {
        return currency;
    }

    @Override
    @JsonIgnore
    public @NonNull UUID getStockId() {
        return stockId;
    }

    @Override
    @JsonIgnore
    public @NonNegative long getShareCount() {
        return shareCount;
    }

    @Override
    @JsonIgnore
    public List<Person> getEmployees() {
        return new ArrayList<>(employees);
    }

    @Override
    @JsonIgnore
    public void addEmployee(Person employee) {
        employees.add(employee);
    }

    @Override
    @JsonIgnore
    public void removeEmployee(Person employee) {
        employees.remove(employee);
    }

    @Override
    @JsonIgnore
    public List<Person> getDirectors() {
        return new ArrayList<>(directors);
    }

    @Override
    @JsonIgnore
    public void addDirector(Person director) {
        directors.add(director);
    }

    @Override
    @JsonIgnore
    public void removeDirector(Person director) {
        directors.remove(director);
    }

    @Override
    @JsonIgnore
    public @Nullable Person getRepresentative() {
        return ceo;
    }

    @Override
    @JsonIgnore
    public void setRepresentative(@Nullable Person representative) {
        this.ceo = representative;
    }

    @Override
    @JsonIgnore
    public void setShareCount(@NonNegative long shares) {
        this.shareCount = shares;
    }

    @Override
    @JsonIgnore
    public void addShareCount(@NonNegative long delta) {
        this.shareCount += delta;
    }

    @Override
    @JsonIgnore
    public void reduceShareCount(@NonNegative long delta) throws IllegalArgumentException {
        if (shareCount - delta < 1) throw new IllegalArgumentException();
        this.shareCount -= delta;
    }

    @NonNull
    @JsonProperty
    private CashStack ceoPay;

    @Override
    @JsonIgnore
    public @NonNull CashStack getRepresentativePay() {
        return new CashStack(ceoPay);
    }

    @Override
    @JsonIgnore
    public void setRepresentativePay(@NonNull CashStack pay) {
        this.ceoPay = pay;
    }

    @NonNull
    @JsonProperty
    private CashStack directorPay;

    @NonNull
    @Override
    @JsonIgnore
    public CashStack getDirectorPay() {
        return new CashStack(directorPay);
    }

    @Override
    @JsonIgnore
    public void setDirectorPay(@NonNull CashStack directorPay) {
        this.directorPay = directorPay;
    }

    @NonNull
    @JsonProperty
    private CashStack employeePay;

    @NonNull
    @Override
    @JsonIgnore
    public CashStack getEmployeePay() {
        return new CashStack(employeePay);
    }

    @Override
    @JsonIgnore
    public void setEmployeePay(@NonNull CashStack employeePay) {
        this.employeePay = employeePay;
    }

    @Override
    @JsonIgnore
    public @NonNull List<Vote> getOpenVotes() {
        return new ArrayList<>(openVotes);
    }

    @Override
    @JsonIgnore
    public void openVote(@NonNull Vote vote) {
        this.openVotes.add(vote);
    }

    @Override
    @JsonIgnore
    public void cleanVotes() {
        openVotes.removeIf(v -> v.getExpiry().isBeforeNow());
        openVotes.removeIf(v -> v.getCandidates().size() == 0);
    }

    @Override
    @JsonIgnore
    public List<Actor> getShareholders(EconomyState state) {
        List<Actor> holders = new ArrayList<>();

        for (Actor a : state.getActors()) {
            if (a.getAssets().has(new Stock(stockId))) {
                holders.add(a);
            }
        }

        holders.sort((h1, h2) -> {
            long c1 = Objects.requireNonNull(h1.getAssets().get(new Stock(stockId))).getQuantity();
            long c2 = Objects.requireNonNull(h2.getAssets().get(new Stock(stockId))).getQuantity();

            return Long.compare(c2, c1);
        });

        return holders;
    }

    @Override
    @JsonIgnore
    public List<Actor> getMajorityShareholders(EconomyState state) {
        List<Actor> holders = new ArrayList<>();

        long highestShareCount = -1L;

        // List is already sorted
        for (Actor a : getShareholders(state)) {

            // Deduct self owned shares
            if (!a.equals(this)) {

                long q = Objects.requireNonNull(a.getAssets().get(new Stock(stockId))).getQuantity();
                if (q > highestShareCount) {
                    // First majority shareholder
                    highestShareCount = q;
                    holders.add(a);
                } else if (q == highestShareCount) {
                    // Equal shares as first majority shareholder
                    holders.add(a);
                } else {
                    // Not a majority shareholder; break
                    break;
                }

            }
        }

        return holders;
    }

    @Override
    @JsonIgnore
    public List<Voter> getVoters(@NonNull EconomyState state) {
        List<Voter> voters = new ArrayList<>();

        for (Actor a : getShareholders(state)) {
            Stock stock = new Stock(getStockId());
            AssetStack shares = a.getAssets().get(stock);
            if (shares instanceof StockStack ss) {
                voters.add(Voter.get(a, ss.getQuantity()));
            }
        }

        return voters;
    }

    @Override
    @JsonIgnore
    public void initialize(@NonNull EconomyState state) {
        List<Person> employees = getEmployees();
        this.employees.clear();

        List<Person> directors = getDirectors();
        this.directors.clear();

        for (Person orig : state.getPersons()) {
            for (Person ref : employees) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    this.employees.add(orig);
                }
            }

            for (Person ref : directors) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    this.directors.add(orig);
                }
            }
        }

        if (ceo != null) {
            for (Person p : state.getPersons()) {
                if (p.getUniqueId().equals(ceo.getUniqueId())) {
                    ceo = p;
                    break;
                }
            }
        }

        for (Vote v : getOpenVotes()) {
            v.initialize(state);
        }
    }
}
