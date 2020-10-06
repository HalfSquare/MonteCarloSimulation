package nz.ac.vuw.engr301.group15.gui;

import com.orsoncharts.data.xyz.XYZDataset;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.openrocket.aerodynamics.WarningSet;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.models.wind.WindModel;
import net.sf.openrocket.simulation.SimulationConditions;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.Map;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import nz.ac.vuw.engr301.group15.montecarlo.SimulationDuple;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;

public class Gui extends JFrame {

  private SettingsWindow settingsWindow = null;
  private SimulationWindow simulationWindow = null;
  private GraphWindow graphWindow = null;
  private MapWindow mapWindow = null;

  private JFileChooser fileChooser = null;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";
  public static final String MAP = "MAP";

  public File rocketModelFile;
  public File missionControlFile;
  public MissionControlSettings settingsMissionControl;

  public static final int NUM_ATTR = 13;
  private ArrayList<SimulationDuple> data;
  public int numberOfClusters = 3;
  private XYZDataset<String> dataset3d;


  public enum GraphType {
    TWOD, FLIGHTPATH
  }

  public static class UserState {
    public static boolean showGui = true;
    public static String csvImportPath = "";
    public static String orkImportPath = "";
    public static String exportPath = "";
  }

  /**
   * Creates a window that runs monte carlo simulations.
   */
  public Gui() {

    // If null, the user has chose not to import custom files and defaults should be used
    rocketModelFile = null;
    missionControlFile = null;
    settingsMissionControl = null;

    if (UserState.orkImportPath.length() > 0) {
      rocketModelFile = new File(UserState.orkImportPath);
      if (!rocketModelFile.exists()) {
        System.out.println("Could not find ork import path of "
            + rocketModelFile.getAbsolutePath());
        System.exit(1);
      }
    }

    if (UserState.showGui) {
      this.data = new ArrayList<>();

      this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      this.setSize(800, 500);
      this.setLocationRelativeTo(null);

      settingsWindow = new SettingsWindow();
      simulationWindow = new SimulationWindow();
      graphWindow = new GraphWindow();
      mapWindow = new MapWindow();

      fileChooser = new JFileChooser();

      settingsWindow.doUiStuff();
      simulationWindow.doUiStuff();
      graphWindow.doUiStuff();

      settingsWindow.setNumSim("1000");
      settingsWindow.setNumClusters(numberOfClusters);

      loadCsvFile();

      setState(SETTINGS);

      this.setVisible(true);
    } else {
      loadCsvFile();
      SimulationRunner simulationRunner = new SimulationRunner();
      simulationRunner.start();
    }
  }

  private void loadCsvFile() {
    if (UserState.csvImportPath.length() > 0) {
      File csvFile = new File(UserState.csvImportPath);
      if (csvFile.exists()) {
        loadMissionControlData(csvFile);
      } else {
        System.out.println("Could not find csv import path of " + csvFile.getAbsolutePath());
        System.exit(1);
      }
    }
  }

  private void startSettings() {
    settingsWindow.setImportCsvButton(e -> openFileManager());
    settingsWindow.setImportOrkButton(e -> openFileManagerOrk());
    settingsWindow.setStartButtonListener(e -> setState(SIMULATION));
    this.add(settingsWindow.getRootPanel());
  }

  private void startSimulation() {
    // Simulation Window
    this.add(simulationWindow.getRootPanel());
    simulationWindow.resetBar();
    simulationWindow.setBar1Max(settingsMissionControl.getNumSimulationsAsInteger());

    // Simulation stuff
    SimulationRunner runner = new SimulationRunner(() -> {
      compute3dData();
      setState(GRAPH);
    });

    runner.start();
  }

  /**
   * Start map.
   */
  public void startMap() {
    this.add(mapWindow.getRootPanel());
    mapWindow.setBackButton(e -> setState(GRAPH));
    try {
      mapWindow.setMapImage(Map.createMap());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void compute3dData() {
    String filePath = Gui.savePointsAsCsv(Gui.createList(data));
    numberOfClusters = settingsWindow.getNumClusters();

    simulationWindow.setBar2Max(5);
    Set<LatLongBean> clusters = KmeansClustering.calculateClusters(
        filePath, numberOfClusters, simulationWindow::uptickBar2);

    this.dataset3d = GraphCreator.create3dDataset(data, clusters);
  }

  public SettingsWindow getSettingsWindow() {
    return settingsWindow;
  }

  public SimulationWindow getSimulationWindow() {
    return simulationWindow;
  }

  public GraphWindow getGraphWindow() {
    return graphWindow;
  }


  private void startGraph() {
    this.add(graphWindow.getRootPanel());
    graphWindow.resetGraphPanel(); // resets the graph panel and clears previous graph
    GraphCreator g = new GraphCreator(graphWindow, data, dataset3d);
    ChartPanel chartPanel = g.createGraph(numberOfClusters);

    graphWindow.setReRunButtonListener(e -> setState(SETTINGS));
    graphWindow.setGraphTypeComboBoxListener(e ->
        setState(GRAPH)); // redraws the graph if combobox was selected
    graphWindow.setSaveImageToFileButton(e -> saveGraphAsImage(chartPanel));
    graphWindow.setCsvButtonListener(e -> saveSettingsAsCsv());
    graphWindow.setSavePointsAsCsvButton(e -> savePointsAsCsv(createList(data)));
    graphWindow.setSaveSimulationStatsToCsvButton(e -> saveStatsToCsv(createStatsList(data)));
    //createTable();

  }

  /**
   * This creates a list of all the longitude and latitude points, separated by a comma. After each
   * set of points, a new line is created
   *
   * @return list of all the points
   */
  public static ArrayList<String> createList(ArrayList<SimulationDuple> data) {
    ArrayList<String> pointList = new ArrayList<>();

    //Adding in the column names
    pointList.add("Longitude");
    pointList.add(",");
    pointList.add("Latitude");
    pointList.add("\n");

    //Reading the points into an ArrayList
    for (SimulationStatus longAndLat : SimulationDuple.getStatuses(data)) {
      WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
      double x = landingPos.getLongitudeDeg();
      double y = landingPos.getLatitudeDeg();
      pointList.add(String.valueOf(x));
      pointList.add(",");
      pointList.add(String.valueOf(y));
      pointList.add("\n");
    }
    return pointList;
  }

  /**
   * This creates a list of all the longitude and latitude points, separated by a comma. After each
   * set of points, a new line is created
   *
   * @return list of all the points
   */
  public static ArrayList<String> createStatsList(ArrayList<SimulationDuple> data) {
    ArrayList<String> statsList = new ArrayList<>();

    //Adding in the column names
    statsList.add(
        "Landing Position Longitude,Landing Position Latitude,Landing Position Altitude,"
        + "Simulation Time,Motor Ignited,Lift Off,Launch Rod Cleared,Tumbling,Launch Rod Angle,"
        + "Launch Rod Direction,Warning Set,Max Alt Time,Effective Launch Rod Length");
    statsList.add("\n");
    //Reading the points into an ArrayList
    for (SimulationStatus c : SimulationDuple.getStatuses(data)) {
      WorldCoordinate landingPos = c.getRocketWorldPosition();
      SimulationConditions conditions = c.getSimulationConditions();
      WarningSet warningSet = c.getWarnings();
      statsList.add(String.valueOf(landingPos.getLongitudeDeg()) + ','); // longitude
      statsList.add(String.valueOf(landingPos.getLatitudeDeg()) + ','); // latitude
      statsList.add(String.valueOf(landingPos.getAltitude()) + ','); // altitude
      statsList.add(String.valueOf(c.getSimulationTime()) + ','); // simulation time
      statsList.add(String.valueOf(c.isMotorIgnited()) + ','); // lift off
      statsList.add(String.valueOf(c.isLiftoff()) + ','); // lift off
      statsList.add(String.valueOf(c.isLaunchRodCleared()) + ','); // launch rod cleared
      statsList.add(String.valueOf(c.isTumbling()) + ','); // launch rod cleared
      statsList.add(String.valueOf(conditions.getLaunchRodAngle()) + ','); // launch rod angle
      statsList.add(String.valueOf(
              conditions.getLaunchRodDirection()) + ','); // launch rod direction
      statsList.add(String.valueOf(warningSet.toString()) + ','); // warning set
      statsList.add(String.valueOf(c.getMaxAlt()) + ','); // max alt time
      statsList.add(String.valueOf(
              c.getEffectiveLaunchRodLength()) + ','); // effective launch rod length
      statsList.add("\n");
    }
    return statsList;
  }

  /**
   * This saves all the simulation stats to a CSV file.
   *
   * @param statsList the list of stats
   * @return filepath
   */
  public static String saveStatsToCsv(ArrayList<String> statsList) {
    try {
      File file = new File("simulationStats.csv");
      PrintWriter pw = new PrintWriter(file);
      // Reading everything into a string
      StringBuilder sb = new StringBuilder();
      for (String s : statsList) {
        sb.append(s);
      }

      // Writing to the print writer
      pw.write(sb.toString());
      pw.close();
      return file.getAbsolutePath();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * This saves all the points to a CSV file.
   *
   * @return filepath
   */
  public static String savePointsAsCsv(ArrayList<String> list) {
    File file = null;
    try {
      if (UserState.exportPath.length() > 0) {
        String filePath = System.getProperty("user.home") + UserState.exportPath;
        file = new File(filePath, "points.csv");
      } else {
        file = new File("points.csv");
      }
      PrintWriter pw = new PrintWriter(file);
      // Reading everything into a string
      StringBuilder sb = new StringBuilder();
      for (String s : list) {
        sb.append(s);
      }

      // Writing to the print writer
      pw.write(sb.toString());
      pw.close();
      System.out.println("Exported csv to: " + file.getAbsolutePath());
      return file.getAbsolutePath();
    } catch (FileNotFoundException e) {
      System.out.println("Invalid file export path: " + file.getAbsolutePath());
      return null;
    }
  }

  /**
   * This will open a fileChooser to save the simulation settings as a CSV.
   */
  private void saveSettingsAsCsv() {
    JFileChooser j = new JFileChooser();
    j.showSaveDialog(null);
    writeMissionControlSettings(j.getSelectedFile());
  }

  /**
   * This will write the current simulation settings to a CSV file.
   *
   * @param file being created.
   */
  public void writeMissionControlSettings(File file) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      MissionControlSettings s = settingsMissionControl;
      writer.write("launchRodAngle,launchRodLength,launchRodDir,launchAlt,launchLat,"
          + "launchLong,maxAngle,windSpeed"
          + ",windDir,windTurbulence,launchTemp,launchAirPressure,numSimulations\n");
      writer.write(s.getLaunchRodAngle() + ","
          + s.getLaunchRodLength() + "," + s.getLaunchRodDir() + ","
          + s.getLaunchAlt() + "," + s.getLaunchLat() + ","
          + s.getLaunchLong() + "," + s.getMaxAngle() + ","
          + s.getWindSpeed() + "," + s.getWindDir() + ","
          + s.getWindTurbulence() + "," + s.getLaunchTemp() + ","
          + s.getLaunchAirPressure() + "," + s.getNumSimulations());
      writer.close();
    } catch (Exception ex) {
      System.out.println("Uh oh! " + ex);
    }

  }

  /**
   * This opens up a fileChooser to open up a CSV file.
   */
  private void openFileManager() {
    JFileChooser j = new JFileChooser();
    // Filter for CSV files only
    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv", "CSV");
    j.setFileFilter(filter);
    j.showSaveDialog(null);
    missionControlFile = j.getSelectedFile();
    // If a valid file has been given, parse & load data from the file
    if (j.getSelectedFile() != null) {
      loadMissionControlData(j.getSelectedFile());
    }
  }

  /**
   * This opens up a fileChooser to open up an ORK file (OpenRocket model rocket file).
   */
  private void openFileManagerOrk() {
    JFileChooser j = new JFileChooser();
    //Filter for ORK files only
    FileNameExtensionFilter filter = new FileNameExtensionFilter("ORK files", "ork", "ORK");
    j.setFileFilter(filter);
    j.showSaveDialog(null);
    rocketModelFile = j.getSelectedFile();
  }


  /**
   * Method to read and load the mission control data from a CSV into a bean.
   *
   * @param file CSV file with mission control data, should follow the given template.
   */
  public void loadMissionControlData(File file) {
    MissionControlSettings settings = new MissionControlSettings();

    // Attempt to read data
    try {
      Scanner sc = new Scanner(file);
      String[][] data = new String[2][NUM_ATTR];
      String name;
      String value;

      // Read data into 2D array, splitting at the commas (0 is data names, 1 is data values)
      for (int i = 0; i < 2; i++) {
        if (sc.hasNext()) {
          data[i] = sc.nextLine().split(",");
        }
      }

      // Read data from 2D array into the bean,
      // using a switch so that attribute ordering does not matter
      for (int i = 0; i < NUM_ATTR; i++) {
        name = data[0][i];
        value = data[1][i];
        switch (name) {
          case "numSimulations":
            settings.setNumSimulations(value);

            if (Integer.parseInt(value) > 0) {
              if (UserState.showGui) {
                settingsWindow.setNumSim(value);
              }
            } else {
              if (UserState.showGui) {
                settingsMissionControl.setErrorsFound(true);
                JOptionPane.showMessageDialog(null,
                    "Enter a simulation number larger than 0",
                    "Following error found",
                    JOptionPane.ERROR_MESSAGE);
              } else {
                settingsMissionControl.setErrorsFound(true);
                System.out.print("ERROR: Invalid simulation number.");
                settingsWindow.setNumSim("0");
              }
            }
            break;
          case "launchRodAngle":
            settings.setLaunchRodAngle(value);
            break;
          case "launchRodLength":
            settings.setLaunchRodLength(value);
            break;
          case "launchRodDir":
            settings.setLaunchRodDir(value);
            break;
          case "launchAlt":
            settings.setLaunchAlt(value);
            break;
          case "launchLat":
            settings.setLaunchLat(value);
            break;
          case "launchLong":
            settings.setLaunchLong(value);
            break;
          case "maxAngle":
            settings.setMaxAngle(value);
            break;
          case "windSpeed":
            settings.setWindSpeed(value);
            break;
          case "windDir":
            settings.setWindDir(value);
            break;
          case "windTurbulence":
            settings.setWindTurbulence(value);
            break;
          case "launchTemp":
            settings.setLaunchTemp(value);
            break;
          case "launchAirPressure":
            settings.setLaunchAirPressure(value);
            break;
          default:
            System.out.println("Unknown attribute: " + name);
        }
      }
      // Copy settings to the public bean
      settingsMissionControl = settings;

      if (settingsMissionControl.hasErrors()) {
        JOptionPane.showMessageDialog(null,
            settingsMissionControl.getErrors(),
            "Following errors found",
            JOptionPane.ERROR_MESSAGE);
        return;
      }
      if (UserState.showGui) {
        settingsWindow.setData(settings);
      }

      System.out.println("CSV file successfully imported");

      sc.close();
    } catch (IOException ex) {
      System.out.println("Uh oh! " + ex);
    }
  }

  private void setState(String state) {
    settingsWindow.setVisible(false);
    simulationWindow.setVisible(false);
    graphWindow.setVisible(false);
    mapWindow.setVisible(false);

    if (SETTINGS.equals(state)) {
      startSettings();
      settingsWindow.setVisible(true);
    } else if (SIMULATION.equals(state)) {
      // Get simulation settings from the GUI
      settingsMissionControl = settingsWindow.getSettings();
      startSimulation();
      simulationWindow.setVisible(true);
    } else if (GRAPH.equals(state)) {
      startGraph();
      graphWindow.setVisible(true);
    } else if (MAP.equals(state)) {
      startMap();
      mapWindow.setVisible(true);
    } else {
      throw new RuntimeException("Unexpected state switch");
    }
  }

  /**
   * Saves the currently displayed graph as a PNG image.
   *
   * @param chartPanel the chartPanel being saved
   */
  //TODO doesn't work with 3D graph, problem is likely in GraphCreator.java
  private void saveGraphAsImage(ChartPanel chartPanel) {
    //Code adapted from https://stackoverflow.com/questions/34836338/how-to-save-current-chart-in-chartpanel-as-png-programmatically#34836396
    try {
      File file = new File("chart.png"); //default filename
      fileChooser.setSelectedFile(file);
      int option = fileChooser.showDialog(this, "Save as image");

      //If user clicked "save", set the file to the desired new file
      if (option == JFileChooser.APPROVE_OPTION) {
        file = fileChooser.getSelectedFile();
      } else {
        return;
      }

      //Save chart as image to selected file at original size
      try (OutputStream out = new FileOutputStream(file)) {
        ChartUtilities.writeChartAsPNG(out,
            chartPanel.getChart(),
            chartPanel.getWidth(),
            chartPanel.getHeight());
      }

    } catch (IOException ex) {
      throw new Error("IO Exception");
    }
  }

  /**
   * A simple runnable for the gui.
   *
   * @param args args
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    argumentParser(args);
  }

  private static void argumentParser(String[] args) {
    for (String argument : args) {
      String[] arg = argument.split("=");
      switch (arg[0].toUpperCase()) {
        case "-NOGUI":
          UserState.showGui = false;
          break;
        case "-IMPORTCSV":
          if (arg.length == 2) {
            UserState.csvImportPath = arg[1];
          }
          break;
        case "-IMPORTORK":
          if (arg.length == 2) {
            UserState.orkImportPath = arg[1];
          }
          break;
        case "-EXPORT":
          if (arg.length == 2) {
            UserState.exportPath = arg[1];
          }
          break;
        default:
          break;
      }
    }
    new Gui();
  }

  class SimulationRunner implements Runnable {
    private Thread thread;
    private final Runnable onFinish;

    SimulationRunner() {
      this(null);
    }

    SimulationRunner(Runnable onFinish) {
      this.onFinish = onFinish;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     *
     * <p>The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
      MonteCarloSimulation mcs;
      if (UserState.showGui) {
        mcs = new MonteCarloSimulation(simulationWindow::uptickBar);
      } else {
        mcs = new MonteCarloSimulation();
      }
      try {
        InputStream rocketFile;
        if (rocketModelFile == null) {
          ClassLoader classLoader = this.getClass().getClassLoader();
          rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
        } else {
          rocketFile = new FileInputStream(rocketModelFile);
        }

        assert rocketFile != null;
        System.out.println("ORK file successfully imported");
        data = mcs.runSimulations(rocketFile, settingsMissionControl);
        rocketFile.close();

        if (UserState.exportPath.length() > 0) {
          savePointsAsCsv(createList(data));
        }

        if (!UserState.showGui) {
          System.exit(0);
        }


      } catch (RocketLoadException | IOException e) {
        e.printStackTrace();
        //TODO deal with FileNotFoundException (don't continue running code)
      }
      if (UserState.showGui) {
        if (onFinish != null) {
          onFinish.run();
        }
      }
    }

    public void start() {
      if (thread == null) {
        thread = new Thread(this, "sim");
        thread.start();
      }
    }
  }
}