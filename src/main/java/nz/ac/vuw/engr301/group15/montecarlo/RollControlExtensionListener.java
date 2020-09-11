package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.example.RollControlListener;

public class RollControlExtensionListener extends RollControlListener {
	private SimulationStatus simulationStatus;

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
