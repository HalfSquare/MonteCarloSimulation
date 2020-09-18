package nz.ac.vuw.engr301.group15.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;

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
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return rootPanel;
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
}
