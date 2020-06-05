package net.sf.openrocket.template;

import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtension;

/**
 * The actual simulation extension.
 * A new instance is created for each simulation it is attached to.
 */
class ExtensionTemplate extends AbstractSimulationExtension {

	@Override
	public void initialize(SimulationConditions simulationConditions) throws SimulationException {
		// Edit conditions and/or add simulation listeners
	}

	@Override
	public String getName() {
		return "Extension template title";
	}

	@Override
	public String getDescription() {
		return "This extension is a template";
	}
}
