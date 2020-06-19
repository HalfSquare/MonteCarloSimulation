package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.util.WorldCoordinate;

public class MonteCarloSimulationExtensionListener extends AbstractSimulationListener {
    private SimulationStatus simulationStatus;

    @Override
    public void endSimulation(SimulationStatus status, SimulationException exception) {

        // Prints landing position and launch site position after simulation has run
        WorldCoordinate landingPos = status.getRocketWorldPosition();
        WorldCoordinate launchPos = status.getSimulationConditions().getLaunchSite();
        System.out.println("Landing position: " + landingPos);
        System.out.println("Launch position: " + launchPos);

        // Latitude Difference
        System.out.println(landingPos.getLatitudeDeg() - launchPos.getLatitudeDeg());

        simulationStatus = status;
    }

    public SimulationStatus getSimulation() {
        if (simulationStatus == null) {
            return null;
        } else {
            return simulationStatus;
        }
    }
}
