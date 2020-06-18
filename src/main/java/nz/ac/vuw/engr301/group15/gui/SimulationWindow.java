package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;

public class SimulationWindow {
  private JProgressBar progressBar1;
  private JPanel rootPanel;
  private JProgressBar progressBar2;
  private JButton button1;
  private JButton cancelButton;

  public SimulationWindow() {
    this.cancelButton.addActionListener(new ExitAction());
    this.progressBar1.setMaximum(500);
    this.progressBar1.setMinimum(0);
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }

  public void bar(int n) {
    progressBar1.setValue(n);
  }
}
