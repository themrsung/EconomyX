package oasis.economyx.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import oasis.economyx.EconomyX;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.contract.Contract;
import oasis.economyx.io.data.ContractData;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.codehaus.plexus.util.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EconomyXState implements EconomyState {
    /**
     * EconomyX instance
     */
    @NonNull
    private final EconomyX EX;

    @Override
    @NonNull
    public EconomyX getEX() {
        return EX;
    }

    /**
     * A list of every actor within this state
     */
    private final List<Actor> actors;

    @Override
    public List<Actor> getActors() {
        return new ArrayList<>(actors);
    }

    @Override
    public boolean isActor(UUID uniqueId) {
        for (Actor a : getActors()) {
            if (a.getUniqueId().equals(uniqueId)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public @NonNull Actor getActor(UUID uniqueId) throws IllegalArgumentException {
        for (Actor a : getActors()) {
            if (a.getUniqueId().equals(uniqueId)) {
                return a;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void addActor(@NonNull Actor actor) {
        actors.add(actor);
    }

    @Override
    public void removeActor(@NonNull Actor actor) {
        actors.remove(actor);
    }


    /**
     * Creates an empty state
     */
    public EconomyXState(@NotNull EconomyX EX) {
        this.EX = EX;
        this.actors = new ArrayList<>();
    }

    private EconomyXState(@NotNull EconomyX EX, List<Actor> actors) {
        this.EX = EX;
        this.actors = actors;
    }

    public static String PATH = "oasis/economy";

    public static EconomyXState load(@NonNull EconomyX EX) {
        File path = new File(PATH);
        if (!path.exists()) {
            EX.getLogger().info("Plugin folder found. Loading empty state.");
            return new EconomyXState(EX);
        }

        File actorsFolder = new File(PATH + "/actors");
        if (!actorsFolder.exists()) {
            EX.getLogger().info("Actor data not found. Loading empty state.");
            return new EconomyXState(EX);
        }

        File[] actorFiles = actorsFolder.listFiles();
        if (actorFiles == null) {
            EX.getLogger().info("No actor save data found. Loading empty state.");
            return new EconomyXState(EX);
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        List<Actor> actors = new ArrayList<>();

        for (File f : actorFiles) {
            try {
                actors.add(mapper.readValue(f, Actor.class));
            } catch (IOException e) {
                EX.getLogger().info("Error loading actor: " + e.getMessage());
            }
        }

        return new EconomyXState(EX, actors);
    }

    @Override
    public void save() {
        File path = new File(PATH);

        if (!path.exists() && !path.mkdirs()) {
            getEX().getLogger().info("Failed to initialize plugin directory. Save halted");
            return;
        }

        File actorsFolder = new File(PATH + "/actors");
        if (!actorsFolder.exists() && !actorsFolder.mkdirs()) {
            getEX().getLogger().info("Failed to initialize actors directory. Save halted.");
            return;
        }

        try {
            FileUtils.cleanDirectory(actorsFolder);
        } catch (IOException e) {
            getEX().getLogger().info("Failed to clean actors directory. Save halted.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        for (Actor a : getActors()) {
            try {
                File f = new File(PATH + "/actors/" + a.getUniqueId().toString() + ".yml");
                mapper.writeValue(f, a);

            } catch (IOException e) {
                getEX().getLogger().info("Error saving actor " + a.getUniqueId().toString().substring(0, 10) + " (" + e.getMessage() + ")");
                getEX().getLogger().info("Save is continuing, but data is faulty. Please report this issue if you have not edited the base code.");
            }
        }
    }
}
