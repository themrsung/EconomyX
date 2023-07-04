package oasis.economyx.commands.voting;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.voting.common.ChangeNameAgenda;
import oasis.economyx.classes.voting.representable.FireRepresentativeAgenda;
import oasis.economyx.classes.voting.representable.HireRepresentativeAgenda;
import oasis.economyx.classes.voting.stock.StockIssueAgenda;
import oasis.economyx.classes.voting.stock.StockRetireAgenda;
import oasis.economyx.classes.voting.stock.StockSplitAgenda;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.commands.create.CreateCommand;
import oasis.economyx.events.voting.VoteCastEvent;
import oasis.economyx.events.voting.VoteProposedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.actor.types.institutional.Legislative;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.interfaces.voting.Candidate;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class VoteCommand extends EconomyCommand {
    private static final int VOTE_LIFETIME_DAYS = 7;

    public VoteCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (params.length < 2) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Keyword k = Keyword.fromInput(params[0]);
        Vote v = Inputs.searchVote(params[0], getState());

        if (k != null) {
            switch (k) {
                case CREATE ->  {
                    if (!(actor instanceof Democratic d)) {
                        player.sendRawMessage(Messages.ACTOR_CANNOT_HOST_VOTES);
                        return;
                    }

                    VoteType type = VoteType.fromInput(params[1]);
                    if (type == null) {
                        player.sendRawMessage(Messages.INVALID_VOTE_TYPE);
                        return;
                    }

                    Vote vote = null;

                    List<Voter> voters = d.getVoters(getState());

                    boolean isVoter = false;
                    for (Voter voter : voters) {
                        if (voter.getVoter().equals(caller)) {
                            Bukkit.getLogger().info("TTT");
                            isVoter = true;
                            break;
                        }
                    }

                    if (!isVoter) {
                        player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
                        return;
                    }

                    long totalVotes = 0L;
                    for (Voter voter : voters) {
                        totalVotes += voter.getVotes();
                    }

                    switch (type) {
                        case FIRE_REPRESENTATIVE -> {
                            if (d.getRepresentative() == null) {
                                player.sendRawMessage(Messages.REPRESENTATIVE_ALREADY_NULL);
                                return;
                            }

                            vote = Vote.getBooleanVote(
                                    UUID.randomUUID(),
                                    type.toName(d),
                                    voters,
                                    new FireRepresentativeAgenda(d),
                                    new DateTime().plusDays(VOTE_LIFETIME_DAYS),
                                    (float) 2 / 3,
                                    (long) (totalVotes * 0.5f)
                            );
                        }
                        case HIRE_REPRESENTATIVE -> {
                            if (params.length < 3) {
                                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                                return;
                            }

                            if (d.getRepresentative() != null) {
                                player.sendRawMessage(Messages.REPRESENTATIVE_NOT_NULL);
                                return;
                            }

                            Person person = Inputs.searchPerson(params[2], getState());
                            if (person == null) {
                                player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                                return;
                            }

                            vote = Vote.getBooleanVote(
                                    UUID.randomUUID(),
                                    type.toName(d),
                                    voters,
                                    new HireRepresentativeAgenda(d, person),
                                    new DateTime().plusDays(VOTE_LIFETIME_DAYS),
                                    0.5f,
                                    (long) (totalVotes * 0.25f)
                            );
                        }
                        case CHANGE_NAME -> {
                            if (params.length < 3) {
                                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                                return;
                            }

                            String name = params[2];
                            if (name.length() > CreateCommand.MAX_NAME_LENGTH) {
                                player.sendRawMessage(Messages.NAME_TOO_LONG(CreateCommand.MAX_NAME_LENGTH));
                                return;
                            }

                            for (String s : Lists.ACTOR_NAMES(getState())) {
                                if (s.equalsIgnoreCase(name)) {
                                    player.sendRawMessage(Messages.NAME_TAKEN);
                                    return;
                                }
                            }

                            vote = Vote.getBooleanVote(
                                    UUID.randomUUID(),
                                    type.toName(d),
                                    voters,
                                    new ChangeNameAgenda(d, name),
                                    new DateTime().plusDays(VOTE_LIFETIME_DAYS),
                                    0.5f,
                                    (long) (totalVotes * 0.25f)
                            );
                        }
                        case STOCK_ISSUE, STOCK_RETIRE, STOCK_SPLIT -> {
                            if (params.length < 3) {
                                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                                return;
                            }

                            if (!(d instanceof Shared s)) {
                                player.sendRawMessage(Messages.ACTOR_CANNOT_ISSUE_SHARES);
                                return;
                            }

                            long shares = Inputs.fromNumber(params[2]);
                            if (shares < 0L) {
                                player.sendRawMessage(Messages.INVALID_NUMBER);
                                return;
                            }

                            Agenda agenda = switch (type) {
                                case STOCK_ISSUE -> new StockIssueAgenda(s, shares);
                                case STOCK_RETIRE -> new StockRetireAgenda(s, shares);
                                case STOCK_SPLIT -> new StockSplitAgenda(s, shares);
                                default -> null;
                            };

                            vote = Vote.getBooleanVote(
                                    UUID.randomUUID(),
                                    type.toName(d),
                                    voters,
                                    agenda,
                                    new DateTime().plusDays(VOTE_LIFETIME_DAYS),
                                    type == VoteType.STOCK_ISSUE ? (float) 2 / 3 : 0.5f,
                                    type == VoteType.STOCK_ISSUE ? (long) (totalVotes * 0.5f) : (long) (totalVotes * 0.25f)
                            );
                        }
                        case PRESIDENTIAL_ELECTION, GENERAL_ELECTION -> {
                            if (params.length < 3) {
                                player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                                return;
                            }

                            switch (type) {
                                case PRESIDENTIAL_ELECTION -> {
                                    if (!(d instanceof Sovereign)) {
                                        player.sendRawMessage(Messages.ACTOR_CANNOT_HAVE_PRESIDENT);
                                        return;
                                    }
                                }
                                case GENERAL_ELECTION -> {
                                    if (!(d instanceof Legislative)) {
                                        player.sendRawMessage(Messages.ACTOR_NOT_LEGISLATURE);
                                        return;
                                    }
                                }
                            }

                            String[] candidateNames = Arrays.copyOfRange(params, 2, params.length);
                            List<Candidate> candidates = new ArrayList<>();

                            for (String s : candidateNames) {
                                Person person = Inputs.searchPerson(s, getState());
                                if (person != null) candidates.add(Candidate.get(s, new HireRepresentativeAgenda(d, person)));
                            }

                            if (candidates.size() == 0) {
                                player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                                return;
                            }

                            vote = Vote.getMultipleSelectionVote(
                                    UUID.randomUUID(),
                                    type.toName(d),
                                    candidates,
                                    voters,
                                    new DateTime().plusDays(VOTE_LIFETIME_DAYS),
                                    0.5f,
                                    (long) (totalVotes * 0.1f)
                            );
                        }


                    }

                    if (vote == null) {
                        player.sendRawMessage(Messages.UNKNOWN_ERROR);
                        return;
                    }

                    for (Vote existing : d.getOpenVotes()) {
                        if (existing.getName().equalsIgnoreCase(type.toName(d))) {
                            player.sendRawMessage(Messages.SAME_VOTE_ALREADY_OPEN);
                            return;
                        }
                    }

                    Bukkit.getPluginManager().callEvent(new VoteProposedEvent(d, vote));
                    player.sendRawMessage(Messages.VOTE_PROPOSED);
                }
                case INFO -> {
                    Vote vote = Inputs.searchVote(params[1], getState());

                    if (vote == null) {
                        player.sendRawMessage(Messages.VOTE_NOT_FOUND);
                        return;
                    }

                    for (String s : Messages.VOTE_INFO(vote)) player.sendRawMessage(s);
                }
                default -> {
                    player.sendRawMessage(Messages.INVALID_KEYWORD);
                }
            }
        } else if (v != null) {
            if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
                player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
                return;
            }

            Candidate candidate = null;

            for (Candidate c : v.getCandidates()) {
                if (c.getName().equalsIgnoreCase(params[1])) {
                    candidate = c;
                    break;
                }
            }

            if (candidate == null) {
                player.sendRawMessage(Messages.CANDIDATE_NOT_FOUND);
                return;
            }

            Voter voter = null;

            for (Voter vt : v.getVoters()) {
                if (vt.getVoter().equals(actor)) {
                    voter = vt;
                    break;
                }
            }

            if (voter == null) {
                player.sendRawMessage(Messages.NO_REMAINING_VOTES);
                return;
            }

            Bukkit.getPluginManager().callEvent(new VoteCastEvent(v, voter, candidate, voter.getVotes()));
            player.sendRawMessage(Messages.VOTE_CAST);
        } else {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
        }
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Keyword.CREATE.toInput());
            list.addAll(Keyword.INFO.toInput());
            list.addAll(Lists.VOTE_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else {
            Keyword k = Keyword.fromInput(params[0]);
            Vote v = Inputs.searchVote(params[0], getState());

            if (params.length < 3) {
                if (k != null) {
                    switch (k) {
                        case CREATE -> {
                            for (VoteType vt : VoteType.values()) list.addAll(vt.toInput());
                        }
                        case INFO -> {
                            list.addAll(Lists.VOTE_NAMES(getState()));
                        }
                    }
                } else {
                    if (v != null) {
                        for (Candidate c : v.getCandidates()) {
                            list.add(c.getName());
                        }
                    }
                }

                if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().contains(params[1].toLowerCase()));
            } else if (params.length < 4) {
                if (k != null) {
                    switch (k) {
                        case CREATE -> {
                            VoteType vt = VoteType.fromInput(params[1]);
                            if (vt != null) {
                                switch (vt) {
                                    case HIRE_REPRESENTATIVE -> list.addAll(Lists.ACTOR_NAMES(getState()));
                                    case CHANGE_NAME -> list.add(Messages.INSERT_NAME);
                                    case DIVIDEND -> list.addAll(Lists.ASSET_NAMES(getState()));
                                    case STOCK_ISSUE, STOCK_RETIRE, STOCK_SPLIT -> list.add(Messages.INSERT_NUMBER);
                                    case PRESIDENTIAL_ELECTION, GENERAL_ELECTION -> list.add(Messages.INSERT_CANDIDATES);
                                    default -> list.add(Messages.ALL_DONE);

                                }
                            }
                        }
                        case INFO -> {
                            list.addAll(Lists.VOTE_NAMES(getState()));
                        }
                    }

                    if (!params[2].equals("")) list.removeIf(s -> !s.toLowerCase().contains(params[2].toLowerCase()));
                } else {
                    list.add(Messages.ALL_DONE);
                }
            } else {
                VoteType vt = VoteType.fromInput(params[1]);
                if (vt == null) {
                    list.add(Messages.ALL_DONE);
                } else {
                    switch (vt) {
                        case PRESIDENTIAL_ELECTION, GENERAL_ELECTION -> list.add(Messages.INSERT_CANDIDATES);
                        default -> list.add(Messages.ALL_DONE);
                    }
                }
            }
        }
    }

    enum VoteType {
        CHANGE_NAME,
        FIRE_REPRESENTATIVE,
        HIRE_REPRESENTATIVE,
        DIVIDEND,
        STOCK_ISSUE,
        STOCK_RETIRE,
        STOCK_SPLIT,
        PRESIDENTIAL_ELECTION,
        GENERAL_ELECTION;

        @NonNull
        public String toName(@NonNull Democratic host) {
            return switch (this) {
                case CHANGE_NAME -> host.getName() + "_명칭변경";
                case FIRE_REPRESENTATIVE -> host.getName() + "_대표해임";
                case HIRE_REPRESENTATIVE -> host.getName() + "_대표선임";
                case DIVIDEND -> host.getName() + "_배당";
                case STOCK_ISSUE -> host.getName() + "_신주발행";
                case STOCK_RETIRE -> host.getName() + "_자사주소각";
                case STOCK_SPLIT -> host.getName() + "_주식분할";
                case PRESIDENTIAL_ELECTION -> host.getName() + "_대선";
                case GENERAL_ELECTION -> host.getName() + "_총선";
            };
        }

        public boolean isBoolean() {
            return switch (this) {
                case PRESIDENTIAL_ELECTION, GENERAL_ELECTION -> false;
                default -> true;
            };
        }

        private static final List<String> K_CHANGE_NAME = Arrays.asList("change_name", "사명변경");
        private static final List<String> K_FIRE_REPRESENTATIVE = Arrays.asList("fire_representative", "대표해임");
        private static final List<String> K_HIRE_REPRESENTATIVE = Arrays.asList("hire_representative", "대표선임");
        private static final List<String> K_DIVIDEND = Arrays.asList("dividend", "배당");
        private static final List<String> K_STOCK_ISSUE = Arrays.asList("stock_issue", "주식발행");
        private static final List<String> K_STOCK_RETIRE = Arrays.asList("stock_retire", "자사주소각");
        private static final List<String> K_STOCK_SPLIT = Arrays.asList("stock_split", "주식분할");
        private static final List<String> K_PRESIDENTIAL_ELECTION = Arrays.asList("presidential_election", "대선", "대통령선거");
        private static final List<String> K_GENERAL_ELECTION = Arrays.asList("general_election", "총선", "의장선거");

        @NonNull
        public List<String> toInput() {
            return switch (this) {
                case CHANGE_NAME -> K_CHANGE_NAME;
                case FIRE_REPRESENTATIVE -> K_FIRE_REPRESENTATIVE;
                case HIRE_REPRESENTATIVE -> K_HIRE_REPRESENTATIVE;
                case DIVIDEND -> K_DIVIDEND;
                case STOCK_ISSUE -> K_STOCK_ISSUE;
                case STOCK_RETIRE -> K_STOCK_RETIRE;
                case STOCK_SPLIT -> K_STOCK_SPLIT;
                case PRESIDENTIAL_ELECTION -> K_PRESIDENTIAL_ELECTION;
                case GENERAL_ELECTION -> K_GENERAL_ELECTION;
            };
        }

        @Nullable
        public static VoteType fromInput(@NonNull String input) {
            if (K_CHANGE_NAME.contains(input.toLowerCase())) return CHANGE_NAME;
            if (K_FIRE_REPRESENTATIVE.contains(input.toLowerCase())) return FIRE_REPRESENTATIVE;
            if (K_HIRE_REPRESENTATIVE.contains(input.toLowerCase())) return HIRE_REPRESENTATIVE;
            if (K_DIVIDEND.contains(input.toLowerCase())) return DIVIDEND;
            if (K_STOCK_ISSUE.contains(input.toLowerCase())) return STOCK_ISSUE;
            if (K_STOCK_RETIRE.contains(input.toLowerCase())) return STOCK_RETIRE;
            if (K_STOCK_SPLIT.contains(input.toLowerCase())) return STOCK_SPLIT;
            if (K_PRESIDENTIAL_ELECTION.contains(input.toLowerCase())) return PRESIDENTIAL_ELECTION;
            if (K_GENERAL_ELECTION.contains(input.toLowerCase())) return GENERAL_ELECTION;

            return null;
        }

    }
}
