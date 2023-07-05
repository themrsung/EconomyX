package oasis.economyx.events.payment;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A payment events denotes the one-way flow of an asset stack from one holder to another
 */
public final class PaymentEvent extends EconomyEvent {
    /**
     * Creates a new payment event
     *
     * @param sender    Actor to take asset from
     * @param recipient Actor to give asset to
     * @param asset     Asset to transfer
     * @param cause     Cause of this payment: {@link Cause}
     */
    public PaymentEvent(@NonNull Actor sender, @NonNull Actor recipient, @NonNull AssetStack asset, @NonNull Cause cause) {
        super();

        this.sender = sender;
        this.recipient = recipient;
        this.asset = asset;
        this.cause = cause;
    }

    @NonNull
    private final Actor sender;
    @NonNull
    private final Actor recipient;
    @NonNull
    private final AssetStack asset;
    @NonNull
    private final Cause cause;

    /**
     * Gets the actor sending this payment.
     *
     * @return Sender
     */
    @NonNull
    public Actor getSender() {
        return sender;
    }

    /**
     * Gets the actor receiving this payment.
     *
     * @return Recipient
     */
    @NonNull
    public Actor getRecipient() {
        return recipient;
    }

    /**
     * Gets the asset being transferred.
     *
     * @return Asset
     */
    @NonNull
    public AssetStack getAsset() {
        return asset;
    }

    /**
     * Gets the type of asset being transferred.
     *
     * @return Asset type
     */
    public Asset.Type getAssetType() {
        return getAsset().getType();
    }

    /**
     * Gets the cause of this event.
     *
     * @return Cause
     */
    public Cause getCause() {
        return cause;
    }

    /**
     * Cause of a payment
     */
    public enum Cause {
        /**
         * When a bank pays interest.
         */
        INTEREST_PAYMENT,

        /**
         * When an employer pays salaries.
         */
        SALARY_PAYMENT,

        /**
         * When a representable actor pays its representative.
         */
        REPRESENTATIVE_PAYMENT,

        /**
         * When an actor deposits funds into a bank.
         */
        BANK_DEPOSIT,

        /**
         * When an actor withdraws funds from a bank.
         */
        BANK_WITHDRAWAL,

        /**
         * When a credit card is used.
         * Settlement happens between the card's issuer and the seller.
         */
        CREDIT_CARD_PAYMENT,

        /**
         * When the balance of a credit card is settled.
         * Happens between the cardholder and the issuer.
         */
        CREDIT_CARD_SETTLEMENT,

        /**
         * When a debit card is used.
         * Payment happens after holder withdraws from their balance, then goes to seller.
         */
        DEBIT_CARD_PAYMENT,

        /**
         * Maintenance fee for vaults.
         */
        VAULT_MAINTENANCE_FEE,

        /**
         * When a non-perpetual forward expires.
         */
        FORWARD_EXPIRED,

        /**
         * When a non-perpetual note expires.
         */
        NOTE_EXPIRED,

        /**
         * When an option is exercised.
         */
        OPTION_EXERCISED,

        /**
         * When a swap agreement is settled.
         */
        SWAP_SETTLED,

        /**
         * When dividends are paid to shareholders.
         */
        DIVIDEND_PAYMENT,

        /**
         * The settlement of a fulfilled order.
         */
        ORDER_SETTLEMENT,

        /**
         * The fee paid to brokerages by order senders.
         */
        BROKERAGE_FEE,

        /**
         * The fee paid to exchanges by brokerages.
         */
        MARKET_FEE,

        /**
         * The initial capital deposited on actor creation.
         */
        ACTOR_INITIAL_CAPITAL,

        /**
         * Transfer of cash using /pay.
         */
        PAY_COMMAND,

        /**
         * Transfer of asset using /sendasset.
         */
        SEND_COMMAND,

        /**
         * When a property is protected.
         */
        PROPERTY_PROTECTION_FEE,

        /**
         * Tax on salaries
         */
        INCOME_TAX,

        /**
         * Tax on interest
         */
        INTEREST_TAX,

        /**
         * Tax on dividends
         */
        DIVIDEND_TAX;
    }
}
