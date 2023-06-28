package oasis.economyx.trading.market;

import oasis.economyx.asset.cash.CashStack;

/**
 * Represents a bid/ask tick in a trading
 * Used to structure trading data without exposing individual orders
 */
public final class MarketTick {
    public MarketTick(boolean buy, CashStack price, long quantity) {
        this.buy = buy;
        this.price = price;
        this.quantity = quantity;
    }

    public MarketTick(MarketTick other) {
        this.buy = other.buy;
        this.price = other.price;
        this.quantity = other.quantity;
    }

    private final boolean buy;
    private final CashStack price;
    private long quantity;

    public boolean isBuy() {
        return buy;
    }

    public CashStack getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
