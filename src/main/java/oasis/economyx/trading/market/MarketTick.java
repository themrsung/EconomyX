package oasis.economyx.trading.market;

/**
 * Represents a bid/ask tick in a market
 * Used to structure market data without exposing individual orders
 */
public final class MarketTick {
    public MarketTick(boolean buy, double price, long quantity) {
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
    private final double price;
    private long quantity;

    public boolean isBuy() {
        return buy;
    }

    public double getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
