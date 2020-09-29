package nz.ac.vuw.engr301.group15.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.jfree.chart.ChartPanel;


public class GraphWindow {
  private JComboBox<String> graphTypeComboBox;
  private JPanel rootPanel;
  private JButton exitButton;
  private JButton reRunButton;
  private JPanel graphPanel;
  private JButton saveImageToFileButton;
  private JButton csvExportButton;
  private JButton viewOnMapButton;
  private JButton savePointsAsCsvButton;

  private JTable simulationTable;

  /**
   * Constructor.
   */
  public GraphWindow() {

  }

  public JTable getSimulationTable() {
    return simulationTable;
  }

  public void addToGraph(ChartPanel chartPanel) {
    graphPanel.add(chartPanel);
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

  public void setViewOnMapButton(ActionListener listener) {
    this.viewOnMapButton.addActionListener(listener);
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
