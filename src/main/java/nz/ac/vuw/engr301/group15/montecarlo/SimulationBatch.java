package nz.ac.vuw.engr301.group15.montecarlo;

public class SimulationBatch implements Runnable {
  private Thread t;
  private final String name;
  private final int simsInBatch;
  private int simsRun;
  private final Runnable onStep;
  private final Runnable onFinish;

  public SimulationBatch(String name, int simsInBatch, Runnable onStep, Runnable onFinish) {
    this.name = name;
    this.simsInBatch = simsInBatch;
    this.onStep = onStep;
    this.onFinish = onFinish;
  }

  @Override
  public void run() {
    for (simsRun = 1; simsRun <= simsInBatch; simsRun++) {
      System.out.println(name + ": " + simsRun);
      if (onStep != null) {
        onStep.run();
      }
    }

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

  public static void main(String[] args) {
    SimulationBatch batch1 = new SimulationBatch("Batch 1", 500, null, () -> System.out.println("Done 1"));
    SimulationBatch batch2 = new SimulationBatch("Batch 2", 700, null, () -> System.out.println("Done 2"));

    batch1.start();
    batch2.start();
  }
}
