package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;

public class MonteCarloSimulationExtensionListener extends AbstractSimulationListener {

  private static SimulationStatus simulationStatus;
  private final SimulationOptions simulationOptions;

  /**
   * Listener for each simulation that takes the simulation options.
   *
   * @param simulationOptions The options for the simulation.
   */
  public MonteCarloSimulationExtensionListener(SimulationOptions simulationOptions) {
    super();
    this.simulationOptions = simulationOptions;
  }

  @Override
  public void endSimulation(SimulationStatus status, SimulationException exception) {

    // Prints landing position and launch site position after simulation has run
    //        WorldCoordinate landingPos = status.getRocketWorldPosition();
    //        WorldCoordinate launchPos = status.getSimulationConditions().getLaunchSite();
    //        System.out.println("Landing position: " + landingPos);
    //        System.out.println("Launch position: " + launchPos);

    // Latitude Difference
    //        System.out.println(landingPos.getLatitudeDeg() - launchPos.getLatitudeDeg());
    simulationStatus = status;
  }

  /**
   * Gets a simulationDuple.
   *
   * @return simulationDuple containing SimulationOptions and simulationStatus of the endpoint.
   */
  public SimulationDuple getSimulation() {
    if (simulationStatus == null) {
      return null;
    } else {
      return new SimulationDuple(simulationOptions.clone(), simulationStatus);
    }
  }

  public void reset() {
    simulationStatus = null;
  }
}
