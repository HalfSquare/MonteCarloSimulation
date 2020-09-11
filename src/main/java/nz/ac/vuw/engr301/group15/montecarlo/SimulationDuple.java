package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.simulation.SimulationStatus;

import java.util.ArrayList;

public class SimulationDuple {
  private SimulationOptions simulationOptions;
  private SimulationStatus simulationStatus;

  public SimulationDuple(SimulationOptions simulationOptions, SimulationStatus simulationStatus) {
    this.simulationOptions = simulationOptions;
    this.simulationStatus = simulationStatus;
  }

  public SimulationOptions getSimulationOptions() {
    return simulationOptions;
  }

  public void setSimulationOptions(SimulationOptions simulationOptions) {
    this.simulationOptions = simulationOptions;
  }

  public SimulationStatus getSimulationStatus() {
    return simulationStatus;
  }

  public void setSimulationStatus(SimulationStatus simulationStatus) {
    this.simulationStatus = simulationStatus;
  }

  public static ArrayList<SimulationStatus> getStatuses(ArrayList<SimulationDuple> simulationDuples) {
    ArrayList<SimulationStatus> simulationStatuses = new ArrayList<>();

    for (SimulationDuple simulationDuple : simulationDuples) {
      simulationStatuses.add(simulationDuple.getSimulationStatus());
    }

    return simulationStatuses;
  }

  public static ArrayList<SimulationOptions> getOptions(ArrayList<SimulationDuple> simulationDuples) {
    ArrayList<SimulationOptions> simulationOptions = new ArrayList<>();

    for (SimulationDuple simulationDuple : simulationDuples) {
      simulationOptions.add(simulationDuple.getSimulationOptions());
    }

    return simulationOptions;
  }
}
