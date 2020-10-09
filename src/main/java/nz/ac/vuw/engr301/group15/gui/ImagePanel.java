package nz.ac.vuw.engr301.group15.gui;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class ImagePanel extends JPanel {

  private BufferedImage img;

  public ImagePanel(BufferedImage image){
    this.img = image;
  }

  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(img, 0, 0, this);
  }
}
