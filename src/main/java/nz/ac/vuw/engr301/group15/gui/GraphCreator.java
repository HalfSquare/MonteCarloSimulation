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
                //return make3Dgraph();
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

    //private make3DGraph
}
