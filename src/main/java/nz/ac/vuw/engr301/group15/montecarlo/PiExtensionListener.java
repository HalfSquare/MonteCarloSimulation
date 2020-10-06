package nz.ac.vuw.engr301.group15.montecarlo;

import java.util.HashMap;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;

public class PiExtensionListener extends PiListener {
  private SimulationStatus simulationStatus;

  public void setProportionalValue(double pval) {
    this.kp = pval;
  }

  public double getProportionalValue() {
    return this.kp;
  }

  public void setIntegralValue(double ival) {
    this.ki = ival;
  }

  public double getIntegralValue() {
    return this.ki;
  }

  public HashMap<Double, Double> getRocketAngles() {
    return this.rocketAngle;
  }

  public void endSimulation(SimulationStatus status, SimulationException exception) {
    simulationStatus = status;
  }

  /**
   * Gets simulation status of the simulation assigned to the listener.
   *
   * @return null if simulation is still running.
   */
  public SimulationStatus getSimulation() {
    if (simulationStatus == null) {
      return null;
    } else {
      return simulationStatus;
    }
  }


  public void reset() {
    simulationStatus = null;
  }
}
