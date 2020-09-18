package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MapWindow extends JPanel {
  private JPanel rootPanel;
  private JButton backButton;
  private JPanel mapPanel;
  private JLabel label;

  public JPanel getRootPanel() {
    return this.rootPanel;
  }

  public void setBackButton(ActionListener listener) {
    this.backButton.addActionListener(listener);
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }

  public void setMapImage(BufferedImage map) {
    label.setIcon(new ImageIcon(map));
  }
}
