package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.example.RollControlListener;

public class RollControlExtensionListener extends RollControlListener {
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
