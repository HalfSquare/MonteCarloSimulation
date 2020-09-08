package nz.ac.vuw.engr301.group15.gui;

import com.orsoncharts.graphics3d.World;
import com.orsoncharts.renderer.xyz.LineXYZRenderer;
import com.orsoncharts.style.ChartStyle;
import com.orsoncharts.style.StandardChartStyle;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.rocketcomponent.Configuration;
import net.sf.openrocket.rocketcomponent.Rocket;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulationExtensionListener;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulationExtensionListenerRecordPath;
import nz.ac.vuw.engr301.group15.montecarlo.OpenRocketHelper;
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

import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.axis.IntegerTickSelector;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.plot.XYZPlot;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GraphCreator {
  private GraphWindow graphWindow;
  private ArrayList<SimulationStatus> data;

  /**
   * Constructor
   *
   * @param graphWindow the graph JFrame
   * @param data  ArrayList of SimulationStatus
   */
  public GraphCreator(GraphWindow graphWindow, ArrayList<SimulationStatus> data){
    this.graphWindow = graphWindow;
    this.data = data;
  }

  /**
   * Creates the graph.
   *
   * @return created graph in ChartPanel form
   */
  public ChartPanel createGraph() {

    //TODO: major rewriting needed
    Gui.GraphType graphType;
    JFreeChart chart;

    if(graphWindow.getGraphTypeComboBox().equals("3D")){
      graphType = Gui.GraphType.FLIGHTPATH;
    }
    else {
      graphType = Gui.GraphType.valueOf(graphWindow.getGraphTypeComboBox().toUpperCase());
    }

    // Create chart
    chart = ChartFactory.createScatterPlot(
            "Longitude vs. Latitude Points",
            "Longitude",
            "Latitude",
            createDataset(),
            PlotOrientation.VERTICAL,
            true, true, false);
    // Default circle shape
    Shape shape = new Ellipse2D.Double(-3.0, -3.0, 3.0, 3.0);

    // Creates the plotting shape
    switch(graphType){
      case CROSS:
        shape = ShapeUtilities.createDiagonalCross(1, 1);
        break;
      case SQUARE:
        shape = new Rectangle2D.Double(-3, -3, 3, 3);
        break;
      case CIRCLE:
        shape = new Ellipse2D.Double(-3.0, -3.0, 3.0, 3.0);
        break;
      case FLIGHTPATH:
        ChartPanel panel = new ChartPanel(chart);
        graphWindow.getGraphPanel().setLayout(new BorderLayout());
        graphWindow.getGraphPanel().add(create3DGraph(create3DDataset()), BorderLayout.CENTER);
        graphWindow.getGraphPanel().validate();
        return panel;
      default:
        throw new RuntimeException("Help");
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

  /**
   * Returns a panel containing the content for the demo.  This method is
   * used across all the individual demo applications to allow aggregation
   * into a single "umbrella" demo (OrsonChartsDemo).
   *
   * @return A panel containing the content for the demo.
   */
  public static JPanel create3DGraph(XYZDataset dataset) {
    Chart3D chart = createChart(dataset);
    Chart3DPanel chartPanel = new Chart3DPanel(chart);
    return chartPanel;
    //chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
//        content.add(new DisplayPanel3D(chartPanel));
//        return content;
  }

  private static Chart3D createChart(XYZDataset dataset) {
    Chart3D chart = Chart3DFactory.createXYZLineChart("Flight paths",
            "", dataset, "Latitude (?)", "Altitude", "Longitude (?)");
    chart.setChartBoxColor(new Color(255, 255, 255, 128));
    XYZPlot plot = (XYZPlot) chart.getPlot();
    plot.setDimensions(new Dimension3D(15, 3, 8));

    //Customise axes
    NumberAxis3D zAxis = (NumberAxis3D) plot.getZAxis();
    zAxis.setTickSelector(new IntegerTickSelector());
    zAxis.setRange(0, 20);
    plot.getXAxis().setRange(5, 30);
    plot.getYAxis().setRange(0, 100);

    //Customise colours
    LineXYZRenderer renderer = new LineXYZRenderer();
    renderer.setColors(Color.BLUE, Color.RED);
    plot.setRenderer(renderer);

    return chart;
  }

  /**
   * Creates a sample dataset (hard-coded for the purpose of keeping the
   * demo self-contained - in practice you would normally read your data
   * from a file, database or other source).
   *
   * @return A sample dataset.
   */
  public XYZDataset<String> create3DDataset() {
    //TODO This method should get the set of latLongBeans from the kmeans clustering algorithm
    //Find the closest simulation endpoints to those beans
    //run the simulations again and record each point in the flight path
    //Graph those points

    //Test code
    SimulationStatus sampleSim = data.get(0);
    ArrayList<WorldCoordinate> coordPoints = (ArrayList<WorldCoordinate>)recordFlightpath(sampleSim);

    //-------------------Example points----------------//
    XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();

    XYZSeries<String> series1 = new XYZSeries<String>("Series 1");
    series1.add(10,10,10);
    series1.add(12,40,11);
    series1.add(15,80,10);
    series1.add(18,40, 9);
    series1.add(20,0,8);
    dataset.add(series1);

    XYZSeries<String> series2 = new XYZSeries<String>("Series 2");
    series2.add(10,10,10);
    series2.add(12,40,9);
    series2.add(18,10,10);
    series2.add(19,0,10);
    dataset.add(series2);

    XYZSeries<String> series3 = new XYZSeries<String>("Series 3");
    series3.add(10,10,10);
    series3.add(13,70,10);
    series3.add(14,65,10);
    series3.add(16,20,10);
    series3.add(20, 0, 11);
    dataset.add(series3);

    return dataset;
  }

  /**
   * Takes a single simulation an re-runs it, recording each coordinate along the way
   * @param simulation the simulation to be recorded
   * @return A list of each coordinate along the way
   */
  private List<WorldCoordinate> recordFlightpath(SimulationStatus simulation){
    List<WorldCoordinate> points = new ArrayList<WorldCoordinate>();
    //get config and run again
    Configuration config = simulation.getConfiguration();
    OpenRocketHelper helper = new OpenRocketHelper();
    Simulation sim = new Simulation(config.getRocket());
    MonteCarloSimulation mcs = new MonteCarloSimulation();
    mcs.setDoRandom(false);
    MonteCarloSimulationExtensionListenerRecordPath simulationListener =  new MonteCarloSimulationExtensionListenerRecordPath();

    try {
      sim.simulate(simulationListener);
    } catch (SimulationException exception) {
      exception.printStackTrace();
    }

    points = simulationListener.getPathPoints();

    return points;
  }
}
