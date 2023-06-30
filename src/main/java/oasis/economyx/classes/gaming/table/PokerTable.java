package oasis.economyx.classes.gaming.table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.gaming.House;
import oasis.economyx.interfaces.gaming.card.Deck;
import oasis.economyx.interfaces.gaming.player.CardPlayer;
import oasis.economyx.interfaces.gaming.table.Table;
import oasis.economyx.types.asset.chip.Chip;
import oasis.economyx.types.asset.chip.ChipStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A game of Texas Hold'em
 */
public final class PokerTable implements Table {
    /**
     * The maximum number of players a table will allow.
     * Numbers over 21 will break the code.
     * 9 is recommended.
     */
    @JsonIgnore
    public static final int MAX_PLAYERS = 9;

    /**
     * Creates a new poker table.
     *
     * @param uniqueId Unique ID of this table
     * @param casino   Casino running this table
     */
    public PokerTable(@NonNull UUID uniqueId, @NonNull House casino) {
        this.uniqueId = uniqueId;
        this.casino = casino;
        this.chips = new ChipStack(casino.getIssuedChip(), 0L);

        this.dealer = Deck.getDefaultDeck();
        this.burnt = Deck.getEmptyDeck();
        this.board = Deck.getEmptyDeck();
        this.stage = Stage.SHUFFLING;
        this.players = new ArrayList<>();
    }

    public PokerTable() {
        this.uniqueId = UUID.randomUUID();
        this.casino = null;
        this.chips = new ChipStack(new Chip(), 0L);

        this.dealer = Deck.getDefaultDeck();
        this.burnt = Deck.getEmptyDeck();
        this.board = Deck.getEmptyDeck();
        this.stage = Stage.SHUFFLING;
        this.players = new ArrayList<>();
    }

    public PokerTable(PokerTable other) {
        this.uniqueId = other.uniqueId;
        this.casino = other.casino;
        this.chips = other.chips;

        this.dealer = other.dealer;
        this.burnt = other.burnt;
        this.board = other.board;
        this.stage = other.stage;
        this.players = other.players;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final House casino;

    @NonNull
    @JsonProperty
    private final ChipStack chips;

    // Transient variables
    @NonNull
    @JsonIgnore
    private final transient Deck dealer;

    @NonNull
    @JsonIgnore
    private final transient Deck burnt;

    @NonNull
    @JsonIgnore
    private final transient Deck board;

    @NonNull
    @JsonIgnore
    private final transient List<CardPlayer> players;

    @NonNull
    @JsonIgnore
    private transient Stage stage;

    @Override
    public void progressGame() {
        // Clear outed players
        players.removeIf(p -> p.getStack().getQuantity() == 0L);

        // Reset game if only one player remains
        if (players.size() < 1) {
            stage = Stage.SHUFFLING;
            return;
        } else if (players.size() > MAX_PLAYERS) {
            stage = Stage.SHUFFLING;
            return;
        }

        switch (stage) {
            case SHUFFLING -> {
                if (dealer.size() != 52) throw new RuntimeException();
                dealer.shuffle();

                if (players.size() > 1) {
                    stage = Stage.PITCHING;
                }
            }

            case PITCHING -> {
                for (int i = 0; i < 2; i++) {
                    for (CardPlayer p : getPlayers()) {
                        dealer.sendFirst(p.getHand());
                    }
                }

                // TODO Collect blinds

                stage = Stage.PRE_FLOP;
            }

            case PRE_FLOP -> {
                // TODO if (!flopPitched) {

                // Burn one card
                dealer.sendFirst(burnt);

                // Pitch flop
                for (int i = 0; i < 3; i++) {
                    dealer.sendFirst(board);
                }

                // }

                // TODO Collect bets
                stage = Stage.FLOP;
            }

            case FLOP -> {
                // TODO if (!turnPitched) {

                // Burn one card
                dealer.sendFirst(burnt);

                // Pitch turn
                dealer.sendFirst(board);

                // }

                // TODO Collect bets
                stage = Stage.TURN;
            }

            case TURN -> {
                // TODO if (!riverPitched) {

                // Burn one card
                dealer.sendFirst(burnt);

                // Pitch river
                dealer.sendFirst(board);

                // }

                // TODO Collect bets
                stage = Stage.RIVER;
            }

            case RIVER -> {
                // TODO
            }

            case SHOWDOWN -> {
                // TODO
            }
        }
    }

    @Override
    @NonNull
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @NonNull
    public House getCasino() {
        return casino;
    }

    @Override
    @NonNull
    public ChipStack getChips() {
        return chips;
    }

    @NonNull
    public Deck getBoard() {
        return board;
    }

    @NonNull
    public List<CardPlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Adds a player to this game.
     * Note that an added players cannot be removed manually.
     * Players with 0 chips in their stack will be automatically removed.
     *
     * @param player Player to add
     */
    void addPlayer(CardPlayer player) {
        players.add(player);
    }

    @NonNull
    public Stage getStage() {
        return stage;
    }

    @JsonProperty
    private final Type type = Type.POKER;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    public enum Stage {
        /**
         * When table is waiting for players
         */
        SHUFFLING,

        /**
         * When the dealer is pitching cards to players
         */
        PITCHING,
        PRE_FLOP,
        FLOP,
        TURN,
        RIVER,
        SHOWDOWN;
    }
}
