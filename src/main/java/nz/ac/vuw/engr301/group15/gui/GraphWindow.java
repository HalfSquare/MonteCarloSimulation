package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GraphWindow {
  private JComboBox<String> graphTypeComboBox;
  private JButton saveToFileButton;
  private JPanel rootPanel;
  private JButton exitButton;
  private JButton reRunButton;
  private JPanel graphPanel;

  /**
   * Constructor.
   */
  public GraphWindow(){

  }

  public JPanel getRootPanel() {
    return this.rootPanel;
  }

  public JPanel getGraphPanel() {
    return this.graphPanel;
  }

  public void setReRunButtonListener(ActionListener listener) {
    this.reRunButton.addActionListener(listener);
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }


  public void doUIStuff() {
    exitButton.addActionListener(new ExitAction());
  }
}
