package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.util.ArrayList;
import net.sf.openrocket.util.WorldCoordinate;

public class MonteCarloSimulationExtensionListenerRecordPath extends AbstractSimulationListener {
  private SimulationStatus simulationStatus;

  private ArrayList<WorldCoordinate> pathPoints = new ArrayList<>();

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

  @Override
  public void postStep(SimulationStatus status) throws SimulationException {
    pathPoints.add(status.getRocketWorldPosition());
  }

  public void reset() {
    simulationStatus = null;
  }

  public ArrayList<WorldCoordinate> getPathPoints() {
    return pathPoints;
  }

  public void setPathPoints(ArrayList<WorldCoordinate> pathPoints) {
    this.pathPoints = pathPoints;
  }
}
