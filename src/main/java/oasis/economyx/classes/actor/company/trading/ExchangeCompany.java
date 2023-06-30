package oasis.economyx.classes.actor.company.trading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ExchangeCompany extends Company implements Exchange {
    /**
     * Creates a new exchange
     *
     * @param uniqueId   Unique ID of this exchange
     * @param name       Name of this exchange (not unique)
     * @param stockId    ID of this exchange's stock
     * @param shareCount Initial share count
     * @param currency   Quote currency of in this exchange
     */
    public ExchangeCompany(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.markets = new ArrayList<>();
        this.marketFeeRate = 0f;
    }

    public ExchangeCompany() {
        super();

        this.markets = new ArrayList<>();
        this.marketFeeRate = 0f;
    }

    public ExchangeCompany(ExchangeCompany other) {
        super(other);
        this.markets = other.markets;
        this.marketFeeRate = other.marketFeeRate;
    }

    @NonNull
    @JsonProperty
    private final List<Marketplace> markets;

    @NonNegative
    @JsonProperty
    private float marketFeeRate;


    @Override
    @JsonIgnore
    public List<Marketplace> getMarkets() {
        return new ArrayList<>(markets);
    }

    @Override
    @JsonIgnore
    public void listMarket(@NonNull Marketplace market) throws IllegalArgumentException {
        if (getMarket(market.getAsset().getAsset()) != null) throw new IllegalArgumentException();

        markets.add(market);
    }

    @Override
    @JsonIgnore
    public void delistMarket(@NonNull Marketplace market) {
        for (Order o : market.getOrders()) {
            market.cancelOrder(o, this);
        }

        markets.remove(market);
    }

    @Override
    @JsonIgnore
    public float getMarketFeeRate() {
        return marketFeeRate;
    }

    @Override
    @JsonIgnore
    public void setMarketFeeRate(float marketFeeRate) {
        this.marketFeeRate = marketFeeRate;
    }

    @JsonProperty
    private final Type type = Type.EXCHANGE_COMPANY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
