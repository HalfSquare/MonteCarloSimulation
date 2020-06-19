package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame {

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";

  public static final int NUM_SIMS = 100;
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

    setState(SETTINGS);

    this.setVisible(true);
    //showOnScreen(2, this);
  }

  private void startSettings() {
    settingsWindow.setStartButtonListener(e -> setState(SIMULATION));
    this.add(settingsWindow.getRootPanel());
  }

  private void startSimulation() {
    // Simulation Window
    this.add(simulationWindow.getRootPanel());
    simulationWindow.setBar1Max(NUM_SIMS);

    // Simulation stuff
    SimulationRunner runner = new SimulationRunner();
    runner.start();

  }

  private void startGraph() {
    GraphType graphType = GraphType.CIRCLE;

    this.add(graphWindow.getRootPanel());
    graphWindow.setReRunButtonListener(e -> setState(SETTINGS));
  }

  private void setState(String state) {
    settingsWindow.setVisible(false);
    simulationWindow.setVisible(false);
    graphWindow.setVisible(false);

    if (SETTINGS.equals(state)) {
      settingsWindow.setVisible(true);
      startSettings();
    } else if (SIMULATION.equals(state)) {
      simulationWindow.setVisible(true);
      startSimulation();
    } else if (GRAPH.equals(state)) {
      graphWindow.setVisible(true);
      startGraph();
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
      System.out.println("Starting ");
      if (t == null) {
        t = new Thread(this, "sim");
        t.start();
      }
    }
  }
}


