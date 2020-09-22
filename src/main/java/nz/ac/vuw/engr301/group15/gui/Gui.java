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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.Map;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import nz.ac.vuw.engr301.group15.montecarlo.SimulationBatch;
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
  private static final int THREAD_COUNT = 100;

  public enum GraphType {
    CIRCLE, SQUARE, CROSS, FLIGHTPATH
  }


  /**
   * Creates a window that runs monte carlo simulations.
   *
   * @param show boolean to determine if should be run with GUI or not.
   * @param file CSV file to import weather data.
   */
  public Gui(boolean show, File file) {

    if (show) {
      this.data = new ArrayList<>();

      this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      this.setSize(600, 400);
      this.setLocationRelativeTo(null);

      settingsWindow = new SettingsWindow();
      simulationWindow = new SimulationWindow();
      graphWindow = new GraphWindow();
      mapWindow = new MapWindow();

      // If null, the user has chose not to import custom files and defaults should be used
      rocketModelFile = null;
      missionControlFile = null;
      settingsMissionControl = null;

      fileChooser = new JFileChooser();

      settingsWindow.doUiStuff();
      simulationWindow.doUiStuff();
      graphWindow.doUiStuff();

      settingsWindow.setNumSim("1000");
      settingsWindow.setNumClusters(numberOfClusters);

      setState(SETTINGS);

      this.setVisible(true);
    } else {
      loadMissionControlData(file, false);
      SimulationRunner simulationRunner = new SimulationRunner();
      simulationRunner.show = false;
      simulationRunner.start();
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
    simulationWindow.resetBars();
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
    //createTable();

  }

  /**
   * This creates a list of all the longitude and latitude points, separated by a comma.
   * After each set of points, a new line is created
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
   * This saves all the points to a CSV file.
   *
   * @return filepath
   */
  public static String savePointsAsCsv(ArrayList<String> list) {
    try {
      File file = new File("points.csv");
      PrintWriter pw = new PrintWriter(file);
      // Reading everything into a string
      StringBuilder sb = new StringBuilder();
      for (String s : list) {
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
      loadMissionControlData(j.getSelectedFile(), true);
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
   * @param show boolean to determine if program should run with GUI or not.
   */
  public void loadMissionControlData(File file, boolean show) {
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
              if (show) {
                settingsWindow.setNumSim(value);
              }
            } else {
              if (show) {
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
      if (show) {
        settingsWindow.setData(settings);
      } else {
        System.out.println("CSV file successfully imported");
      }
      sc.close();
    } catch (Exception ex) {
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
      OutputStream out = new FileOutputStream(file);
      ChartUtilities.writeChartAsPNG(out,
          chartPanel.getChart(),
          chartPanel.getWidth(),
          chartPanel.getHeight());
      out.close();

    } catch (IOException ex) {
      throw new Error("IO Exception");
    }
  }

  //  /**
  //   * Table containing all longitude and latitude data points
  //   * Uncomment if you wish to view it. Additionally, you should go to GraphWindow.form
  //   * and create a new JTable called simulationTable after double clicking on the centre of the
  //   * page
  //   */
  //  private void createTable() {
  //    String[][] pointArray = new String[data.size()][2];
  //    String[] columnNames = {"Longitude", "Latitude"};
  //
  //    //reading the points into the List
  //    for (int i = 0; i < data.size(); i++) {
  //      SimulationStatus longAndLat = SimulationDuple.getStatuses(data).get(i);
  //      WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
  //      double x = landingPos.getLongitudeDeg();
  //      double y = landingPos.getLatitudeDeg();
  //      pointArray[i][0] = String.valueOf(x);
  //      pointArray[i][1] = String.valueOf(y);
  //    }
  //
  //    DefaultTableModel tableModel = new DefaultTableModel(pointArray, columnNames) {
  //      @Override
  //      public boolean isCellEditable(int row, int column) {
  //        return false;
  //      }
  //    };
  //    graphWindow.getSimulationTable().setModel(tableModel);
  //  }

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
    if (args.length >= 1) {
      String guiArg = args[0];
      if (guiArg.equals("-nogui")) {
        if (args.length > 1) {
          File f = new File(args[1]);
          new Gui(false, f);
        } else {
          throw new RuntimeException("Invalid arguments: "
              + "Correct format e.g. -nogui src/main/resources/testMCData.csv");
        }
      } else { // run with gui
        new Gui(true, null);
      }
    } else {
      new Gui(true, null);
    }
  }


  class SimulationRunner implements Runnable {
    private Thread thread;
    private final Runnable onFinish;
    private boolean show = true;

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
      if (show) {
        mcs = new MonteCarloSimulation(simulationWindow::uptickBar);
      } else {
        mcs = new MonteCarloSimulation();
      }
      try {


        data = new ArrayList<>();

        ArrayList<SimulationBatch> batches = new ArrayList<>();
        ExecutorService es = Executors.newCachedThreadPool();

        int perBatch = settingsMissionControl.getNumSimulationsAsInteger() / THREAD_COUNT;

        simulationWindow.setBar1Max(THREAD_COUNT);
        simulationWindow.setBatch2Max(settingsMissionControl.getNumSimulationsAsInteger());

        ArrayList<Runnable> barUpdates = new ArrayList<>();
        barUpdates.add(simulationWindow::uptickBar);
        barUpdates.add(simulationWindow::uptickBatch2);

        for (int thread = 1; thread <= THREAD_COUNT; thread++) {
          InputStream rocketFile;
          if (rocketModelFile == null) {
            ClassLoader classLoader = this.getClass().getClassLoader();
            rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
          } else {
            rocketFile = new FileInputStream(rocketModelFile);
          }

          assert rocketFile != null;

          SimulationBatch curThread = new SimulationBatch(
                  "Simulation Batch " + thread,
                  perBatch,
                  rocketFile,
                  settingsMissionControl,
                  simulationWindow::uptickBatch2,
                  simulationWindow::uptickBar
//                  barUpdates.get(thread-1),
//                  () -> System.out.println("")
                );
          batches.add(curThread);
          es.execute(curThread);
//          curThread.start();
        }
        es.shutdown();
        es.awaitTermination(3, TimeUnit.HOURS);

        for (SimulationBatch thread : batches) {
          data.addAll(thread.getData());
        }

        // data = mcs.runSimulations(rocketFile, settingsMissionControl);


//        rocketFile.close();

        if (!show) {
          savePointsAsCsv(createList(data));
          System.exit(0);
        }


      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        //TODO deal with FileNotFoundException (don't continue running code)
      }
      if (show) {
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



