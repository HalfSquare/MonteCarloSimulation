package nz.ac.vuw.engr301.group15.montecarlo;

import java.io.InputStream;
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

  //private static final double ROD_ANGLE_SIGMA = 5.0;
  private static final double WIND_SPEED_SIGMA = 0.5;
  private static final double WIND_DIR_SIGMA = 5.0;
  private static final double WIND_TURB_SIGMA = 0.2;
  private static final double LAUNCH_TEMP_SIGMA = 5.0;
  private static final double LAUNCH_AIR_PRES_SIGMA = 5.0;

  private final Runnable listener;

  /**
   * Runs a specified amount of Monte Carlo simulations
   * Currently takes about 10 seconds to run 1,000 simulations
   *
   * @return the simulations ran
   */
  public ArrayList<SimulationStatus> runSimulations(InputStream file, MissionControlSettings settings) throws RocketLoadException {
    MissionControlSettings defaultSettings = loadDefaultSettings();
    // Extract mission control setting data, setting defaults if values are empty

    double numOfSimulations = settings.getNumSimulations().equals("") ? Double.parseDouble(defaultSettings.getNumSimulations()) : Double.parseDouble(settings.getNumSimulations());
    double launchRodAngle = settings.getLaunchRodAngle().equals("") ? Double.parseDouble(defaultSettings.getLaunchRodAngle()) : Double.parseDouble(settings.getLaunchRodAngle());
    double launchRodLength = settings.getLaunchRodLength().equals("") ? Double.parseDouble(defaultSettings.getLaunchRodLength()) : Double.parseDouble(settings.getLaunchRodLength());
    double launchRodDir = settings.getLaunchRodDir().equals("") ? Double.parseDouble(defaultSettings.getLaunchRodDir()) : Double.parseDouble(settings.getLaunchRodDir());
    double launchAlt = settings.getLaunchAlt().equals("") ? Double.parseDouble(defaultSettings.getLaunchAlt()) : Double.parseDouble(settings.getLaunchAlt());
    double launchLat = settings.getLaunchLat().equals("") ? Double.parseDouble(defaultSettings.getLaunchLat()) : Double.parseDouble(settings.getLaunchLat());
    double launchLong = settings.getLaunchLong().equals("") ? Double.parseDouble(defaultSettings.getLaunchLong()) : Double.parseDouble(settings.getLaunchLong());
    double maxAngle = settings.getMaxAngle().equals("") ? Double.parseDouble(defaultSettings.getMaxAngle()) : Double.parseDouble(settings.getMaxAngle());
    double windSpeed = settings.getWindSpeed().equals("") ? Double.parseDouble(defaultSettings.getWindSpeed()) : Double.parseDouble(settings.getWindSpeed());
    double windDir = settings.getWindDir().equals("") ? Double.parseDouble(defaultSettings.getWindDir()) : Double.parseDouble(settings.getWindDir());
    double windTurb = settings.getWindTurbulence().equals("") ? Double.parseDouble(defaultSettings.getWindTurbulence()) : Double.parseDouble(settings.getWindTurbulence());
    double launchTemp = settings.getLaunchTemp().equals("") ? Double.parseDouble(defaultSettings.getLaunchTemp()) : Double.parseDouble(settings.getLaunchTemp());
    double launchAirPres = settings.getLaunchAirPressure().equals("") ? Double.parseDouble(defaultSettings.getLaunchAirPressure()) : Double.parseDouble(settings.getLaunchAirPressure());

    // Create helper object
    OpenRocketHelper helper = new OpenRocketHelper();

    // Opens open rocket document
    OpenRocketDocument document = helper.loadORDocument(file);

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
		simulationOptions.setLaunchRodAngle(launchRodAngle);
		simulationOptions.setLaunchRodLength(launchRodLength);
		simulationOptions.setLaunchRodDirection(launchRodDir);
		simulationOptions.setLaunchAltitude(launchAlt);
		simulationOptions.setLaunchLatitude(launchLat);
		simulationOptions.setLaunchLongitude(launchLong);
		simulationOptions.setMaximumStepAngle(maxAngle);
		simulationOptions.setWindSpeedAverage(windSpeed);
		simulationOptions.setWindTurbulenceIntensity(windTurb);
    simulationOptions.setLaunchTemperature(launchTemp);
    simulationOptions.setLaunchPressure(launchAirPres);

    ArrayList<SimulationStatus> simulationData = new ArrayList<>();
    MonteCarloSimulationExtensionListener simulationListener =  new MonteCarloSimulationExtensionListener();

    char[] animationChars = new char[]{'|', '/', '-', '\\'};
    int loadingSpinIndex = 0;

    for (int simNum = 1; simNum <= numOfSimulations; simNum++) {
      // Randomize some launch conditions with Gaussian distribution
			//simulationOptions.setLaunchRodAngle((rand.nextGaussian() * ROD_ANGLE_SIGMA) + launchRodAngle);
      simulationOptions.setWindSpeedAverage((rand.nextGaussian() * WIND_SPEED_SIGMA) + windSpeed);
      simulationOptions.setWindTurbulenceIntensity((rand.nextGaussian() * WIND_TURB_SIGMA) + windTurb);
      simulationOptions.setLaunchTemperature((rand.nextGaussian() * LAUNCH_TEMP_SIGMA) + launchTemp);
      simulationOptions.setLaunchPressure((rand.nextGaussian() * LAUNCH_AIR_PRES_SIGMA) + launchAirPres);

      simulationListener.reset();
      helper.runSimulation(simulation, simulationListener);

      while (simulationListener.getSimulation() == null) {
        System.out.println("waiting");
      }
      String progress = String.format("%.2f", (simNum/numOfSimulations)*100.0);
      System.out.print("Simulating: " + progress + "% " + animationChars[loadingSpinIndex] + "\r");
      loadingSpinIndex = loadingSpinIndex == 3 ? 0 : loadingSpinIndex + 1;
      simulationData.add(simulationListener.getSimulation());
      if (listener != null) {
        listener.run();
      }

    }
    System.out.println("Simulating: Done!          ");
    System.out.println("Simulations finished");
    return simulationData;
  }

  public static MissionControlSettings loadDefaultSettings() {
    // Load in default mission control settings
    MissionControlSettings defaultSettingsMissionControl = new MissionControlSettings();
    defaultSettingsMissionControl.setLaunchRodAngle("0.0");
    defaultSettingsMissionControl.setLaunchRodLength("0.2");
    defaultSettingsMissionControl.setLaunchRodDir("0.0");
    defaultSettingsMissionControl.setLaunchAlt("159.0");
    defaultSettingsMissionControl.setLaunchLat("-41.1283");
    defaultSettingsMissionControl.setLaunchLong("175.0202");
    defaultSettingsMissionControl.setMaxAngle("0.017453292519943295");
    defaultSettingsMissionControl.setWindSpeed("6.0");
    defaultSettingsMissionControl.setWindDir("0.0");
    defaultSettingsMissionControl.setWindTurbulence("0.1");
    defaultSettingsMissionControl.setLaunchTemp("284.15");
    defaultSettingsMissionControl.setLaunchAirPressure("1010.0");
    defaultSettingsMissionControl.setNumSimulations("1000");

    return defaultSettingsMissionControl;
  }

  public MonteCarloSimulation() {
    this(null);
    Startup.initializeLogging();
    Startup.initializeL10n();
    Startup2.loadMotor();
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
      ClassLoader classLoader = mcs.getClass().getClassLoader();
      InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
      mcs.runSimulations(rocketFile, loadDefaultSettings());
    } catch (RocketLoadException e) {
      e.printStackTrace();
    }
  }


}
