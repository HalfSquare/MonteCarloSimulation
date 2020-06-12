import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

  public enum State {
    SETTINGS,
    SIMULATION,
    GRAPH
  }

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;

  private State state;
  private Timer timer;

  public GUI() {
    this.state = State.SETTINGS;

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setSize(600, 300);

    settingsWindow = new SettingsWindow();
    simulationWindow = new SimulationWindow();
    graphWindow = new GraphWindow();

    setState(State.SETTINGS);

    this.setVisible(true);
  }

  private void startSettings() {
    settingsWindow.setStartButtonListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setState(State.SIMULATION);
      }
    });

    this.add(settingsWindow.getRootPanel());
  }

  private void startSimulation() {
      // Simulation Window
      this.add(simulationWindow.getRootPanel());
      settingsWindow.setVisible(false);


      // Simulation stuff
      timer = new Timer(10, null);
      ActionListener updateBar = new ActionListener() {
        private int bar = 1;

        @Override
        public void actionPerformed(ActionEvent e) {
          if (bar <= 500) {
            simulationWindow.bar(bar++);
          } else {
            System.out.println("Timer done");
            setState(State.GRAPH);
            timer.stop();
          }
        }
      };
      timer.addActionListener(updateBar);
      timer.start();
  }

  private void startGraph() {
    simulationWindow.setVisible(false);
    this.add(graphWindow.getRootPanel());
  }

  private void setState(State state) {
    this.state = state;
    switch (this.state) {
      case SETTINGS:
        startSettings();
        break;
      case SIMULATION:
        startSimulation();
        break;
      case GRAPH:
        startGraph();
        break;
      default:
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
}
