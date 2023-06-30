package oasis.economyx;

import oasis.economyx.listener.EconomyListener;
import oasis.economyx.listener.payment.PaymentListener;
import oasis.economyx.listener.player.PlayerJoinHandler;
import oasis.economyx.listener.vault.VaultOpenedListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.state.EconomyXState;
import oasis.economyx.tasks.EconomyTask;
import oasis.economyx.tasks.expiry.CardExpiryTask;
import oasis.economyx.tasks.expiry.ContractExpiryTask;
import oasis.economyx.tasks.gaming.CasinoProgressTask;
import oasis.economyx.tasks.payment.CreditCardSettlementTask;
import oasis.economyx.tasks.payment.RegularPaymentTask;
import oasis.economyx.tasks.server.AutoSaveTask;
import oasis.economyx.tasks.trading.AuctionTickTask;
import oasis.economyx.tasks.trading.MarketTickTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of EconomyX
 */
public final class EconomyX extends JavaPlugin {
    private EconomyState state;

    public EconomyState getState() {
        return state;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Loading EconomyX.");

        //
        // Listeners
        //

        // Payments
        registerListener(new PaymentListener(this));

        // Player
        registerListener(new PlayerJoinHandler(this));

        // Vault
        registerListener(new VaultOpenedListener(this));

        //
        // Tasks
        //

        // Expiry
        registerTask(new CardExpiryTask(this));
        registerTask(new ContractExpiryTask(this));

        // Gaming
        registerTask(new CasinoProgressTask(this));

        // Payments
        registerTask(new RegularPaymentTask(this));
        registerTask(new CreditCardSettlementTask(this));

        // Server
        registerTask(new AutoSaveTask(this));

        // Trading
        registerTask(new AuctionTickTask(this));
        registerTask(new MarketTickTask(this));

        this.state = new EconomyXState(this);

        Bukkit.getLogger().info("EconomyX loaded.");
    }

    @Override
    public void onDisable() {
        getState().save();

        Bukkit.getLogger().info("EconomyX unloaded.");
    }

    private void registerTask(EconomyTask task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, task.getDelay(), task.getInterval());
    }

    private void registerListener(EconomyListener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }
}
