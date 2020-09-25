package nz.ac.vuw.engr301.group15.gui;

import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GraphWindow {
  private JComboBox<String> graphTypeComboBox;
  private JPanel rootPanel;
  private JButton exitButton;
  private JButton reRunButton;
  private JPanel graphPanel;
  private JButton saveImageToFileButton;
  private JButton csvExportButton;
  private JButton savePointsAsCsvButton;

  public JPanel getRootPanel() {
    return this.rootPanel;
  }

  public JPanel getGraphPanel() {
    return this.graphPanel;
  }

  public void setReRunButtonListener(ActionListener listener) {
    this.reRunButton.addActionListener(listener);
  }

  public void setSaveImageToFileButton(ActionListener listener) {
    this.saveImageToFileButton.addActionListener(listener);
  }

  public void setGraphTypeComboBoxListener(ActionListener listener) {
    this.graphTypeComboBox.addActionListener(listener);
  }

  public void resetGraphPanel() {
    this.graphPanel.removeAll();
  }

  public String getGraphTypeComboBox() {
    return Objects.requireNonNull(graphTypeComboBox.getSelectedItem()).toString();
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }

  public void setCsvButtonListener(ActionListener listener) {
    this.csvExportButton.addActionListener(listener);
  }

  public void setSavePointsAsCsvButton(ActionListener listener) {
    this.savePointsAsCsvButton.addActionListener(listener);
  }

  public void doUiStuff() {
    exitButton.addActionListener(new ExitAction());
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return rootPanel;
  }

}
