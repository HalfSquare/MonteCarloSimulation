package nz.ac.vuw.engr301.group15.gui;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Mission Control Settings contains the values that can be inputted into the simulation to change
 * the simulation results to be more accurate to flight conditions.
 * <p>
 * <p>
 * Max Angle: maximum step angle of the rocket. Should be converted to radians before handing to
 * OpenRocket - should be between (0.017453292519943295, 0.3490658503988659), or between (1, 20)
 * degrees otherwise OpenRocket will clamp to these values.
 * <p>
 * Wind Direction: direction of the wind at launch site, measured in degrees. While OpenRocket
 * supports this, the SimulationOptions class does not - this is odd, and may need to be added in.
 * Currently not sure which direction 0 degrees is - assumption is North-facing.
 * <p>
 * Wind Turbulence: wind turbulence intensity of launch site, used to calculate wind speed deviation
 * (average * turbulence). Percentage value, 100% = 2.0 m/s standard deviation, 10% = 0.2 m/s
 * standard deviation - however should be handed to OpenRocket as a decimal.
 * <p>
 * Wind Speed: average wind speed of launch site, measured in m/s. Min value = 0.0, Max value =
 * 135.
 * <p>
 * Launch Temperature: temperature of the launch site. Read in as a Celcius value, should be
 * converted to kelvin before handing to OpenRocket.
 * <p>
 * Launch Air Pressure: air pressure of the launch site, used in conjunction with launch temperature
 * to calculate atmosphere, measured in mbar (Millibar Pressure Unit).
 * <p>
 * Launch Rod Length: this is probably measured in metres, must be between (0.0, 1.0471975511965976)
 * otherwise OpenRocket will clamp to these values.
 * <p>
 * Launch Rod Direction: direction of the rocket, measured in radians, assumption is that 0 is
 * North-facing.
 * <p>
 * Launch Rod Angle: tilt of the rocket downwards, measured in radians, assumption is that 0 is
 * straight sky-facing. Max value: 1.0471975511965976
 * <p>
 * Launch Altitude: altitude of the launch site, measured in metres.
 * <p>
 * Launch Latitude: standard coordinate measurement, South values should be negative and North
 * values positive.
 * <p>
 * Launch Longitude: standard coordinate measurement, West values should be negative and East values
 * positive.
 * <p>
 * Number of Simulations: number of Monte Carlo simulations to be run.
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
  private HashMap<String, ArrayList<String>> errorMap = new HashMap<>(); // hash map storing the errors

  public MissionControlSettings() {
  }

  public HashMap<String, ArrayList<String>> getErrorMap() {
    return errorMap;
  }

  public boolean hasErrors() {
    return errorsFound;
  }

  public void setErrorsFound(boolean errorsFound) {
    this.errorsFound = errorsFound;
  }

  private boolean errorsFound = false; // error checker


  /**
   * Adds the invalid value to the errorMap
   *
   * @param type  Type of Data
   * @param value Invalid data value
   */
  public void addError(String type, String value) {
    // set errors found
    setErrorsFound(true);
    System.out.println("ERROR:" + type + ": " + value);
    // create a new arrayList if the error type is new
    if (!errorMap.containsKey(type)) {
      errorMap.put(type, new ArrayList<>());
    }
    // add the value to the arrayList
    errorMap.get(type).add(value);
  }

  public boolean isBetween(Double min, Double max, String stringValue) {
    if (stringValue.equals("")){
      return true;
    }
    double value = Double.parseDouble(stringValue);
    return ((min <= value && value <= max));
  }

  public String getMaxAngle() {
    return maxAngle;
  }

  public void setMaxAngle(final String maxAngle) {
    if (isBetween(0.017453292519943295, 0.3490658503988659, maxAngle)) {
      this.maxAngle = maxAngle;
    } else {
      addError("Max Angle", maxAngle);
      this.maxAngle = ""; // default?
    }
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
    if (isBetween(0.0, 135.0, windSpeed)) {
      this.windSpeed = windSpeed;
    } else {
      addError("Wind Speed", windSpeed);
      this.windSpeed = ""; // default?
    }
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
    if (isBetween(0.0, 1.0471975511965976, launchRodLength)) {
      this.launchRodLength = launchRodLength;
    } else {
      addError("Launch Rod Length", launchRodLength);
      this.launchRodLength = ""; // default?
    }
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
    if (isBetween(0.0, 1.0471975511965976, launchRodAngle)) {
      this.launchRodAngle = launchRodAngle;
    } else {
      addError("Launch Rod Angle", launchRodAngle);
      this.launchRodAngle = ""; // default?
    }
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
    if (Integer.parseInt(numSimulations) > 0) {
      this.numSimulations = numSimulations;
    } else {
      addError("Number of Simulations", numSimulations);
      this.numSimulations = "0";
    }
  }
}