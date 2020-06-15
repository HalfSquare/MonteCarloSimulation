package net.sf.openrocket.template;

import net.sf.openrocket.document.OpenRocketDocument;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.startup.Startup;

import java.util.Random;

public class MonteCarloSimulation {

    /**
     * Runs a specified amount of Monte Carlo simulations
	 * Currently takes about 10 seconds to run 1,000 simulations
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

		// Random has a built in Gaussian distribution function (below) which could be suitable
		Random rand = new Random();

        // Change simulation options
        SimulationOptions simulationOptions = simulation.getOptions();
        simulationOptions.setWindSpeedAverage(2);
        // Time between simulation steps (A smaller time step results in a more accurate but slower simulation)
        simulationOptions.setTimeStep(0.05); // (0.05) = the 4th order simulation method

        for (int simNum = 1; simNum <= num; simNum++) {
            System.out.println("Running simulation number: "+simNum);

            //TODO: find which variables we want to take in from user, and which variables should be randomized

			// Randomize some launch conditions with Gaussian distribution
			simulationOptions.setWindSpeedAverage(rand.nextGaussian()+2);
			simulationOptions.setLaunchRodAngle(rand.nextGaussian()*45);
			simulationOptions.setLaunchTemperature(rand.nextGaussian()+30);

			helper.runSimulation(simulation, new MonteCarloSimulationExtensionListener());

        }
    }

    public MonteCarloSimulation() {
        // runs 20 simulations can throw Rocket load exception if .ork document cant be loaded

		//TODO: allow user to choose to import their rocket/.ork file - should this tie into GUI?
        try {
            runSimulations(1000);
        } catch (RocketLoadException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //TODO: Needs to start up openrocket to load motor investigate methods to load motor programmatically (Check Startup2 class)

		//TODO: Investigate whether there is a way to open OpenRocket in headless/noGUI mode, or close OpenRocket automatically
        Startup.main(args);
        new MonteCarloSimulation();
    }


}
