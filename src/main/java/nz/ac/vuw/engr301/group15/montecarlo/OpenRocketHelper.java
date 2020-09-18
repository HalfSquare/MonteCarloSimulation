package nz.ac.vuw.engr301.group15.montecarlo;

import java.io.InputStream;
import net.sf.openrocket.document.OpenRocketDocument;
import net.sf.openrocket.document.Simulation;
import net.sf.openrocket.file.GeneralRocketLoader;
import net.sf.openrocket.file.RocketLoadException;
import net.sf.openrocket.simulation.exception.SimulationException;
import net.sf.openrocket.simulation.listeners.AbstractSimulationListener;

/**
 * An OpenRocket Helper.
 *
 * @author Michael, Georgia
 */
public class OpenRocketHelper {

  public OpenRocketHelper() {
  }

  /**
   * Load .ork document.
   *
   * @param rocketFile  Name of file to load
   */
  public OpenRocketDocument loadOrDocument(InputStream rocketFile) throws RocketLoadException {
    GeneralRocketLoader gen = new GeneralRocketLoader();
    return gen.load(rocketFile);
  }

  /**
   * Runs a single simulation.
   *
   * @param simulation    Simulation object to run
   * @param listener      Listener to add to simulation
   */
  public void runSimulation(Simulation simulation, AbstractSimulationListener listener) {
    simulation.getOptions().randomizeSeed();
    try {
      simulation.simulate(listener);
    } catch (SimulationException exception) {
      exception.printStackTrace();
    }
  }

}
