package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Mapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

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

    settingsWindow.doUIStuff();
    simulationWindow.doUIStuff();
    graphWindow.doUIStuff();

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

    //Table
    String[][] pointArray = new String[data.size()][2];
    String[] columnNames = {"Longitude", "Latitude"};


    //reading the points into the List
    for(int i = 0; i < data.size(); i++){
        SimulationStatus longAndLat = data.get(i);
        WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
        double x = landingPos.getLongitudeDeg();
        double y =  landingPos.getLatitudeDeg();
        pointArray[i][0] = String.valueOf(x);
        pointArray[i][1] = String.valueOf(y);
    }


    DefaultTableModel tableModel = new DefaultTableModel(pointArray, columnNames){
      @Override
      public boolean isCellEditable(int row, int column){
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

//// Create scatter points
//      for(int i = 0; i < data.size(); i++){
//        SimulationStatus longAndLat = data.get(i);
//        WorldCoordinate landingPos = longAndLat.getRocketWorldPosition();
//        x = (float)landingPos.getLongitudeDeg();
//        y = (float)landingPos.getLatitudeDeg();
//        z = (float)0;
//        a = 0.25f;
//        points[i] = new Coord3d(x,y,z);
//        colors[i] = new org.jzy3d.colors.Color(x, y, z, a);
//      }
//      System.out.println("Points length:" + points.length);
//      System.out.println("colors length:" + colors.length);

//      Start of almost functioninggraph
//      JFreeChart xylineChart = ChartFactory.createXYLineChart(
//              "chartTitle" ,
//              "Category" ,
//              "Score" ,
//              createDataset() ,
//              PlotOrientation.VERTICAL ,
//              true , true , false);
//
//      ChartPanel chartPanel = new ChartPanel( xylineChart );
//      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
//      final XYPlot plot = xylineChart.getXYPlot( );
//
//      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
//      renderer.setSeriesPaint( 0 , Color.BLACK);
//      renderer.setSeriesPaint( 1 , Color.GREEN );
//      renderer.setSeriesPaint( 2 , Color.YELLOW );
//      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
//      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
//      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
//      plot.setRenderer( renderer );
////      setContentPane( chartPanel );
//      graphWindow.addToGraph( chartPanel );
    }
//
//    private XYDataset createDataset( ) {
//      final XYSeries firefox = new XYSeries( "Firefox" );
//      firefox.add( 1.0 , 1.0 );
//      firefox.add( 2.0 , 4.0 );
//      firefox.add( 3.0 , 3.0 );
//
//      final XYSeries chrome = new XYSeries( "Chrome" );
//      chrome.add( 1.0 , 4.0 );
//      chrome.add( 2.0 , 5.0 );
//      chrome.add( 3.0 , 6.0 );
//
//      final XYSeries iexplorer = new XYSeries( "InternetExplorer" );
//      iexplorer.add( 3.0 , 4.0 );
//      iexplorer.add( 4.0 , 5.0 );
//      iexplorer.add( 5.0 , 4.0 );
//
//      final XYSeriesCollection dataset = new XYSeriesCollection( );
//      dataset.addSeries( firefox );
//      dataset.addSeries( chrome );
//      dataset.addSeries( iexplorer );
//      return dataset;
//    }
  }
}


