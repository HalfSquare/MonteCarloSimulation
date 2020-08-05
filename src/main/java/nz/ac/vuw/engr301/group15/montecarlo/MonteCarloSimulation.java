package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.document.OpenRocketDocument;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.startup.Startup;
import net.sf.openrocket.startup.Startup2;
import nz.ac.vuw.engr301.group15.gui.MissionControlSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MonteCarloSimulation {

  private static final double ROD_ANGLE_SIGMA = 5.0;
  private static final double WIND_SPEED_SIGMA = 5.0;
  private static final double WIND_DIR_SIGMA = 5.0;
  private static final double WIND_TURB_SIGMA = 5.0;
  private static final double LAUNCH_TEMP_SIGMA = 5.0;
  private static final double LAUNCH_AIR_PRES_SIGMA = 5.0;

  private final Runnable listener;

  /**
   * Runs a specified amount of Monte Carlo simulations
   * Currently takes about 10 seconds to run 1,000 simulations
   *
   * @param num Number of simulations to run
   * @return the simulations ran
   */
  public ArrayList<SimulationStatus> runSimulations(int num, File file, MissionControlSettings settings) throws RocketLoadException {

    // Extract mission control setting data, setting defaults if values are empty


    double launchRodLength = Double.parseDouble(settings.getLaunchRodLength());
    double launchRodDir = Double.parseDouble(settings.getLaunchRodDir());
    double launchAlt = Double.parseDouble(settings.getLaunchAlt());
    double launchLat = Double.parseDouble(settings.getLaunchLat());
    double launchLong = Double.parseDouble(settings.getLaunchLong());
    double maxAngle = Double.parseDouble(settings.getMaxAngle());
    double windSpeed = Double.parseDouble(settings.getWindSpeed());
    double windDir = Double.parseDouble(settings.getWindDir());
    double windTurb = Double.parseDouble(settings.getWindTurbulence());
    double launchTemp = Double.parseDouble(settings.getLaunchTemp());
    double launchAirPres = Double.parseDouble(settings.getLaunchAirPressure());

    MissionControlSettings defaultSettings = loadDefaultSettings();

    System.out.println("Yeet: " + settings.getLaunchRodLength());

    // Create helper object
    OpenRocketHelper helper = new OpenRocketHelper();

    // Opens open rocket document
    OpenRocketDocument document = helper.loadORDocument(file.getPath());

    // Gets first simulation from the ork file
    Simulation simulation = document.getSimulation(0);

    Rocket rocket = simulation.getRocket();

    // Random has a built in Gaussian distribution function (below) which could be suitable
    Random rand = new Random();

    // Change simulation options
    SimulationOptions simulationOptions = simulation.getOptions();

    // Time between simulation steps (A smaller time step results in a more accurate but slower simulation)
    simulationOptions.setTimeStep(0.05); // (0.05) = the 4th order simulation method

    // Set base mission control settings to simulation options
    //simulationOptions.setLaunchTemperature();

    ArrayList<SimulationStatus> simulationData = new ArrayList<>();
    MonteCarloSimulationExtensionListener simulationListener =  new MonteCarloSimulationExtensionListener();
    for (int simNum = 1; simNum <= num; simNum++) {


      //TODO: find which variables we want to take in from user, and which variables should be randomized

      // Randomize some launch conditions with Gaussian distribution
      simulationOptions.setWindSpeedAverage(rand.nextGaussian() + 2);
      simulationOptions.setLaunchRodAngle(rand.nextGaussian() * 45);
      simulationOptions.setLaunchTemperature(rand.nextGaussian() + 30);

      simulationListener.reset();
      helper.runSimulation(simulation, simulationListener);

      while (simulationListener.getSimulation() == null) {
        System.out.println("waiting");
      }
      simulationData.add(simulationListener.getSimulation());
      if (listener != null) {
        listener.run();
      }

    }
    return simulationData;
  }

  private MissionControlSettings loadDefaultSettings(){
    // Load in default mission control settings
    MissionControlSettings defaultSettingsMissionControl = new MissionControlSettings();
    defaultSettingsMissionControl.setLaunchRodAngle("0");
    defaultSettingsMissionControl.setLaunchRodLength("10");
    defaultSettingsMissionControl.setLaunchRodDir("0");
    defaultSettingsMissionControl.setLaunchAlt("282");
    defaultSettingsMissionControl.setLaunchLat("-41.1325");
    defaultSettingsMissionControl.setLaunchLong("175.0298");
    defaultSettingsMissionControl.setMaxAngle("0");
    defaultSettingsMissionControl.setWindSpeed("10");
    defaultSettingsMissionControl.setWindDir("5");
    defaultSettingsMissionControl.setWindTurbulence("0");
    defaultSettingsMissionControl.setLaunchTemp("30");
    defaultSettingsMissionControl.setLaunchAirPressure("2");
    defaultSettingsMissionControl.setNumSimulations("1000");

    return defaultSettingsMissionControl;
  }

  public MonteCarloSimulation() {
    this(null);
  }

  public MonteCarloSimulation(Runnable runnable) {
    this.listener = runnable;
    Startup.initializeLogging();
    Startup.initializeL10n();
    Startup2.loadMotor();
  }

  public static void main(String[] args) {
    try {
      MonteCarloSimulation mcs = new MonteCarloSimulation();
      mcs.runSimulations(5, new File ("src/main/resources/rocket-1-1-9.ork"), new MissionControlSettings());
    } catch (RocketLoadException e) {
      e.printStackTrace();
    }
  }


}
