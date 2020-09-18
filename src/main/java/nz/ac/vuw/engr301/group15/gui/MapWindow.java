package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MapWindow {
  private JPanel rootPanel;
  private JButton backButton;
  private JPanel MapPanel;

  public JPanel getRootPanel() {
    return this.rootPanel;
  }

  public void setBackButton(ActionListener listener) {
    this.backButton.addActionListener(listener);
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }

}
