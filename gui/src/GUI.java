import net.sf.openrocket.MonteCarlo.MonteCarloSimulation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";

  public enum GraphType{
    CIRCLE, SQUARE, ROCKET
  }

  private Timer timer;

  public GUI() {
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
    settingsWindow.setStartButtonListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setState(SIMULATION);
      }
    });
    this.add(settingsWindow.getRootPanel());
  }

  private void startSimulation() {
      // Simulation Window
      this.add(simulationWindow.getRootPanel());

      simulationWindow.bar(0);

      // Simulation stuff


      timer = new Timer(1, null);
      ActionListener updateBar = new ActionListener() {
        private int bar = 1;
        public void actionPerformed(ActionEvent e) {
          if (bar <= 500) {
            simulationWindow.bar(bar++);
          } else {
            timer.stop();
            setState(GRAPH);
          }
        }
      };
      timer.addActionListener(updateBar);
      timer.start();
  }

  private void startGraph() {
    GraphType graphType = GraphType.CIRCLE;
    //graphWindow.

    this.add(graphWindow.getRootPanel());
    graphWindow.setReRunButtonListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setState(SETTINGS);
      }
    });
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
      MonteCarloSimulation.main(null);
      startGraph();
      graphWindow.setVisible(true);
    } else {
      throw new RuntimeException("Unexpected state switch");
    }
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    GUI gui = new GUI();
  }

//  //Method to show on second monitor
//  //TODO: remove
//  public static void showOnScreen( int screen, JFrame frame )
//  {
//    GraphicsEnvironment ge = GraphicsEnvironment
//            .getLocalGraphicsEnvironment();
//    GraphicsDevice[] gs = ge.getScreenDevices();
//    if( screen > -1 && screen < gs.length )
//    {
//      gs[screen].setFullScreenWindow( frame );
//    }
//    else if( gs.length > 0 )
//    {
//      gs[0].setFullScreenWindow( frame );
//    }
//    else
//    {
//      throw new RuntimeException( "No Screens Found" );
//    }
//  }
}


