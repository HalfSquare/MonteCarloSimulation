package nz.ac.vuw.engr301.group15.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class SettingsWindow {
  private JPanel rootPanel;
  private JTabbedPane simulationPane;
  private JFormattedTextField maxAngle;
  private JButton startButton;
  private JButton cancelButton;
  private JFormattedTextField numSimulations;
  private JFormattedTextField formattedTextField2;
  private JButton importOrkButton;
  private JButton importCsvButton;
  private JFormattedTextField windSpeed;
  private JFormattedTextField windDir;
  private JFormattedTextField windTurbulence;
  private JFormattedTextField launchTemp;
  private JFormattedTextField launchAirPressure;
  private JFormattedTextField launchRodAngle;
  private JFormattedTextField launchRodLength;
  private JFormattedTextField launchRodDir;
  private JFormattedTextField launchAlt;
  private JFormattedTextField launchLat;
  private JFormattedTextField launchLong;
  private JSpinner numClusters;


  public SettingsWindow() {

  }

  public void setNumSim(int num) {
    numSimulations.setValue(num);
  }

  public void setNumClusters(int num) {
    numClusters.setValue(num);
  }

  public int getNumClusters() {
    return (int) numClusters.getValue();
  }

  public void setStartButtonListener(ActionListener listener) {
    this.startButton.addActionListener(listener);
  }

  public void setImportCsvButton(ActionListener listener) {
    this.importCsvButton.addActionListener(listener);
  }

  public void setImportOrkButton(ActionListener listener) {
    this.importOrkButton.addActionListener(listener);
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public void setVisible(boolean flag) {
    rootPanel.setVisible(flag);
  }

  public void doUiStuff() {
    cancelButton.addActionListener(new ExitAction());
  }

  /**
   * Sets the mission control data.
   *
   * @param data the mission control data object
   */
  public void setData(MissionControlSettings data) {
    numSimulations.setText(data.getNumSimulations());
    maxAngle.setText(data.getMaxAngle());
    windTurbulence.setText(data.getWindTurbulence());
    windSpeed.setText(data.getWindSpeed());
    launchTemp.setText(data.getLaunchTemp());
    launchAirPressure.setText(data.getLaunchAirPressure());
    windDir.setText(data.getWindDir());
    launchRodDir.setText(data.getLaunchRodDir());
    launchAlt.setText(data.getLaunchAlt());
    launchLong.setText(data.getLaunchLong());
    launchLat.setText(data.getLaunchLat());
    launchRodLength.setText(data.getLaunchRodLength());
    launchRodAngle.setText(data.getLaunchRodAngle());
  }

  /**
   * Gets the mission control data and sets it in the
   * data object.
   *
   * @param data the mission control settings object to add the data to
   */
  public void getData(MissionControlSettings data) {
    data.setNumSimulations(numSimulations.getText());
    data.setMaxAngle(maxAngle.getText());
    data.setWindTurbulence(windTurbulence.getText());
    data.setWindSpeed(windSpeed.getText());
    data.setLaunchTemp(launchTemp.getText());
    data.setLaunchAirPressure(launchAirPressure.getText());
    data.setWindDir(windDir.getText());
    data.setLaunchRodDir(launchRodDir.getText());
    data.setLaunchAlt(launchAlt.getText());
    data.setLaunchLong(launchLong.getText());
    data.setLaunchLat(launchLat.getText());
    data.setLaunchRodLength(launchRodLength.getText());
    data.setLaunchRodAngle(launchRodAngle.getText());
  }

  /**
   * Returns the mission control settings.
   *
   * @return MissionControlSettings
   */
  public MissionControlSettings getSettings() {
    MissionControlSettings s = new MissionControlSettings();
    s.setNumSimulations(numSimulations.getText());
    s.setMaxAngle(maxAngle.getText());
    s.setWindTurbulence(windTurbulence.getText());
    s.setWindSpeed(windSpeed.getText());
    s.setLaunchTemp(launchTemp.getText());
    s.setLaunchAirPressure(launchAirPressure.getText());
    s.setWindDir(windDir.getText());
    s.setLaunchRodDir(launchRodDir.getText());
    s.setLaunchAlt(launchAlt.getText());
    s.setLaunchLong(launchLong.getText());
    s.setLaunchLat(launchLat.getText());
    s.setLaunchRodLength(launchRodLength.getText());
    s.setLaunchRodAngle(launchRodAngle.getText());

    return s;
  }

  /**
   * Works out if the mission control settings have been modified.
   *
   * @param data MissionControlSettings to test
   * @return true if the settings have been modified
   */
  public boolean isModified(MissionControlSettings data) {
    if (numSimulations.getText() != null
        ? !numSimulations.getText().equals(data.getNumSimulations()) :
        data.getNumSimulations() != null) {
      return true;
    }
    if (maxAngle.getText() != null ? !maxAngle.getText().equals(data.getMaxAngle()) :
        data.getMaxAngle() != null) {
      return true;
    }
    if (windTurbulence.getText() != null
        ? !windTurbulence.getText().equals(data.getWindTurbulence()) :
        data.getWindTurbulence() != null) {
      return true;
    }
    if (windSpeed.getText() != null ? !windSpeed.getText().equals(data.getWindSpeed()) :
        data.getWindSpeed() != null) {
      return true;
    }
    if (launchTemp.getText() != null ? !launchTemp.getText().equals(data.getLaunchTemp()) :
        data.getLaunchTemp() != null) {
      return true;
    }
    if (launchAirPressure.getText() != null
        ? !launchAirPressure.getText().equals(data.getLaunchAirPressure()) :
        data.getLaunchAirPressure() != null) {
      return true;
    }
    if (windDir.getText() != null ? !windDir.getText().equals(data.getWindDir()) :
        data.getWindDir() != null) {
      return true;
    }
    if (launchRodDir.getText() != null ? !launchRodDir.getText().equals(data.getLaunchRodDir()) :
        data.getLaunchRodDir() != null) {
      return true;
    }
    if (launchAlt.getText() != null ? !launchAlt.getText().equals(data.getLaunchAlt()) :
        data.getLaunchAlt() != null) {
      return true;
    }
    if (launchLong.getText() != null ? !launchLong.getText().equals(data.getLaunchLong()) :
        data.getLaunchLong() != null) {
      return true;
    }
    if (launchLat.getText() != null ? !launchLat.getText().equals(data.getLaunchLat()) :
        data.getLaunchLat() != null) {
      return true;
    }
    if (launchRodLength.getText() != null
        ? !launchRodLength.getText().equals(data.getLaunchRodLength()) :
        data.getLaunchRodLength() != null) {
      return true;
    }
    if (launchRodAngle.getText() != null
        ? !launchRodAngle.getText().equals(data.getLaunchRodAngle()) :
        data.getLaunchRodAngle() != null) {
      return true;
    }
    return false;
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
    rootPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
    simulationPane = new JTabbedPane();
    rootPanel.add(simulationPane,
        new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
            new Dimension(500, 400), null, 0, false));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
    simulationPane.addTab("Simulation Settings", panel1);
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(8, 6, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2,
        new GridConstraints(0, 0, 7, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JLabel label1 = new JLabel();
    label1.setText("Number of Simulations");
    label1.setToolTipText("The number of simulation points to generate");
    panel2.add(label1,
        new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label2 = new JLabel();
    label2.setText("Max Rocket Angle (radians)");
    panel2.add(label2,
        new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label3 = new JLabel();
    label3.setText("Placeholder");
    panel2.add(label3,
        new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label4 = new JLabel();
    label4.setText("Number of Clusters");
    label4.setToolTipText(
        "The most representative point from each cluster of points will be traced in 3D");
    panel2.add(label4,
        new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    numSimulations = new JFormattedTextField();
    numSimulations.setText("1000");
    numSimulations.setToolTipText("The number of simulation points to generate");
    panel2.add(numSimulations, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    formattedTextField2 = new JFormattedTextField();
    panel2.add(formattedTextField2, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JLabel label5 = new JLabel();
    label5.setText("Import Rocket Settings");
    panel2.add(label5,
        new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label6 = new JLabel();
    label6.setText("Import Mission Control Settings");
    panel2.add(label6,
        new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final Spacer spacer1 = new Spacer();
    panel2.add(spacer1, new GridConstraints(1, 3, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer2 = new Spacer();
    panel2.add(spacer2, new GridConstraints(1, 0, 6, 2, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer3 = new Spacer();
    panel2.add(spacer3, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer4 = new Spacer();
    panel2.add(spacer4, new GridConstraints(7, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer5 = new Spacer();
    panel2.add(spacer5, new GridConstraints(1, 5, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    importOrkButton = new JButton();
    importOrkButton.setText("Import (.ork)");
    panel2.add(importOrkButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    importCsvButton = new JButton();
    importCsvButton.setText("Import (.csv)");
    panel2.add(importCsvButton, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    maxAngle = new JFormattedTextField();
    panel2.add(maxAngle, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    numClusters = new JSpinner();
    numClusters.setToolTipText(
        "The most representative point from each cluster of points will be traced in 3D");
    panel2.add(numClusters, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    simulationPane.addTab("Weather Settings", panel3);
    final JPanel panel4 = new JPanel();
    panel4.setLayout(new GridLayoutManager(8, 6, new Insets(0, 0, 0, 0), -1, -1));
    panel3.add(panel4,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JLabel label7 = new JLabel();
    label7.setText("Wind Speed (m/s)");
    panel4.add(label7,
        new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label8 = new JLabel();
    label8.setText("Wind Direction (radians)");
    panel4.add(label8,
        new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label9 = new JLabel();
    label9.setText("Launch Temperature (kelvin)");
    panel4.add(label9,
        new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label10 = new JLabel();
    label10.setText("Wind Turbulence (m/s)");
    panel4.add(label10,
        new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    windTurbulence = new JFormattedTextField();
    windTurbulence.setText("");
    panel4.add(windTurbulence,
        new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    windSpeed = new JFormattedTextField();
    panel4.add(windSpeed,
        new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    launchTemp = new JFormattedTextField();
    panel4.add(launchTemp,
        new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JLabel label11 = new JLabel();
    label11.setText("Launch Air Pressure (mbar)");
    panel4.add(label11,
        new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label12 = new JLabel();
    label12.setText("Placeholder");
    panel4.add(label12,
        new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JFormattedTextField formattedTextField1 = new JFormattedTextField();
    panel4.add(formattedTextField1,
        new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    launchAirPressure = new JFormattedTextField();
    panel4.add(launchAirPressure,
        new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final Spacer spacer6 = new Spacer();
    panel4.add(spacer6, new GridConstraints(1, 3, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer7 = new Spacer();
    panel4.add(spacer7, new GridConstraints(1, 0, 6, 2, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer8 = new Spacer();
    panel4.add(spacer8, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer9 = new Spacer();
    panel4.add(spacer9, new GridConstraints(7, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer10 = new Spacer();
    panel4.add(spacer10, new GridConstraints(1, 5, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    windDir = new JFormattedTextField();
    panel4.add(windDir, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JPanel panel5 = new JPanel();
    panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    simulationPane.addTab("Launch Settings", panel5);
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayoutManager(8, 6, new Insets(0, 0, 0, 0), -1, -1));
    panel5.add(panel6,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JLabel label13 = new JLabel();
    label13.setText("Launch Rod Angle (radians)");
    panel6.add(label13,
        new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label14 = new JLabel();
    label14.setText("Launch Rod Length (m)");
    panel6.add(label14,
        new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label15 = new JLabel();
    label15.setText("Launch Altitude (m)");
    panel6.add(label15,
        new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label16 = new JLabel();
    label16.setText("Launch Rod Direction (radians)");
    panel6.add(label16,
        new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    launchRodDir = new JFormattedTextField();
    launchRodDir.setText("");
    panel6.add(launchRodDir,
        new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    launchAlt = new JFormattedTextField();
    panel6.add(launchAlt,
        new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JLabel label17 = new JLabel();
    label17.setText("Launch Latitude (N+, S-)");
    panel6.add(label17,
        new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label18 = new JLabel();
    label18.setText("Launch Longitude (E+, W-)");
    panel6.add(label18,
        new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    launchLong = new JFormattedTextField();
    panel6.add(launchLong,
        new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    launchLat = new JFormattedTextField();
    panel6.add(launchLat,
        new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final Spacer spacer11 = new Spacer();
    panel6.add(spacer11, new GridConstraints(1, 3, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer12 = new Spacer();
    panel6.add(spacer12, new GridConstraints(1, 0, 6, 2, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer13 = new Spacer();
    panel6.add(spacer13, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer14 = new Spacer();
    panel6.add(spacer14, new GridConstraints(7, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer15 = new Spacer();
    panel6.add(spacer15, new GridConstraints(1, 5, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    launchRodLength = new JFormattedTextField();
    panel6.add(launchRodLength, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    launchRodAngle = new JFormattedTextField();
    panel6.add(launchRodAngle, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JPanel panel7 = new JPanel();
    panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    simulationPane.addTab("Other Settings", panel7);
    final JPanel panel8 = new JPanel();
    panel8.setLayout(new GridLayoutManager(8, 6, new Insets(0, 0, 0, 0), -1, -1));
    panel7.add(panel8,
        new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null,
            null, 0, false));
    final JLabel label19 = new JLabel();
    label19.setText("Placeholder");
    panel8.add(label19,
        new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label20 = new JLabel();
    label20.setText("Placeholder");
    panel8.add(label20,
        new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JTextField textField1 = new JTextField();
    panel8.add(textField1,
        new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JLabel label21 = new JLabel();
    label21.setText("Placeholder");
    panel8.add(label21,
        new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label22 = new JLabel();
    label22.setText("Placeholder");
    panel8.add(label22,
        new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JFormattedTextField formattedTextField3 = new JFormattedTextField();
    formattedTextField3.setText("");
    panel8.add(formattedTextField3,
        new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JFormattedTextField formattedTextField4 = new JFormattedTextField();
    panel8.add(formattedTextField4,
        new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JFormattedTextField formattedTextField5 = new JFormattedTextField();
    panel8.add(formattedTextField5,
        new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JLabel label23 = new JLabel();
    label23.setText("Placeholder");
    panel8.add(label23,
        new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JLabel label24 = new JLabel();
    label24.setText("Placeholder");
    panel8.add(label24,
        new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0,
            false));
    final JFormattedTextField formattedTextField6 = new JFormattedTextField();
    panel8.add(formattedTextField6,
        new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final JFormattedTextField formattedTextField7 = new JFormattedTextField();
    panel8.add(formattedTextField7,
        new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null,
            new Dimension(150, -1), null, 0, false));
    final Spacer spacer16 = new Spacer();
    panel8.add(spacer16, new GridConstraints(1, 3, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer17 = new Spacer();
    panel8.add(spacer17, new GridConstraints(1, 0, 6, 2, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    final Spacer spacer18 = new Spacer();
    panel8.add(spacer18, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer19 = new Spacer();
    panel8.add(spacer19, new GridConstraints(7, 0, 1, 6, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0,
        false));
    final Spacer spacer20 = new Spacer();
    panel8.add(spacer20, new GridConstraints(1, 5, 6, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null,
        0, false));
    startButton = new JButton();
    startButton.setText("Start");
    startButton.setToolTipText("Runs the simulation with the current settings");
    rootPanel.add(startButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
        GridConstraints.FILL_HORIZONTAL,
        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
        GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    cancelButton = new JButton();
    cancelButton.setText("Cancel");
    rootPanel.add(cancelButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
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
