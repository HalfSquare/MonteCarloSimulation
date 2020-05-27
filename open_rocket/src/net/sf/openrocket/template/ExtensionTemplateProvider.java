package net.sf.openrocket.template;

import net.sf.openrocket.plugin.Plugin;
import net.sf.openrocket.simulation.extension.AbstractSimulationExtensionProvider;
import net.sf.openrocket.simulation.extension.SimulationExtension;

@Plugin
public class ExtensionTemplateProvider extends AbstractSimulationExtensionProvider {

	protected ExtensionTemplateProvider(Class<? extends SimulationExtension> extensionClass, String... name) {
		super(extensionClass, name);
	}
}
