package oasis.economyx.listeners.personal;

import oasis.economyx.EconomyX;
import oasis.economyx.events.personal.employment.DirectorFiredEvent;
import oasis.economyx.events.personal.employment.DirectorHiredEvent;
import oasis.economyx.events.personal.employment.EmployeeFiredEvent;
import oasis.economyx.events.personal.employment.EmployeeHiredEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class EmploymentListener extends EconomyListener {
    public EmploymentListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDirectorHired(DirectorHiredEvent e) {
        if (e.isCancelled()) return;

        e.getEmployer().addDirector(e.getPerson());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDirectorHired(DirectorFiredEvent e) {
        if (e.isCancelled()) return;

        e.getEmployer().removeDirector(e.getPerson());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEmployeeHired(EmployeeHiredEvent e) {
        if (e.isCancelled()) return;

        e.getEmployer().addEmployee(e.getPerson());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEmployeeFired(EmployeeFiredEvent e) {
        if (e.isCancelled()) return;

        e.getEmployer().removeEmployee(e.getPerson());
    }
}
