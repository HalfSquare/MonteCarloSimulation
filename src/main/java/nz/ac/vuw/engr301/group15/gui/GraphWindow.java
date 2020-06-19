package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GraphWindow {
  private JComboBox<String> graphTypeComboBox;
  private JButton saveToFileButton;
  private JPanel rootPanel;
  private JButton exitButton;
  private JButton reRunButton;

  /**
   * Constructor.
   */
  public GraphWindow(){

  }

  public JPanel getRootPanel() {
    return rootPanel;
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
