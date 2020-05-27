package net.sf.openrocket.template;

import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSwingSimulationExtensionConfigurator;
import javax.swing.*;

/**
 * Configuration dialog for the extension.
 * Can create a component GUI that can adjust extension options.
 */
@Plugin
public class ExtensionTemplateConfigurator extends AbstractSwingSimulationExtensionConfigurator<ExtensionTemplate> {

	public ExtensionTemplateConfigurator() {
		super(ExtensionTemplate.class);
	}

	@Override
	protected JComponent getConfigurationComponent(ExtensionTemplate extensionTemplate, Simulation simulation, JPanel jPanel) {
		jPanel.add(new JLabel("Template:"));
		return jPanel;
	}
}
