package nz.ac.vuw.engr301.group15.gui;

public class LatLongBean {
  private double latitude;
  private double longitude;
  private double[] value;

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
}
