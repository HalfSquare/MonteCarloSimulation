import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

  private final SettingsWindow settingsWindow;
  private final SimulationWindow simulationWindow;
  private final GraphWindow graphWindow;

  public static final String SETTINGS = "SETTINGS";
  public static final String SIMULATION = "SIMULATION";
  public static final String GRAPH = "GRAPH";

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
      @Override
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

        @Override
        public void actionPerformed(ActionEvent e) {
          if (bar <= 500) {
            simulationWindow.bar(bar++);
          } else {
            //System.out.println("Timer done"); //shhh
            timer.stop();
            setState(GRAPH);
          }
        }
      };
      timer.addActionListener(updateBar);
      timer.start();
  }

  private void startGraph() {
    this.add(graphWindow.getRootPanel());
    graphWindow.setReRunButtonListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setState(SETTINGS);
      }
    });
  }

  private void setState(String state) {
    settingsWindow.setVisible(false);
    simulationWindow.setVisible(false);
    graphWindow.setVisible(false);

    switch (state) {
      case SETTINGS:
        startSettings();
        settingsWindow.setVisible(true);
        break;
      case SIMULATION:
        startSimulation();
        simulationWindow.setVisible(true);
        break;
      case GRAPH:
        startGraph();
        graphWindow.setVisible(true);
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


