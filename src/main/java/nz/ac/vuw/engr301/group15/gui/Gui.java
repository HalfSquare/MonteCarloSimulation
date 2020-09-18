package nz.ac.vuw.engr301.group15.gui;

import static nz.ac.vuw.engr301.group15.gui.Gui.GraphType.CIRCLE;
import static nz.ac.vuw.engr301.group15.gui.Gui.GraphType.CROSS;
import static nz.ac.vuw.engr301.group15.gui.Gui.GraphType.SQUARE;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.Map;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;


public class Gui extends JFrame {

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;
  private final MapWindow mapWindow;

  private final JFileChooser fileChooser;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";
  public static final String MAP = "MAP";

  public File rocketModelFile;
  public File missionControlFile;
  public MissionControlSettings settingsMissionControl;

  public static final int NUM_ATTR = 13;
  private ArrayList<SimulationStatus> data;

  public enum GraphType {
    CIRCLE, SQUARE, CROSS
  }

  /**
   * Creates a window that runs monte carlo simulations.
   */
  public Gui() {
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
//        mapWindow.doUiStuff();

    settingsWindow.setNumSim(0);

    setState(SETTINGS);

    this.setVisible(true);
  }

  private void startSettings() {
    settingsWindow.setImportCsvButton(e -> openFileManager());
    settingsWindow.setImportOrkButton(e -> openFileManagerORK());
    settingsWindow.setStartButtonListener(e -> setState(SIMULATION));
    this.add(settingsWindow.getRootPanel());
  }

  private void startSimulation() {
    // Simulation Window
    this.add(simulationWindow.getRootPanel());
    simulationWindow.resetBar();
    simulationWindow.setBar1Max(Integer.parseInt(settingsMissionControl.getNumSimulations()));

    // Simulation stuff
    SimulationRunner runner = new SimulationRunner();
    runner.start();

  }

  private void startGraph() {
    this.add(graphWindow.getRootPanel());
    graphWindow.resetGraphPanel(); // resets the graph panel and clears previous graph
    GraphCreator g = new GraphCreator();
    ChartPanel chartPanel = g.createGraph();
//    g.createGraph();
    graphWindow.setReRunButtonListener(e -> setState(SETTINGS));
    graphWindow.setGraphTypeComboBoxListener(
            e -> setState(GRAPH)); // redraws the graph if combobox was selected
    graphWindow.setSaveImageToFileButton(e -> saveGraphAsImage(chartPanel));
    graphWindow.setCsvButtonListener(e -> saveSettingsAsCSV());
    graphWindow.setSavePointsAsCSVButton(e -> savePointsAsCSV(createList()));
    graphWindow.setViewOnMapButton(e -> setState(MAP));
    //createTable();

  }

  public void startMap() {
    this.add(mapWindow.getRootPanel());
    mapWindow.setBackButton(e -> setState(GRAPH));
    try {
      mapWindow.setMapImage(Map.createMap());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This creates a list of all the longitude and latitude points, separated by a comma
   * After each set of points, a new line is created
   *
   * @return list of all the points
   */
  private ArrayList createList() {
    ArrayList pointList = new ArrayList();

    //Adding in the column names
    pointList.add("Longitude");
    pointList.add(",");
    pointList.add("Latitude");
    pointList.add("\n");

    //Reading the points into an ArrayList
    for (int i = 0; i < data.size(); i++) {
      SimulationStatus longAndLat = data.get(i);
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
   * This saves all the points to a CSV file
   */
  private void savePointsAsCSV(ArrayList list) {
    try {
      PrintWriter pw = new PrintWriter(new File("points.csv"));
      // Reading everything into a string
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < list.size(); i++) {
        sb.append(list.get(i));
      }

      // Writing to the print writer
      pw.write(sb.toString());

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  /**
   * This will open a filechooser to save the simulation settings as a CSV.
   */
  private void saveSettingsAsCSV() {
    JFileChooser j = new JFileChooser();
    j.showSaveDialog(null);
    writeMissionControlSettings(j.getSelectedFile());
  }

  /**
   * This will write the current simulation settings to a CSV file.
   *
   * @param file being created.
   */
  private void writeMissionControlSettings(File file) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      MissionControlSettings s = settingsWindow.getSettings();
      writer.write("launchRodAngle,launchRodLength,launchRodDir,launchAlt,launchLat," +
              "launchLong,maxAngle,windSpeed,windDir,windTurbulence,launchTemp,launchAirPressure,numSimulations");
      writer.write(s.getLaunchRodAngle() + "," + s.getLaunchRodLength() + "," + s.getLaunchRodDir() + "," +
              s.getLaunchAlt() + "," + s.getLaunchLat() + "," + s.getLaunchLong() + "," + s.getMaxAngle() + "," +
              s.getWindSpeed() + "," + s.getWindDir() + "," + s.getWindTurbulence() + "," + s.getLaunchTemp() + "," +
              s.getLaunchAirPressure() + "," + s.getNumSimulations());
      writer.close();
    } catch (Exception ex) {
      System.out.println("Uh oh! " + ex);
    }

  }

  /**
   * This opens up a fileChooser to open up a CSV file
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
      String name, value;

      // Read data into 2D array, splitting at the commas (0 is data names, 1 is data values)
      for (int i = 0; i < 2; i++) {
        if (sc.hasNext()) {
          data[i] = sc.nextLine().split(",");
        }
      }

      // Read data from 2D array into the bean, using a switch so that attribute ordering does not matter
      for (int i = 0; i < NUM_ATTR; i++) {
        name = data[0][i];
        value = data[1][i];
        switch (name) {
          case "numSimulations":
            settings.setNumSimulations(value);
            settingsWindow.setNumSim(Integer.parseInt(value));
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
        }
      }
      // Copy settings to the public bean
      settingsMissionControl = settings;
      settingsWindow.setData(settings);
      sc.close();
    } catch (Exception ex) {
      System.out.println("Uh oh! " + ex);
    }
  }

  /**
   * This opens up a fileChooser to open up an ORK file (OpenRocket model rocket file)
   */
  private void openFileManagerORK() {
    JFileChooser j = new JFileChooser();
    //Filter for ORK files only
    FileNameExtensionFilter filter = new FileNameExtensionFilter("ORK files", "ork", "ORK");
    j.setFileFilter(filter);
    j.showSaveDialog(null);
    rocketModelFile = j.getSelectedFile();
  }

  /**
   * Saves the currently displayed graph as a PNG image.
   *
   * @param chartPanel the chartPanel being saved
   */
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

    } catch (IOException ex) {
      throw new Error("IO Exception");
    }
  }

  /**
   * Table containing all longitude and latitude data points
   * Uncomment if you wish to view it. Additionally, you should go to GraphWindow.form
   * and create a new JTable called simulationTable after double cliking on the centre of the
   * page
   */
  private void createTable() {
    String[][] pointArray = new String[data.size()][2];
    String[] columnNames = {"Longitude", "Latitude"};

    //reading the points into the List
    for (int i = 0; i < data.size(); i++) {
      SimulationStatus longAndLat = data.get(i);
      WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
      double x = landingPos.getLongitudeDeg();
      double y = landingPos.getLatitudeDeg();
      pointArray[i][0] = String.valueOf(x);
      pointArray[i][1] = String.valueOf(y);
    }

    DefaultTableModel tableModel = new DefaultTableModel(pointArray, columnNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    graphWindow.getSimulationTable().setModel(tableModel);
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

    Gui gui = new Gui();
  }

  class SimulationRunner implements Runnable {
    private Thread thread;

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
      MonteCarloSimulation mcs = new MonteCarloSimulation(simulationWindow::uptickBar);
      try {
        if (rocketModelFile == null) {
          ClassLoader classLoader = this.getClass().getClassLoader();
          InputStream rocketFile = classLoader.getResourceAsStream("rocket-1-1-9.ork");
          data = mcs.runSimulations(Integer.parseInt(settingsMissionControl.getNumSimulations()), rocketFile, settingsMissionControl);
        } else {
          InputStream rocketFile = new FileInputStream(rocketModelFile);
          data = mcs.runSimulations(Integer.parseInt(settingsMissionControl.getNumSimulations()), rocketFile, settingsMissionControl);
        }

      } catch (RocketLoadException | FileNotFoundException e) {
        e.printStackTrace();
      }

      setState(GRAPH);
    }

    public void start() {
      if (thread == null) {
        thread = new Thread(this, "sim");
        thread.start();
      }
    }

  }

  class GraphCreator {
    /**
     * Constructor.
     *
     * @return created graph in ChartPanel form
     */
    public ChartPanel createGraph() {

      GraphType graphType = GraphType.valueOf(graphWindow.getGraphTypeComboBox().toUpperCase());

      // Create chart
      JFreeChart chart = ChartFactory.createScatterPlot(
              "Longitude vs. Latitude Points",
              "Longitude",
              "Latitude",
              createDataset(),
              PlotOrientation.VERTICAL,
              true, true, false);

      // Default circle shape
      Shape shape = new Ellipse2D.Double(-3.0, -3.0, 3.0, 3.0);

      // Creates the plotting shape
      if (CROSS.equals(graphType)) {
        shape = ShapeUtilities.createDiagonalCross(1, 1);
      }
      if (SQUARE.equals(graphType)) {
        shape = new Rectangle2D.Double(-3, -3, 3, 3);
      }
      if (CIRCLE.equals(graphType)) {
        shape = new Ellipse2D.Double(-3.0, -3.0, 3.0, 3.0);
      }

      //Changes background color
      XYPlot plot = (XYPlot) chart.getPlot();
      plot.setBackgroundPaint(new Color(255, 228, 196));

      // Renders the points
      AbstractRenderer renderer = (AbstractRenderer) plot.getRenderer();
      renderer.setSeriesShape(0, shape);

      // Create Panel
      ChartPanel panel = new ChartPanel(chart);
      graphWindow.getGraphPanel().setLayout(new BorderLayout());
      graphWindow.getGraphPanel().add(panel, BorderLayout.CENTER);
      graphWindow.getGraphPanel().validate();

      return panel;
    }

    /**
     * This goes through all the simulation points and adds them to a list of longitude and latitude points
     *
     * @return
     */
    private XYDataset createDataset() {
      // Create scatter points
      final XYSeries longAndLatPoints = new XYSeries("longAndLatPoints");
      for (SimulationStatus longAndLat : data) {
        WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
        double x = landingPos.getLongitudeDeg();
        double y = landingPos.getLatitudeDeg();
        longAndLatPoints.add(x, y);
      }

      final XYSeriesCollection dataset = new XYSeriesCollection();
      dataset.addSeries(longAndLatPoints);
      return dataset;
    }
  }
}


