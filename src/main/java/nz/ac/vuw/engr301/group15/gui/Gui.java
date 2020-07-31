package nz.ac.vuw.engr301.group15.gui;

//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Gui extends JFrame {

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;

  private final JFileChooser fileChooser;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";

  public static final int NUM_SIMS = 1000;
  private ArrayList<SimulationStatus> data;

  public enum GraphType {
    CIRCLE, SQUARE, ROCKET
  }

  /**
   * Creates a window that runs monte carlo simulations.
   */
  public Gui() {
    this.data = new ArrayList<>();

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setSize(600, 300);
    this.setLocationRelativeTo(null);

    settingsWindow = new SettingsWindow();
    simulationWindow = new SimulationWindow();
    graphWindow = new GraphWindow();

    fileChooser = new JFileChooser();

    settingsWindow.doUiStuff();
    simulationWindow.doUiStuff();
    graphWindow.doUiStuff();

    settingsWindow.setNumSim(NUM_SIMS);

    setState(SETTINGS);

    this.setVisible(true);
  }

  private void startSettings() {
    settingsWindow.setStartButtonListener(e -> setState(SIMULATION));
    this.add(settingsWindow.getRootPanel());
  }

  private void startSimulation() {
    // Simulation Window
    this.add(simulationWindow.getRootPanel());
    simulationWindow.resetBar();
    simulationWindow.setBar1Max(NUM_SIMS);

    // Simulation stuff
    SimulationRunner runner = new SimulationRunner();
    runner.start();

  }

  private void startGraph() {
    GraphCreator g = new GraphCreator();
    ChartPanel chartPanel = g.createGraph();
    GraphType graphType = GraphType.CIRCLE;

    this.add(graphWindow.getRootPanel());
    graphWindow.setReRunButtonListener(e -> setState(SETTINGS));

    graphWindow.setSaveImageToFileButton(e -> saveGraphAsImage(chartPanel));
    //createTable();

  }

  /**
   * Saves the currently displayed graph as a PNG image.
   * @param chartPanel the chartPanel being saved
   */
  private void saveGraphAsImage(ChartPanel chartPanel){
    //Code adapted from https://stackoverflow.com/questions/34836338/how-to-save-current-chart-in-chartpanel-as-png-programmatically#34836396
    try {
      File file = new File("chart.png"); //default filename
      fileChooser.setSelectedFile(file);
      int option = fileChooser.showDialog(this, "Save as image");

      //If user clicked "save", set the file to the desired new file
      if(option == JFileChooser.APPROVE_OPTION) {
        file = fileChooser.getSelectedFile();
      }
      else{
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
   *     Uncomment if you wish to view it. Additionally, you should go to GraphWindow.form
   *     and create a new JTable called simulationTable after double cliking on the centre of the
   *     page
   */
  private void createTable() {
    String[][] pointArray = new String[data.size()][2];
    String[] columnNames = {"Longitude", "Latitude"};

    //reading the points into the List
    for (int i = 0; i < data.size(); i++) {
      SimulationStatus longAndLat = data.get(i);
      WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
      double x = landingPos.getLongitudeDeg();
      double y =  landingPos.getLatitudeDeg();
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

    if (SETTINGS.equals(state)) {
      startSettings();
      settingsWindow.setVisible(true);
    } else if (SIMULATION.equals(state)) {
      startSimulation();
      simulationWindow.setVisible(true);
    } else if (GRAPH.equals(state)) {
      startGraph();
      graphWindow.setVisible(true);
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
        data = mcs.runSimulations(NUM_SIMS);
      } catch (RocketLoadException e) {
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
      // Create chart
      JFreeChart chart = ChartFactory.createScatterPlot(
              "tLongitude vs. Latitude Points",
              "Longitude",
              "Latitude",
              createDataset(),
              PlotOrientation.VERTICAL,
              true, true, false);


      //Changes background color
      XYPlot plot = (XYPlot) chart.getPlot();
      plot.setBackgroundPaint(new Color(255, 228, 196));

      // Create Panel
      ChartPanel panel = new ChartPanel(chart);
      graphWindow.getGraphPanel().setLayout(new BorderLayout());
      graphWindow.getGraphPanel().add(panel, BorderLayout.CENTER);
      graphWindow.getGraphPanel().validate();

      return panel;
    }

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


