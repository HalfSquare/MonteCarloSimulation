package nz.ac.vuw.engr301.group15.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
import static nz.ac.vuw.engr301.group15.gui.Gui.GraphType.CIRCLE;
import static nz.ac.vuw.engr301.group15.gui.Gui.GraphType.CROSS;
import static nz.ac.vuw.engr301.group15.gui.Gui.GraphType.SQUARE;


public class Gui extends JFrame {

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";

  public static final int NUM_SIMS = 1000;
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
    this.setSize(600, 300);
    this.setLocationRelativeTo(null);

    settingsWindow = new SettingsWindow();
    simulationWindow = new SimulationWindow();
    graphWindow = new GraphWindow();

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
    this.add(graphWindow.getRootPanel());
    graphWindow.resetGraphPanel(); // resets the graph panel and clears previous graph
    GraphCreater g = new GraphCreater();
    g.createGraph();
    graphWindow.setReRunButtonListener(e -> setState(SETTINGS));
    graphWindow.setGraphTypeComboBoxListener(
      e -> setState(GRAPH)); // redraws the graph if combobox was selected
    //createTable();

  }

  /**
   * Table containing all longitude and latitude data points Uncomment if you wish to view it.
   * Additionally, you should go to GraphWindow.form and create a new JTable called simulationTable
   * after double cliking on the centre of the page
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
     * When an object implementing interface <code>Runnable</code> is used to create a thread,
     * starting the thread causes the object's
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

  class GraphCreater {

    public void createGraph() {
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
      if (CROSS.equals(graphType)){
        shape = ShapeUtilities.createDiagonalCross(1, 1);
      }
      if (SQUARE.equals(graphType)){
        shape = new Rectangle2D.Double(-3,-3,3,3);
      }
      if (CIRCLE.equals(graphType)){
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


