package net.sf.openrocket.MonteCarlo;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.util.WorldCoordinate;

public class MonteCarloSimulationExtensionListener extends AbstractSimulationListener {

    @Override
    public void endSimulation(SimulationStatus status, SimulationException exception) {

        // Prints landing position and launch site position after simulation has run
        WorldCoordinate landingPos = status.getRocketWorldPosition();
        WorldCoordinate launchPos = status.getSimulationConditions().getLaunchSite();
        System.out.println("Landing position: " + landingPos);
        System.out.println("Launch position: " + launchPos);

        // Latitude Difference
        System.out.println(landingPos.getLatitudeDeg() - launchPos.getLatitudeDeg());

    }
}
