package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class MapWindow extends JPanel{
  private JPanel rootPanel;
  private JButton backButton;
  private JPanel mapPanel;
  private JLabel label;
  private BufferedImage mapImage;

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
//    MapPanel.add(new JLabel(new ImageIcon(map)));

    mapImage = map;

//    JLabel label = new JLabel(new ImageIcon(map));
    label.setIcon(new ImageIcon(map));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(mapImage, 0, 0, this);
  }
}
