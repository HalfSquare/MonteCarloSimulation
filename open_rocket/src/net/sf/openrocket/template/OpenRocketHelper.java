package net.sf.openrocket.template;

import net.sf.openrocket.document.OpenRocketDocument;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.file.*;
import java.io.File;

import net.sf.openrocket.simulation.listeners.*;

public class OpenRocketHelper {

    public OpenRocketHelper() {
    }

    public OpenRocketDocument loadORDocument(String fileName) throws RocketLoadException {
        File rocketFile = new File(fileName);
        GeneralRocketLoader gen = new GeneralRocketLoader();
        return gen.load(rocketFile);
    }

    public void runSimulation(Simulation simulation, AbstractSimulationListener listener) {
        simulation.getOptions().randomizeSeed();
        try {
            simulation.simulate(listener);
        } catch (SimulationException exception) {
            exception.printStackTrace();
        }
    }

}
