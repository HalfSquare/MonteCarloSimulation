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
  private JButton saveToFileButton;
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

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    rootPanel = new JPanel();
    rootPanel.setLayout(new GridLayoutManager(3, 6, new Insets(0, 0, 0, 0), -1, -1));
    graphTypeComboBox = new JComboBox();
    final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
    defaultComboBoxModel1.addElement("Circle");
    defaultComboBoxModel1.addElement("Square");
    defaultComboBoxModel1.addElement("Cross");
    defaultComboBoxModel1.addElement("3D");
    graphTypeComboBox.setModel(defaultComboBoxModel1);
    rootPanel.add(graphTypeComboBox, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    graphPanel = new JPanel();
    graphPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    rootPanel.add(graphPanel,
        new GridConstraints(1, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final Spacer spacer1 = new Spacer();
    rootPanel.add(spacer1, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    exitButton = new JButton();
    exitButton.setText("Exit");
    rootPanel.add(exitButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    saveToFileButton = new JButton();
    saveToFileButton.setText("Save to file");
    rootPanel.add(saveToFileButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    reRunButton = new JButton();
    reRunButton.setText("Re-run");
    rootPanel.add(reRunButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    saveImageToFileButton = new JButton();
    saveImageToFileButton.setText("Save as image");
    rootPanel.add(saveImageToFileButton,
        new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    csvExportButton = new JButton();
    csvExportButton.setText("Export Settings");
    rootPanel.add(csvExportButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    savePointsAsCsvButton = new JButton();
    savePointsAsCsvButton.setText("Save Points as CSV");
    rootPanel.add(savePointsAsCsvButton,
        new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
            GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    viewOnMapButton = new JButton();
    viewOnMapButton.setText("View on Map");
    rootPanel.add(viewOnMapButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return rootPanel;
  }

}
