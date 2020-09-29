package nz.ac.vuw.engr301.group15.gui;

import java.util.ArrayList;
import org.apache.commons.lang.math.NumberUtils;

/**
 Mission Control Settings contains the values that can be inputted into the simulation to
 change the simulation results to be more accurate to flight conditions.


 Max Angle: maximum step angle of the rocket.
 Should be converted to radians before handing to OpenRocket - should be between
 (0.017453292519943295, 0.3490658503988659),
 or between (1, 20) degrees otherwise OpenRocket will clamp to these values.

 Wind Direction: direction of the wind at launch site, measured in degrees.
 While OpenRocket supports this, the SimulationOptions class
  does not - this is odd, and may need to be added in.
 Currently not sure which direction 0 degrees is - assumption is North-facing.

 Wind Turbulence: wind turbulence intensity of launch site,
 used to calculate wind speed deviation (average * turbulence).
 Percentage value, 100% = 2.0 m/s standard deviation,
 10% = 0.2 m/s standard deviation - however should be handed to
 OpenRocket as a decimal.

 Wind Speed: average wind speed of launch site, measured in m/s.

 Launch Temperature: temperature of the launch site.
 Read in as a Celcius value, should be converted to kelvin
  before handing to OpenRocket.

 Launch Air Pressure: air pressure of the launch site,
 used in conjunction with launch temperature to calculate atmosphere,
 measured in mbar (Millibar Pressure Unit).

 Launch Rod Length: this is probably measured in metres, must be between (0.0, 1.0471975511965976)
  otherwise OpenRocket will clamp to these values.

 Launch Rod Direction: direction of the rocket, measured in radians,
 assumption is that 0 is North-facing.

 Launch Rod Angle: tilt of the rocket downwards, measured in radians,
 assumption is that 0 is straight sky-facing.
 Max value: 1.0471975511965976

 Launch Altitude: altitude of the launch site, measured in metres.

 Launch Latitude: standard coordinate measurement,
 South values should be negative and North values positive.

 Launch Longitude: standard coordinate measurement,
 West values should be negative and East values positive.
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
  private final ArrayList<String> errorList = new ArrayList<>(); // hash map storing the errors
  private boolean errorsFound = false; // error checker


  public MissionControlSettings() {
  }

  public ArrayList<String> getErrors() {
    return errorList;
  }

  public boolean hasErrors() {
    return errorsFound;
  }

  public void setErrorsFound(boolean errorsFound) {
    this.errorsFound = errorsFound;
  }

  /**
   * Checks whether a number is valid.
   * Invalid numbers are added to the error list.
   *
   * @param stringValue the string to be checked as a number
   * @param type the data field number belongs to
   *
   * @return true if valid, false if invalid
   */
  private boolean isValidCheck(String stringValue, String type) {
    if (!stringValue.equals("")) {
      if (!NumberUtils.isNumber(stringValue)) {
        setErrorsFound(true);
        errorList
          .add("Invalid " + type + " value: " + stringValue + ". Must be a valid number");
        return false;
      }
    }
    return true;
  }

  public String getMaxAngle() {
    return maxAngle;
  }

  /**
   * Sets maximum angle when it is valid.
   *
   * @param maxAngle The value to check if it is valid.
   */
  public void setMaxAngle(final String maxAngle) {
    if (isValidCheck(maxAngle, "Max Angle")) {
      this.maxAngle = maxAngle;
    } else {
      this.maxAngle = "";
    }
  }

  public String getWindDir() {
    return windDir;
  }

  /**
   * Sets wind direction when it is valid.
   *
   * @param windDir The value to check if it is valid.
   */
  public void setWindDir(final String windDir) {
    if (isValidCheck(windDir, "Wind Dir")) {
      this.windDir = windDir;
    } else {
      this.windDir = "";
    }
  }

  public String getWindTurbulence() {
    return windTurbulence;
  }

  /**
   * Sets wind turbulence when it is valid.
   *
   * @param windTurbulence The value to check if it is valid.
   */
  public void setWindTurbulence(final String windTurbulence) {
    if (isValidCheck(windTurbulence, "Wind Turbulence")) {
      this.windTurbulence = windTurbulence;
    } else {
      this.windTurbulence = "";
    }
  }

  public String getWindSpeed() {
    return windSpeed;
  }

  /**
   * Sets wind speed when it is valid.
   *
   * @param windSpeed The value to check if it is valid.
   */
  public void setWindSpeed(final String windSpeed) {
    if (isValidCheck(windSpeed, "Wind Speed")) {
      this.windSpeed = windSpeed;
    } else {
      this.windSpeed = "";
    }

  }

  public String getLaunchTemp() {
    return launchTemp;
  }

  /**
   * Sets launch temperature when it is valid.
   *
   * @param launchTemp The value to check if it is valid.
   */
  public void setLaunchTemp(final String launchTemp) {
    if (isValidCheck(launchTemp, "Launch Temp")) {
      this.launchTemp = launchTemp;
    } else {
      this.launchTemp = "";
    }
  }

  public String getLaunchAirPressure() {
    return launchAirPressure;
  }

  /**
   * Sets launch air pressure when it is valid.
   *
   * @param launchAirPressure The value to check if it is valid.
   */
  public void setLaunchAirPressure(final String launchAirPressure) {
    if (isValidCheck(launchAirPressure, "Launch Air Pressure")) {
      this.launchAirPressure = launchAirPressure;
    } else {
      this.launchAirPressure = "";
    }
  }

  public String getLaunchRodLength() {
    return launchRodLength;
  }

  /**
   * Sets launch rod length when it is valid.
   *
   * @param launchRodLength The value to check if it is valid.
   */
  public void setLaunchRodLength(final String launchRodLength) {
    if (isValidCheck(launchRodLength, "Launch Rod Length")) {
      this.launchRodLength = launchRodLength;
    } else {
      this.launchRodLength = "";
    }
  }

  public String getLaunchRodDir() {
    return launchRodDir;
  }

  /**
   * Sets launch rod direction when it is valid.
   *
   * @param launchRodDir The value to check if it is valid.
   */
  public void setLaunchRodDir(final String launchRodDir) {
    if (isValidCheck(launchRodDir, "Launch Rod Dir")) {
      this.launchRodDir = launchRodDir;
    } else {
      this.launchRodDir = "";
    }
  }

  public String getLaunchRodAngle() {
    return launchRodAngle;
  }

  /**
   * Sets launch rod angle when it is valid.
   *
   * @param launchRodAngle The value to check if it is valid.
   */
  public void setLaunchRodAngle(final String launchRodAngle) {
    if (isValidCheck(launchRodAngle, "Launch Rod Angle")) {
      this.launchRodAngle = launchRodAngle;
    } else {
      this.launchRodAngle = "";
    }
  }

  public String getLaunchAlt() {
    return launchAlt;
  }

  /**
   * Sets launch altitude when it is valid.
   *
   * @param launchAlt The value to check if it is valid.
   */
  public void setLaunchAlt(final String launchAlt) {
    if (isValidCheck(launchAlt, "Launch Alt")) {
      this.launchAlt = launchAlt;
    } else {
      this.launchAlt = "";
    }
  }

  public String getLaunchLong() {
    return launchLong;
  }

  /**
   * Sets launch starting longitude when it is valid.
   *
   * @param launchLong The value to check if it is valid.
   */
  public void setLaunchLong(final String launchLong) {
    if (isValidCheck(launchLong, "Launch Long")) {
      this.launchLong = launchLong;
    } else {
      this.launchLong = "";
    }
  }

  public String getLaunchLat() {
    return launchLat;
  }

  /**
   * Sets launch starting latitude when it is valid.
   *
   * @param launchLat The value to check if it is valid.
   */
  public void setLaunchLat(final String launchLat) {
    if (isValidCheck(launchLat, "Launch Lat")) {
      this.launchLat = launchLat;
    } else {
      this.launchLat = "";
    }
  }

  public String getNumSimulations() {
    return numSimulations;
  }

  public int getNumSimulationsAsInteger() {
    return numSimulations.equals("") ? 0 : Integer.parseInt(numSimulations.replaceAll(",", ""));
  }

  /**
   * Sets the number of simulations and records errors to the error list.
   *
   * @param numSimulations the number of simulations
   */
  public void setNumSimulations(final String numSimulations) {
    if (!NumberUtils.isNumber(numSimulations)) {
      setErrorsFound(true);
      errorList
              .add("Invalid number of simulation value: " + numSimulations + ". Must be a number");
      this.numSimulations = "0";
      return;
    }
    this.numSimulations = numSimulations;
  }
}