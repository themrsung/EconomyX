package oasis.economyx.classes.actor.company.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.banking.Account;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Bank extends Company implements Banker {
    /**
     * Creates a new bank
     * @param uniqueId Unique ID of this bank
     * @param name Name of this bank (not unique)
     * @param stockId ID of this bank's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public Bank(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.accounts = new ArrayList<>();
        this.interestRate = 0f;
        this.issuedCards = new ArrayList<>();
    }

    public Bank() {
        super();

        this.accounts = new ArrayList<>();
        this.interestRate = 0f;
        this.issuedCards = new ArrayList<>();
    }

    public Bank(Bank other) {
        super(other);

        this.accounts = other.accounts;
        this.interestRate = other.interestRate;
        this.issuedCards = other.issuedCards;
    }

    @JsonProperty
    private final List<Account> accounts;

    @JsonProperty
    @NonNegative
    private float interestRate;

    @NonNull
    @JsonProperty
    private final List<Card> issuedCards;

    @Override
    @JsonIgnore
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    @JsonIgnore
    public void addAccount(Account account) {
        this.accounts.add(account);
        account.onOpened(this);
    }

    @Override
    @JsonIgnore
    public void removeAccount(Account account) {
        if (this.accounts.remove(account)) account.onClosed(this);
    }

    @Override
    @JsonIgnore
    @NonNegative
    public float getInterestRate() {
        return interestRate;
    }

    @Override
    @JsonIgnore
    public void setInterestRate(@NonNegative float rate) throws IllegalArgumentException {
        if (rate < 0f) throw new IllegalArgumentException();
        this.interestRate = rate;
    }


    @Override
    public @NonNull List<Card> getIssuedCards() {
        return new ArrayList<>(issuedCards);
    }

    @Override
    public ItemStack issueCard(@NonNull Card card) {
        issuedCards.add(card);

        ItemStack physicalCard = ItemStack.builder().itemType(Card.CARD_ITEM).build();

        // TODO add enchantments

        return physicalCard;
    }

    @Override
    public void cancelCard(@NonNull Card card) {
        issuedCards.remove(card);
    }

    @JsonProperty
    private final ActorType type = ActorType.BANK;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }

}
