package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.simulation.SimulationStatus;
import net.sf.openrocket.util.WorldCoordinate;

public class LatLongBean {
  private double latitude;
  private double longitude;
  private double[] value;

  /**
   * A bean for latitude and longitude.
   *
   * @param latitude lat
   * @param longitude long
   */
  public LatLongBean(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.value = new double[]{ latitude, longitude };
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double[] getValue() {
    return value.clone();
  }

  public void setValue(double[] value) {
    this.value = value.clone();
  }

  public static LatLongBean fromSimulationStatus(SimulationStatus status) {
    WorldCoordinate coord = status.getRocketWorldPosition();
    return new LatLongBean(coord.getLatitudeDeg(), coord.getLongitudeDeg());
  }
}
