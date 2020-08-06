package nz.ac.vuw.engr301.group15.gui;

/**
 Mission Control Settings contains the values that can be inputted into the simulation to
 change the simulation results to be more accurate to flight conditions.

 Excerpt from OpenRocket Developer's Wiki:
 	Units used in OpenRocket
 	OpenRocket always uses internally pure SI units. For example all rocket dimensions and flight distances are in
 	meters, all masses are in kilograms, density is in kg/m³, temperature is in Kelvin etc. This convention is also
 	used when storing the design in the OpenRocket format.

 	The only exception to this rule is angles:

 	Angles are represented as radians internally, but in the file format they are converted to degrees. This is to make
 	the file format more human-readable and to avoid rounding errors.
 	Latitude and longitude of the launch site are represented in degrees both internally and externally.
 	When displaying measures to the user, the values are converted into the preferred units of the user. This is
 	performed using classes in the package net.sf.openrocket.unit. The Unit class represents a single unit and it
 	includes methods for converting between that unit and SI units in addition to creating a string representation with
 	a suitable amount of decimals. A UnitGroup describes a measurable quantity such as temperature and contains the units
 	available for that quantity, such as Celcius, Fahrenheit and Kelvin.

 Max Angle: maximum step angle of the rocket. Should be converted to radians before handing to OpenRocket - should be
  between (0.017453292519943295, 0.3490658503988659), or between (1, 20) degrees otherwise OpenRocket will clamp to
  these values.

 Wind Direction: direction of the wind at launch site, measured in degrees. While OpenRocket supports this, the SimulationOptions class
  does not - this is odd, and may need to be added in. Currently not sure which direction 0 degrees is - assumption is
  North-facing.

 Wind Turbulence: wind turbulence intensity of launch site, used to calculate wind speed deviation (average * turbulence). Percentage
  value, 100% = 2.0 m/s standard deviation, 10% = 0.2 m/s standard deviation.

 Wind Speed: average wind speed of launch site, measured in m/s.

 Launch Temperature: temperature of the launch site. Read in as a celcius value, should be converted to kelvin
  before handing to OpenRocket.

 Launch Air Pressure:

 Launch Rod Length: this is probably measured in metres, must be between (0.0, 1.0471975511965976)
  otherwise OpenRocket will clamp to these values.

 Launch Rod Direction:

 Launch Rod Angle: max 1.0471975511965976

 Launch Altitude:

 Launch Latitude: standard coordinate measurement, South values should be negative and North values positive.

 Launch Longitude: standard coordinate measurement, West values should be negative and East values positive.

 Number of Simulations: number of Monte Carlo simulations to be run
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