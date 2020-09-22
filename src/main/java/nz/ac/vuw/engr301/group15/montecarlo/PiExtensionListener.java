package nz.ac.vuw.engr301.group15.montecarlo;

import java.util.HashMap;
import java.util.Iterator;
import net.sf.openrocket.rocketcomponent.FinSet;
import net.sf.openrocket.rocketcomponent.RocketComponent;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.util.MathUtil;

public class PiExtensionListener extends PiListener {
  private SimulationStatus simulationStatus;

  public void setProportionalValue(double pVal) {
    this.KP = pVal;
  }

  public double getProportionalValue() {
    return this.KP;
  }

  public void setIntegralValue(double iVal) {
    this.KI = iVal;
  }

  public double getIntegralValue() {
    return this.KI;
  }

  public HashMap<Double, Double> getRocketAngles() {
    return this.rocketAngle;
  }

  public void endSimulation(SimulationStatus status, SimulationException exception) {
    simulationStatus = status;
  }

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
