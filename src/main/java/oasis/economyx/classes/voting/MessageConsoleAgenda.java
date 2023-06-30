package oasis.economyx.classes.voting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.voting.Agenda;
import org.bukkit.Bukkit;

public class MessageConsoleAgenda implements Agenda {
    @Override
    public void run() {
        Bukkit.getLogger().info("yes");
    }

    @JsonProperty
    private final Agenda.Type type = Type.MESSAGE_CONSOLE;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }
}
