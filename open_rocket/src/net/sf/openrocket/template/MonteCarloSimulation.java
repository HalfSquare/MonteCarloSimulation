package net.sf.openrocket.template;

import net.sf.openrocket.document.OpenRocketDocument;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.startup.Startup;

public class MonteCarloSimulation {

    /**
     * Runs a specified amount of simulations
     * @param num   Number of simulations to run
     */
    public void runSimulations(int num) throws RocketLoadException {

        // Create helper object
        OpenRocketHelper helper = new OpenRocketHelper();

        // Opens open rocket document
        OpenRocketDocument document = helper.loadORDocument("open_rocket/src/rocket-1-1-9.ork");

        // Gets first simulation from the ork file
        Simulation simulation = document.getSimulation(0);

        Rocket rocket = simulation.getRocket();

        // Change simulation options
        SimulationOptions simulationOptions = simulation.getOptions();
        simulationOptions.setWindSpeedAverage(2);
        // Time between simulation steps (A smaller time step results in a more accurate but slower simulation)
        simulationOptions.setTimeStep(0.05); // (0.05) = the 4th order simulation method

        for (int simNum = 1; simNum <= num; simNum++) {
            System.out.println("Running simulation number: "+simNum);

            //TODO: randomise the simulation conditions eg. wind speed, launch rod angle (Gaussian function)

            helper.runSimulation(simulation, new MonteCarloSimulationExtensionListener());

        }
    }

    public MonteCarloSimulation() {
        // runs 20 simulations can throw Rocket load exception if .ork document cant be loaded
        try {
            runSimulations(20);
        } catch (RocketLoadException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //TODO: Needs to start up openrocket to load motor investigate methods to load motor programmatically (Check Startup2 class)
        Startup.main(args);
        new MonteCarloSimulation();
    }


}
