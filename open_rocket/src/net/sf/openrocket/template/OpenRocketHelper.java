package net.sf.openrocket.template;

import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.file.*;
import java.io.File;
import net.sf.openrocket.simulation.listeners.*;
import net.sf.openrocket.simulation.*;

public class OpenRocketHelper {

    public static void main(String[] args) throws Exception {
        //Startup.main(args);
//        Simulation sim = new Simulation(new Rocket());
//        sim.getSimulationListeners().add("net.sf.openrocket.simulation.listeners.example.AirStart");
        //net.sf.openrocket.file.GeneralRocketLoader

		// Creating the openrocket objects
        AbstractSimulationListener orp = new AbstractSimulationListener();
        SimulationStatus sim = new SimulationStatus();
        SimulationConditions conditions = new SimulationConditions();
        Rocket rocket = new Rocket();

		// Load in rocket, currently breaking on line 29
        File rocketFile = new File("open_rocket/src/rocket-1-1-9.ork");
        GeneralRocketLoader gen = new GeneralRocketLoader();
        gen.load(rocketFile);
        System.out.println("help");

        // Setting simulation conditions
        conditions.setRocket(rocket);
        sim.setSimulationConditions(conditions);

        // Starting the simulation

		//MotorConfigurationModel motor = new MotorConfigurationModel();
        //sim.setMotorConfiguration();
        SimulationException ex = new SimulationException();
        sim.setSimulationTime(10);
        orp.startSimulation(sim);
        orp.endSimulation(sim, ex);
    }

}
