package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Scatter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.Color.WHITE;

public class GUI extends JFrame {

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";

  public static final int NUM_SIMS = 1000;
  private ArrayList<SimulationStatus> data;

  public enum GraphType {
    CIRCLE, SQUARE, ROCKET
  }

  public GUI() {
    this.data = new ArrayList<>();

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setSize(600, 300);
    this.setLocationRelativeTo(null);

    settingsWindow = new SettingsWindow();
    simulationWindow = new SimulationWindow();
    graphWindow = new GraphWindow();

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
    GraphCreater g = new GraphCreater();
    g.createGraph();
    GraphType graphType = GraphType.CIRCLE;

    this.add(graphWindow.getRootPanel());
    graphWindow.setReRunButtonListener(e -> setState(SETTINGS));
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

  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    GUI gui = new GUI();
  }

  class SimulationRunner implements Runnable {
    private Thread t;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
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
//      System.out.println("Starting ");
      if (t == null) {
        t = new Thread(this, "sim");
        t.start();
      }
    }

    // Define a function to plot
    Mapper mapper = new Mapper() {
      public double f(double x, double y) {
        return 10 * Math.sin(x / 10) * Math.cos(y / 20);
      }
    };

  }

  class GraphCreater{
    public void createGraph(){
//      Graphics2D g2D = (Graphics2D)g;
//      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//      //Adding all points to list to be graphed
//      ArrayList<Point> graphPoints = new ArrayList<Point>();
//      for (int i = 0; i < data.size(); i++ ){
//        // Get the long and lat points
//        SimulationStatus longAndLat = data.get(0);
//        WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
//        int longPost = (int)landingPos.getLongitudeDeg();
//        int latPos = (int)landingPos.getLatitudeDeg();
//        graphPoints.add(new Point(longPost, latPos));
//      }
//
//      //Drawing the graph
//
//      //Making the background
//      g2D.setColor(Color.WHITE);
      // Define a function to plot
      float x;
      float y;
      float z;
      float a;

      System.out.println("data " + data.size());
      Coord3d[] points = new Coord3d[data.size()];
      org.jzy3d.colors.Color[]   colors = new org.jzy3d.colors.Color[data.size()];

// Create scatter points
      for(int i = 0; i < data.size(); i++){
        SimulationStatus longAndLat = data.get(i);
        WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
        x = (float)landingPos.getLongitudeDeg();
        y = (float)landingPos.getLatitudeDeg();
        z = (float)0;
        a = 0.25f;
        points[i] = new Coord3d(x,y,z);
        colors[i] = new org.jzy3d.colors.Color(x, y, z, a);
      }
      System.out.println("Points length:" + points.length);
      System.out.println("colors length:" + colors.length);

// Create a drawable scatter with a colormap
      Scatter scatter = new Scatter(points, colors);

// Create a chart and add scatter
      Chart chart = new Chart();
      chart.white();
//      chart.getAxeLayout().setMainColor(org.jzy3d.colors.Color.WHITE);
//      chart.getView().setBackgroundColor(org.jzy3d.colors.Color.BLACK);
      chart.getScene().add(scatter);
      ChartLauncher.openChart(chart);

    }
  }
}


