package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.simulation.SimulationStatus;

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
    return value;
  }

  public void setValue(double[] value) {
    this.value = value;
  }

  /**
   * Finds the distance between two simulation statuses.
   *
   * @param first the first point
   * @param second the second point
   * @return the distance between the points
   */
  public static double distance(SimulationStatus first, SimulationStatus second) {
    //https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
    int r = 6371; // Radius of the earth in km

    double lat1 = first.getRocketWorldPosition().getLatitudeRad();
    double long1 = first.getRocketWorldPosition().getLongitudeRad();
    double lat2 = second.getRocketWorldPosition().getLatitudeRad();
    double long2 = second.getRocketWorldPosition().getLongitudeRad();

    double diffLat = Math.abs(lat2 - lat1);
    double diffLon = Math.abs(long2 - long1);

    // Haversine formula
    double a =
            Math.sin(diffLat / 2) * Math.sin(diffLat / 2)
                    + Math.cos(lat1) * Math.cos(lat2)
                    * Math.sin(diffLon / 2) * Math.sin(diffLon / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return r * c;
  }
}
