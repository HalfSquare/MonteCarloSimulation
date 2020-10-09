package nz.ac.vuw.engr301.group15.montecarlo;

import java.util.HashMap;
import java.util.Iterator;
import net.sf.openrocket.aerodynamics.FlightConditions;
import net.sf.openrocket.rocketcomponent.FinSet;
import net.sf.openrocket.rocketcomponent.RocketComponent;
import net.sf.openrocket.simulation.FlightDataType;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;
import net.sf.openrocket.unit.UnitGroup;
import net.sf.openrocket.util.MathUtil;

public class PiListener extends AbstractSimulationListener {
  private static final String CONTROL_FIN_NAME = "CONTROL";
  private static final FlightDataType FIN_CANT_TYPE;
  private static final double START_TIME = 0.5D;
  private static final double SETPOINT = 0.0D;
  private static final double TURNRATE = 0.17453292519943295D;
  private static final double MAX_ANGLE = 0.2617993877991494D;
  public double kp = 0.007D;
  public double ki = 0.2D;
  private double rollrate;
  private double prevTime = 0.0D;
  private double intState = 0.0D;
  private double finPosition = 0.0D;
  HashMap<Double, Double> rocketAngle = new HashMap<>();

  public PiListener() {
  }

  public FlightConditions postFlightConditions(SimulationStatus status,
                                               FlightConditions flightConditions) {
    this.rollrate = flightConditions.getRollRate();
    return null;
  }

  public void postStep(SimulationStatus status) throws SimulationException {
    if (status.getSimulationTime() < 0.5D) {
      this.prevTime = status.getSimulationTime();
    } else {
      FinSet finset = null;

      for (RocketComponent c : status.getConfiguration()) {
        if (c instanceof FinSet && c.getName().equals("CONTROL")) {
          finset = (FinSet) c;
          break;
        }
      }

      if (finset == null) {
        throw new SimulationException("A fin set with name 'CONTROL' was not found");
      } else {
        double deltaT = status.getSimulationTime() - this.prevTime;
        this.prevTime = status.getSimulationTime();
        double error = 0.0D - this.rollrate;
        double p = this.kp * error;
        this.intState += error * deltaT;
        double i = this.ki * this.intState;
        double value = p + i;
        if (Math.abs(value) > 0.2617993877991494D) {
          System.err.printf("Attempting to set angle %.1f at t=%.3f, clamping.%n",
              value * 180.0D / 3.141592653589793D, status.getSimulationTime());
          rocketAngle.put(status.getSimulationTime(), value * 180.0D / 3.141592653589793D);
          value = MathUtil.clamp(value, -0.2617993877991494D, 0.2617993877991494D);
        }

        if (this.finPosition < value) {
          this.finPosition = Math.min(this.finPosition + 0.17453292519943295D * deltaT, value);
        } else {
          this.finPosition = Math.max(this.finPosition - 0.17453292519943295D * deltaT, value);
        }

        finset.setCantAngle(this.finPosition);
        status.getFlightData().setValue(FIN_CANT_TYPE, this.finPosition);
      }
    }
  }

  static {
    FIN_CANT_TYPE = FlightDataType.getType("Control fin cant", "CFC", UnitGroup.UNITS_ANGLE);
  }
}

