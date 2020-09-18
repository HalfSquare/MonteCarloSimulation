package nz.ac.vuw.engr301.group15.montecarlo;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Map extends JPanel {
  static String API_Key = "KrGZcmPxmu4tNuWChwMteQNlNADrcByh";
  static int zoom = 9;
  static double cenLeft = 13.567893;
  static double cenRight = 46.112341;
  static String format = "jpg";
  static String layer = "basic";
  static String style = "main";
  static long width = 500;
  static long height = 250;
  static String view = "Unified";
  static String language = "en-GB";

  /**
   * Method which formulates the URL to be sent to the REST API
   * http://api.tomtom.com/map/1/staticimage?
   * key=Your_API_Key&
   * zoom=9&
   * center=13.567893,46.112341&
   * format=jpg&
   * layer=basic&
   * style=main&
   * width=1305&
   * height=748&
   * view=Unified&
   * language=en-GB
   *
   * @return
   */
  public static URL createURL() throws MalformedURLException {
    String urlString = "http://api.tomtom.com/map/1/staticimage?" +
            "key=" + API_Key + "&" +
            "zoom=" + zoom + "&" +
            "center=" + cenLeft + "," + cenRight + "&" +
            "format=" + format + "&" +
            "layer=" + layer + "&" +
            "style=" + style + "&" +
            "width=" + width + "&" +
            "height=" + height + "&" +
            "view=" + view + "&" +
            "language=" + language;

    System.out.println("URL IS: " + urlString);
    return new URL(urlString);
  }

  /**
   * KEEP THIS IN. THIS IS NOT YET IMPLEMENTED, HOWEVER IT IS VERY IMPORTANT FOR THE NEXT STAGE.
   * This method calculates the angle in which the dots should be plotted on the graph and how far
   * away they should be from the center of teh graph. 
   * @param lat1
   * @param long1
   * @param lat2
   * @param long2
   * @return
   */
  private double angleFromCoordinate(double lat1, double long1, double lat2, double long2) {

    double dLon = (long2 - long1);

    double y = Math.sin(dLon) * Math.cos(lat2);
    double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
            * Math.cos(lat2) * Math.cos(dLon);

    double brng = Math.atan2(y, x);

    brng = Math.toDegrees(brng);
    brng = (brng + 360) % 360;
    brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise

    return brng;
  }

  public static BufferedImage createMap() throws IOException {
    URL url = createURL();
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setDoOutput(true);

    System.out.println("status " + con.getResponseCode());

    BufferedImage image = ImageIO.read(con.getInputStream());
    con.disconnect();
    if (image != null) {
      System.out.println(image.toString());
      return image;
    } else {
      throw new RuntimeException("Image is null");
    }
  }

  public static void main(String[] args) {
    try {
      createMap();
    } catch (IOException e) {
      System.out.println("Didn't work");
      e.printStackTrace();
    }
  }
}
