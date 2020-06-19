package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.SimulationStatus;
import nz.ac.vuw.engr301.group15.montecarlo.MonteCarloSimulation;

import javax.swing.*;
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
  }
}


