package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.util.ArrayList;
import net.sf.openrocket.util.WorldCoordinate;

public class MonteCarloSimulationExtensionListenerRecordPath extends AbstractSimulationListener {

  private final ArrayList<WorldCoordinate> pathPoints = new ArrayList<>();

  @Override
  public void postStep(SimulationStatus status) {
    pathPoints.add(status.getRocketWorldPosition());
  }

  public ArrayList<WorldCoordinate> getPathPoints() {
    return pathPoints;
  }
}
