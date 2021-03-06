package nz.ac.vuw.engr301.group15.montecarlo;

import ch.qos.logback.classic.Logger;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import net.sf.openrocket.document.OpenRocketDocument;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.gui.main.Splash;
import net.sf.openrocket.gui.main.SwingExceptionHandler;
import net.sf.openrocket.plugin.PluginModule;
import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.startup.Application;
import net.sf.openrocket.startup.GuiModule;
import nz.ac.vuw.engr301.group15.gui.MissionControlSettings;
import org.slf4j.LoggerFactory;

public class MonteCarloSimulation {

  private static final double ROD_ANGLE_SIGMA = 5.0;
  private static final double WIND_SPEED_SIGMA = 0.5;
  private static final double WIND_DIR_SIGMA = 5.0;
  private static final double WIND_TURB_SIGMA = 0.2;
  private static final double LAUNCH_TEMP_SIGMA = 5.0;
  private static final double LAUNCH_AIR_PRES_SIGMA = 5.0;

  private final Runnable listener;

  private boolean doRandom;

  /**
   * Runs a specified amount of Monte Carlo simulations.
   * Currently takes about 10 seconds to run 1,000 simulations
   *
   * @return the simulations ran
   */
  public ArrayList<SimulationDuple> runSimulations(
          InputStream file, MissionControlSettings settings) throws RocketLoadException {

    MissionControlSettings defaultSettings = MonteCarloSimulation.loadDefaultSettings();

    // Extract mission control setting data, setting defaults if values are empty
    double launchRodAngle = settings.getLaunchRodAngle().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchRodAngle())
            : Double.parseDouble(settings.getLaunchRodAngle());
    double launchRodLength = settings.getLaunchRodLength().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchRodLength())
            : Double.parseDouble(settings.getLaunchRodLength());
    double launchRodDir = settings.getLaunchRodDir().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchRodDir())
            : Double.parseDouble(settings.getLaunchRodDir());
    double launchAlt = settings.getLaunchAlt().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchAlt())
            : Double.parseDouble(settings.getLaunchAlt());
    double launchLat = settings.getLaunchLat().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchLat())
            : Double.parseDouble(settings.getLaunchLat());
    double launchLong = settings.getLaunchLong().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchLong())
            : Double.parseDouble(settings.getLaunchLong());
    double maxAngle = settings.getMaxAngle().equals("")
            ? Double.parseDouble(defaultSettings.getMaxAngle())
            : Double.parseDouble(settings.getMaxAngle());
    double windSpeed = settings.getWindSpeed().equals("")
            ? Double.parseDouble(defaultSettings.getWindSpeed())
            : Double.parseDouble(settings.getWindSpeed());
    double windDir = settings.getWindDir().equals("")
            ? Double.parseDouble(defaultSettings.getWindDir())
            : Double.parseDouble(settings.getWindDir());
    double windTurb = settings.getWindTurbulence().equals("")
            ? Double.parseDouble(defaultSettings.getWindTurbulence())
            : Double.parseDouble(settings.getWindTurbulence());
    double launchTemp = settings.getLaunchTemp().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchTemp())
            : Double.parseDouble(settings.getLaunchTemp());
    double launchAirPres = settings.getLaunchAirPressure().equals("")
            ? Double.parseDouble(defaultSettings.getLaunchAirPressure())
            : Double.parseDouble(settings.getLaunchAirPressure());

    // Create helper object
    OpenRocketHelper helper = new OpenRocketHelper();

    // Opens open rocket document
    OpenRocketDocument document = helper.loadOrDocument(file);

    // Gets first simulation from the ork file
    Simulation simulation = document.getSimulation(0);

    //Rocket rocket = simulation.getRocket();

    // Random has a built in Gaussian distribution function (below) which could be suitable
    Random rand = new Random();

    // Change simulation options
    SimulationOptions simulationOptions = simulation.getOptions();

    // Time between simulation steps
    // (A smaller time step results in a more accurate but slower simulation)
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

    ArrayList<SimulationDuple> simulationData = new ArrayList<>();
    MonteCarloSimulationExtensionListener simulationListener;

    char[] animationChars = new char[]{'|', '/', '-', '\\'};
    int loadingSpinIndex = 0;

    int numOfSimulations = settings.getNumSimulationsAsInteger() == 0
            ? defaultSettings.getNumSimulationsAsInteger()
            : settings.getNumSimulationsAsInteger();

    for (int simNum = 1; simNum <= numOfSimulations; simNum++) {
      // Randomize some launch conditions with Gaussian distribution
      // simulationOptions.setLaunchRodAngle((rand.nextGaussian()
      // * ROD_ANGLE_SIGMA) + launchRodAngle);
      simulationListener =
              new MonteCarloSimulationExtensionListener(simulationOptions);
      simulationListener.reset();
      if (doRandom) {
        //TODO: change wind direction randomly???
        simulationOptions.setWindSpeedAverage(
                (rand.nextGaussian() * WIND_SPEED_SIGMA) + windSpeed);
        simulationOptions.setWindTurbulenceIntensity(
                (rand.nextGaussian() * WIND_TURB_SIGMA) + windTurb);
        simulationOptions.setLaunchTemperature(
                (rand.nextGaussian() * LAUNCH_TEMP_SIGMA) + launchTemp);
        simulationOptions.setLaunchPressure(
                (rand.nextGaussian() * LAUNCH_AIR_PRES_SIGMA) + launchAirPres);
      } else {
        simulationOptions.setWindSpeedAverage(windSpeed);
        simulationOptions.setWindTurbulenceIntensity(windTurb);
        simulationOptions.setLaunchTemperature(launchTemp);
        simulationOptions.setLaunchPressure(launchAirPres);
      }



      helper.runSimulation(simulation, simulationListener);
      while (simulationListener.getSimulation() == null) {
        System.out.println("waiting");
      }

      String progress = String.format("%.2f", (simNum / (double) numOfSimulations) * 100.0);
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

  /**
   * Loads the default settings.
   *
   * @return the default settings
   */
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

  /**
   * A Monte Carlo simulation without a runnable.
   */
  public MonteCarloSimulation() {
    this(null);
    Logger logger = (Logger) LoggerFactory.getLogger("ROOT");
    logger.detachAndStopAllAppenders();
    Splash.init();
    SwingExceptionHandler exceptionHandler = new SwingExceptionHandler();
    Application.setExceptionHandler(exceptionHandler);
    exceptionHandler.registerExceptionHandler();
    GuiModule guiModule = new GuiModule();
    Module pluginModule = new PluginModule();
    Injector injector = Guice.createInjector(guiModule, pluginModule);
    Application.setInjector(injector);
    guiModule.startLoader();
  }

  /**
   * A Monte Carlo simulation with a runnable.
   */
  public MonteCarloSimulation(Runnable runnable) {
    this.doRandom = true;
    this.listener = runnable;
    Logger logger = (Logger) LoggerFactory.getLogger("ROOT");
    logger.detachAndStopAllAppenders();
    Splash.init();
    SwingExceptionHandler exceptionHandler = new SwingExceptionHandler();
    Application.setExceptionHandler(exceptionHandler);
    exceptionHandler.registerExceptionHandler();
    GuiModule guiModule = new GuiModule();
    Module pluginModule = new PluginModule();
    Injector injector = Guice.createInjector(guiModule, pluginModule);
    Application.setInjector(injector);
    guiModule.startLoader();
    System.out.println("Initialised OpenRocket");
  }

  /**
   * For testing purposes.
   *
   * @param args args
   */
  public static void main(String[] args) {
    try {
      MonteCarloSimulation mcs = new MonteCarloSimulation();
      ClassLoader classLoader = mcs.getClass().getClassLoader();
      InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
      mcs.runSimulations(rocketFile, loadDefaultSettings());
      /*
      MonteCarloSimulation mcs = new MonteCarloSimulation();
      ClassLoader classLoader = mcs.getClass().getClassLoader();
      InputStream rocketFile = classLoader.getResourceAsStream("pid_rocket.ork");
      mcs.runPITuning(rocketFile, 1);
      */
    } catch (RocketLoadException e) {
      e.printStackTrace();
    }
  }


  public boolean isDoRandom() {
    return doRandom;
  }

  public void setDoRandom(boolean doRandom) {
    this.doRandom = doRandom;
  }
}
