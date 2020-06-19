package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;

public class SimulationWindow {
  private JProgressBar progressBar1;
  private JPanel rootPanel;
  private JProgressBar progressBar2;
  private JButton abortTODOButton;
  private JButton cancelButton;

  public SimulationWindow() {

  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }

  public void uptickBar() {
    progressBar1.setValue(progressBar1.getValue() + 1);
  }

  public void setBar1Max(int max) {
    this.progressBar1.setMaximum(max);
  }

  public void resetBar() {
    this.progressBar1.setValue(0);
  }

  public void doUIStuff() {
    this.cancelButton.addActionListener(new ExitAction());
    this.progressBar1.setMaximum(100);
    this.progressBar1.setMinimum(0);
    resetBar();
  }
}
