package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;
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
import java.util.ArrayList;

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
        if(graphWindow.getGraphTypeComboBox().equals("3D")){
            graphType = Gui.GraphType.FLIGHTPATH;
        }
        else {
            graphType = Gui.GraphType.valueOf(graphWindow.getGraphTypeComboBox().toUpperCase());
        }

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
                graphWindow.getGraphPanel().setLayout(new BorderLayout());
                graphWindow.getGraphPanel().add(create3DGraph(), BorderLayout.CENTER);
                graphWindow.getGraphPanel().validate();
                break;
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
    public static JPanel create3DGraph() {
        XYZDataset dataset = create3DDataset();
        Chart3D chart = createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        return chartPanel;
        //chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
//        content.add(new DisplayPanel3D(chartPanel));
//        return content;
    }

    private static Chart3D createChart(XYZDataset dataset) {
        Chart3D chart = Chart3DFactory.createXYZLineChart("XYZ Line Chart Demo",
                "Orson Charts", dataset, "Day", "Index", "Station");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(15, 3, 8));
        NumberAxis3D zAxis = (NumberAxis3D) plot.getZAxis();
        zAxis.setTickSelector(new IntegerTickSelector());
        return chart;
    }

    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     *
     * @return A sample dataset.
     */
    public static XYZDataset<String> create3DDataset() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();

//        for (int s = 1; s < 1; s++) {
//            XYZSeries<String> series = new XYZSeries<String>("Series " + s);
//            double y = 1.0;
//            for (int i = 0; i < 3000; i++) {
//                y = y * (1.0 + (Math.random() - 0.499) / 10.0);
//                series.add(i, y, s);
//            }
//            dataset.add(series);
//        }

        XYZSeries<String> series = new XYZSeries<String>("Series");
        series.add(10,10,10);
        series.add(40,40,40);
        dataset.add(series);

        return dataset;
    }
}
