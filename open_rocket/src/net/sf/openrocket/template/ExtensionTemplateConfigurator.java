package net.sf.openrocket.template;

import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSwingSimulationExtensionConfigurator;
import javax.swing.*;

@Plugin
public class ExtensionTemplateConfigurator extends AbstractSwingSimulationExtensionConfigurator<ExtensionTemplate> {

	protected ExtensionTemplateConfigurator(Class<ExtensionTemplate> extensionClass) {
		super(extensionClass);
	}

	@Override
	protected JComponent getConfigurationComponent(ExtensionTemplate extensionTemplate, Simulation simulation, JPanel jPanel) {
		return null;
	}
}
