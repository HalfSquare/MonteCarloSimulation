package nz.ac.vuw.engr301.group15.gui;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.axis.IntegerTickSelector;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.LineXYZRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.rocketcomponent.Configuration;
import net.sf.openrocket.simulation.SimulationOptions;
import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.util.WorldCoordinate;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulationExtensionListenerRecordPath;
import nz.ac.vuw.engr301.group15.montecarlo.SimulationDuple;
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


public class GraphCreator {
  private final GraphWindow graphWindow;
  private final ArrayList<SimulationDuple> data;

  /**
   * Constructor.
   *
   * @param graphWindow the graph JFrame
   * @param data        ArrayList of SimulationStatus
   */
  public GraphCreator(GraphWindow graphWindow, ArrayList<SimulationDuple> data) {
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

    if (graphWindow.getGraphTypeComboBox().equals("3D")) {
      graphType = Gui.GraphType.FLIGHTPATH;
    } else {
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
    Shape shape;

    // Creates the plotting shape
    switch (graphType) {
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
        graphWindow.getGraphPanel().setLayout(new BorderLayout());
        graphWindow.getGraphPanel().add(create3DGraph(create3DDataset()), BorderLayout.CENTER);
        graphWindow.getGraphPanel().validate();
        return new ChartPanel(chart);
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
   * This goes through all the simulation points and adds them
   * to a list of longitude and latitude points.
   *
   * @return the dataset
   */
  private XYDataset createDataset() {
    // Create scatter points
    final XYSeries longAndLatPoints = new XYSeries("longAndLatPoints");
    for (SimulationStatus longAndLat : SimulationDuple.getStatuses(data)) {
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
  public static JPanel create3DGraph(XYZDataset<String> dataset) {
    Chart3D chart = createChart(dataset);
    return new Chart3DPanel(chart);
    //chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
    //content.add(new DisplayPanel3D(chartPanel));
    //return content;
  }

  /**
   * Makes a 3D chart.
   *
   * @param dataset the points to graph
   * @return the 3D chart
   */
  private static Chart3D createChart(XYZDataset<String> dataset) {
    Chart3D chart = Chart3DFactory.createXYZLineChart("Flight paths",
            "", dataset, "Latitude (?)", "Altitude", "Longitude (?)");
    chart.setChartBoxColor(new Color(255, 255, 255, 128));
    XYZPlot plot = (XYZPlot) chart.getPlot();
    plot.setDimensions(new Dimension3D(15, 3, 8));

    //Customise axes
    NumberAxis3D plotZAxis = (NumberAxis3D) plot.getZAxis();
    plotZAxis.setTickSelector(new IntegerTickSelector());
    //zAxis.setRange(0, 20);
    //plot.getXAxis().setRange(5, 30);
    //plot.getYAxis().setRange(0, 100);

    //Customise colours
    LineXYZRenderer renderer = new LineXYZRenderer();
    renderer.setColors(Color.BLUE, Color.RED, Color.GREEN.darker(), Color.MAGENTA);
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
    XYZSeriesCollection<String> dataset = new XYZSeriesCollection<>();
    double groundAlt = data.get(0).getSimulationStatus().getRocketWorldPosition().getAltitude();

    //Gets cluster points from the data
    String filePath = Gui.savePointsAsCsv(Gui.createList(data));
    Set<LatLongBean> clusters = KMeansClustering.calculateClusters(filePath, 3);
    XYZSeries<String> clusterSeries = new XYZSeries<>("Clusters");

    //Find closest SimulationDuple to each cluster center
    int n = 1;
    for (LatLongBean coord : clusters) {
      clusterSeries.add(coord.getLatitude(), groundAlt, coord.getLongitude());

      double smallestDistance = Double.MAX_VALUE;
      SimulationDuple closestPoint = data.get(0);
      //Find closest endpoint to the coord
      for (SimulationDuple endpoint : data) {
        LatLongBean endPointBean = LatLongBean.fromSimulationStatus(endpoint.getSimulationStatus());
        double distanceBetween = distance(coord, endPointBean);
        if (distanceBetween < smallestDistance) {
          closestPoint = endpoint;
          smallestDistance = distanceBetween;
        }
      }



      //Record flight path
      SimulationStatus sampleSim = closestPoint.getSimulationStatus();
      SimulationOptions sampleOptions = closestPoint.getSimulationOptions();
      ArrayList<WorldCoordinate> coordPoints =
              (ArrayList<WorldCoordinate>) recordFlightpath(sampleSim, sampleOptions);

      XYZSeries<String> series = new XYZSeries<>("Center " + n);
      n++;


      //Add each series to the dataset
      for (WorldCoordinate c : coordPoints) {
        series.add(c.getLatitudeDeg(), c.getAltitude(), c.getLongitudeDeg());
        //System.out.printf("Lat: %.10f, Alt: %.10f, Long: %.10f\n"
        // , c.getLatitudeDeg(), c.getAltitude(), c.getLongitudeDeg());
      }
      dataset.add(series);

    }

    dataset.add(clusterSeries);
    return dataset;
  }

  /**
   * Takes a single simulation an re-runs it, recording each coordinate along the way.
   * The simulated flight has a variation of 3-8 cm which is almost as small as the rocket.
   *
   * @param simulationStatus the simulation to be recorded
   * @return A list of each coordinate along the way
   */
  private List<WorldCoordinate> recordFlightpath(SimulationStatus simulationStatus,
                                                 SimulationOptions simulationOptions) {
    // Get config and copy it to new options
    Configuration configuration = simulationStatus.getConfiguration();
    Simulation simulation = new Simulation(configuration.getRocket());
    simulation.getOptions().copyFrom(simulationOptions);

    // Create the new simulation
    MonteCarloSimulation mcs = new MonteCarloSimulation();
    mcs.setDoRandom(false);
    MonteCarloSimulationExtensionListenerRecordPath simulationListener =
            new MonteCarloSimulationExtensionListenerRecordPath();

    System.out.printf("Seed before: %d\n", simulationOptions.getRandomSeed());

    // Run the new simulation
    try {
      simulation.simulate(simulationListener);
    } catch (SimulationException exception) {
      exception.printStackTrace();
    }
    System.out.printf("Seed after: %d\n", simulationOptions.getRandomSeed());

    // Get the points from the simulation
    List<WorldCoordinate> points;
    points = simulationListener.getPathPoints();

    // *** Debugging printouts ***
    //System.out.println("Start to fin Distance:");
    //System.out.println(distance(points.get(0), points.get(points.size() - 1)) * 100_000);

    //System.out.println("Distance:");
    //System.out.println(distance(simulationStatus.getRocketWorldPosition(),
    // points.get(points.size() - 1)) * 100_000);

    //System.out.printf("Initial: %.20f, %.20f\n",
    // simulationStatus.getRocketWorldPosition().getLatitudeDeg(),
    // simulationStatus.getRocketWorldPosition().getLongitudeDeg());
    //System.out.printf("ReRun:   %.20f, %.20f\n", points.get(points.size() - 1).getLatitudeDeg(),
    // points.get(points.size() - 1).getLongitudeDeg());

    return points;
  }

  /**
   * Calculates the distance between two WorldCoordinates.
   *
   * @param first the first lat/long coord
   * @param second the second lat/long coord
   * @return the distance in meters between <code>first</code> and <code>second</code>
   */
  public static double distance(WorldCoordinate first, WorldCoordinate second) {
    //https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
    int radius = 6371; // Radius of the earth in km

    double lat1 = first.getLatitudeRad();
    double long1 = first.getLongitudeRad();
    double lat2 = second.getLatitudeRad();
    double long2 = second.getLongitudeRad();

    double difLat = Math.abs(lat2 - lat1);
    double difLon = Math.abs(long2 - long1);

    // Haversine formula
    double a =
            Math.sin(difLat / 2) * Math.sin(difLat / 2)
                    + Math.cos(lat1) * Math.cos(lat2)
                    * Math.sin(difLon / 2) * Math.sin(difLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return radius * c;
  }

  /**
   * Calculates the distance between two LatLongBeans.
   *
   * @param first the first lat/long coord
   * @param second the second lat/long coord
   * @return the distance in meters between <code>first</code> and <code>second</code>
   */
  public static double distance(LatLongBean first, LatLongBean second) {
    WorldCoordinate firstCoord =
            new WorldCoordinate(first.getLatitude(), first.getLongitude(), 0);
    WorldCoordinate secondCoord =
            new WorldCoordinate(second.getLatitude(), second.getLongitude(), 0);
    return distance(firstCoord, secondCoord);
  }
}
