package oasis.economyx.classes;

import oasis.economyx.actor.Actor;
import oasis.economyx.actor.corporation.Corporation;
import oasis.economyx.actor.person.Person;
import oasis.economyx.asset.stock.Stock;
import oasis.economyx.portfolio.Portfolio;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Company extends EconomicActor implements Corporation {
    public Company(
            UUID uniqueId,
            @Nullable String name,
            Portfolio portfolio,
            UUID stockId,
            long shareCount,
            List<Person> employees,
            List<Person> directors,
            @Nullable Person ceo
    ) {
        super(uniqueId, name, portfolio);
        this.stockId = stockId;
        this.shareCount = shareCount;
        this.employees = employees;
        this.directors = directors;
        this.ceo = ceo;
    }

    private final UUID stockId;
    private long shareCount;

    private final List<Person> employees;
    private final List<Person> directors;
    @Nullable
    private final Person ceo;


    @Override
    public UUID getStockId() {
        return stockId;
    }

    @Override
    public @NonNegative long getShareCount() {
        return shareCount;
    }
    @Override
    public List<Person> getEmployees() {
        return new ArrayList<>(employees);
    }

    @Override
    public void addEmployee(Person employee) {
        employees.add(employee);
    }

    @Override
    public void removeEmployee(Person employee) {
        employees.remove(employee);
    }

    @Override
    public List<Person> getDirectors() {
        return new ArrayList<>(directors);
    }

    @Override
    public void addDirector(Person director) {
        directors.add(director);
    }

    @Override
    public void removeDirector(Person director) {
        directors.remove(director);
    }

    @Override
    public @Nullable Person getRepresentative() {
        return ceo;
    }

    @Override
    public void setShareCount(@NonNegative long shares) {
        this.shareCount = shares;
    }

    @Override
    public void addShareCount(@NonNegative long delta) {
        this.shareCount += delta;
    }

    @Override
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
}
