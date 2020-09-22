package nz.ac.vuw.engr301.group15.montecarlo;

import net.sf.openrocket.file.RocketLoadException;
import nz.ac.vuw.engr301.group15.gui.MissionControlSettings;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class SimulationBatch implements Runnable {
  private final InputStream rocketFile;
  private final MissionControlSettings settings;
  private Thread t;
  private final String name;
  private final int simsInBatch;
  private int simsRun;
  private final Runnable onStep;
  private final Runnable onFinish;

  private ArrayList<SimulationDuple> data;

  public SimulationBatch(String name, int simsInBatch, InputStream rocketFile, MissionControlSettings settings, Runnable onStep, Runnable onFinish) {
    this.name = name;
    this.simsInBatch = simsInBatch;
    this.onStep = onStep;
    this.onFinish = onFinish;
    this.rocketFile = rocketFile;
    this.settings = settings.clone();

    this.settings.setNumSimulations(String.valueOf(simsInBatch));

    data = new ArrayList<>();
  }

  @Override
  public void run() {
    MonteCarloSimulation mcs = new MonteCarloSimulation(onStep);

    try {
      data = mcs.runSimulations(rocketFile, settings);
//      rocketFile.close();
    } catch (RocketLoadException e) {
      e.printStackTrace();
    }

//    System.out.println(name + " done");

    // Run the onFinish Runnable
    if (onFinish != null) {
      onFinish.run();
    }
  }

  public int getSimsRun() {
    return simsRun;
  }

  public void start() {
    System.out.println("Starting: " + name);
    if (t == null) {
      t = new Thread(this, name);
      t.start();
    }
  }

  public Collection<? extends SimulationDuple> getData() {
    return data;
  }
}
