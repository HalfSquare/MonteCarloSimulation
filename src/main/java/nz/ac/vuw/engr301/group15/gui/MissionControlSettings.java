package nz.ac.vuw.engr301.group15.gui;

/**
 Mission Control Settings contains the values that can be inputted into the simulation to
 change the simulation results to be more accurate to flight conditions.


 Max Angle: maximum step angle of the rocket. Should be converted to radians before handing to OpenRocket - should be
 between (0.017453292519943295, 0.3490658503988659), or between (1, 20) degrees otherwise OpenRocket will clamp to
 these values.

 Wind Direction:

 Wind Turbulence:

 Wind Speed:

 Launch Temperature: temperature of the launch site. Read in as a celcius value, should be converted to kelvin
 before handing to OpenRocket.

 Launch Air Pressure:

 Launch Rod Length: this is probably measured in metres, must be between (0.0, 1.0471975511965976)
 otherwise OpenRocket will clamp to these values.

 Launch Rod Direction:

 Launch Rod Angle:

 Launch Altitude:

 Launch Latitude:

 Launch Longitude:

 Number of Simulations:
 */

public class MissionControlSettings {
  private String maxAngle;
  private String windDir;
  private String windTurbulence;
  private String windSpeed;
  private String launchTemp;
  private String launchAirPressure;
  private String launchRodLength;
  private String launchRodDir;
  private String launchRodAngle;
  private String launchAlt;
  private String launchLong;
  private String launchLat;
  private String numSimulations;

  public MissionControlSettings() {
  }

  public String getMaxAngle() {
    return maxAngle;
  }

  public void setMaxAngle(final String maxAngle) {
    this.maxAngle = maxAngle;
  }

  public String getWindDir() {
    return windDir;
  }

  public void setWindDir(final String windDir) {
    this.windDir = windDir;
  }

  public String getWindTurbulence() {
    return windTurbulence;
  }

  public void setWindTurbulence(final String windTurbulence) {
    this.windTurbulence = windTurbulence;
  }

  public String getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(final String windSpeed) {
    this.windSpeed = windSpeed;
  }

  public String getLaunchTemp() {
    return launchTemp;
  }

  public void setLaunchTemp(final String launchTemp) {
    this.launchTemp = launchTemp;
  }

  public String getLaunchAirPressure() {
    return launchAirPressure;
  }

  public void setLaunchAirPressure(final String launchAirPressure) {
    this.launchAirPressure = launchAirPressure;
  }

  public String getLaunchRodLength() {
    return launchRodLength;
  }

  public void setLaunchRodLength(final String launchRodLength) {
    this.launchRodLength = launchRodLength;
  }

  public String getLaunchRodDir() {
    return launchRodDir;
  }

  public void setLaunchRodDir(final String launchRodDir) {
    this.launchRodDir = launchRodDir;
  }

  public String getLaunchRodAngle() {
    return launchRodAngle;
  }

  public void setLaunchRodAngle(final String launchRodAngle) {
    this.launchRodAngle = launchRodAngle;
  }

  public String getLaunchAlt() {
    return launchAlt;
  }

  public void setLaunchAlt(final String launchAlt) {
    this.launchAlt = launchAlt;
  }

  public String getLaunchLong() {
    return launchLong;
  }

  public void setLaunchLong(final String launchLong) {
    this.launchLong = launchLong;
  }

  public String getLaunchLat() {
    return launchLat;
  }

  public void setLaunchLat(final String launchLat) {
    this.launchLat = launchLat;
  }

  public String getNumSimulations() {
    return numSimulations;
  }

  public void setNumSimulations(final String numSimulations) {
    this.numSimulations = numSimulations;
  }
}