package nz.ac.vuw.engr301.group15.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class SettingsWindow {
  private JPanel rootPanel;
  private JTabbedPane tabbedPane1;
  private JTextField textField2;
  private JButton startButton;
  private JButton cancelButton;
  private JFormattedTextField formattedTextField80;
  private JFormattedTextField numberOfSimulations;
  private JFormattedTextField formattedTextField2;
  private JFormattedTextField formattedTextField3;
  private JFormattedTextField formattedTextField4;


  public SettingsWindow() {

  }

  public void setNumSim(int num) {
    numberOfSimulations.setValue(num);
  }

  public void setStartButtonListener(ActionListener listener) {
    this.startButton.addActionListener(listener);
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }

  public void doUiStuff() {
    cancelButton.addActionListener(new ExitAction());
    numberOfSimulations.setEditable(false);
  }
}
