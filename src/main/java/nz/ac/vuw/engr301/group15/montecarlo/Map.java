package nz.ac.vuw.engr301.group15.montecarlo;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map extends JPanel {
  static String API_Key = "KrGZcmPxmu4tNuWChwMteQNlNADrcByh";
  static int zoom = 9;
  static double cenLeft = 13.567893;
  static double cenRight = 46.112341;
  static String format = "jpg";
  static String layer = "basic";
  static String style = "main";
  static long lat = 500;
  static long longit = 250;
  static String view = "Unified";
  static String language = "en-GB";

  //Other things


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
   * @return URL
   */

  public static URL createUrl() throws MalformedURLException {
    String urlString = "http://api.tomtom.com/map/1/staticimage?"
        + "key=" + API_Key + "&"
        + "zoom=" + zoom + "&"
        + "center=" + cenLeft + "," + cenRight + "&"
        + "format=" + format + "&"
        + "layer=" + layer + "&"
        + "style=" + style + "&"
        + "width=" + lat + "&"
        + "height=" + longit + "&"
        + "view=" + view + "&"
        + "language=" + language;
    System.out.println("URL IS: " + urlString);
    return new URL(urlString);
  }

  /**
<<<<<<< HEAD
   * This rearranges the array so that the highest value and the lowest value are in index 0 and index 1.
   * This should be done twice, once for the x value, and once for the y value. 
   * @param array The array to be rearranged
   * @param n the lenght of the array?
   */
  public static void rearrange(ArrayList<Long> array, int n, boolean longitudeValue)
  {
    Long[] arr = array.toArray(new Long[0]);

    // Auxiliary array to hold modified array
    long temp[] = new long[n];

    // Indexes of smallest and largest elements
    // from remaining array.
    int small = 0, large = n - 1;

    // To indicate whether we need to copy remaining
    // largest or remaining smallest at next position
    boolean flag = true;

    // Store result in temp[]
    for (int i = 0; i < n; i++) {
      if (flag) {
        temp[i] = arr[large--];
      } else {
        temp[i] = arr[small++];
      }

      flag = !flag;
    }

    //Calculating the centre point
    long centrePoint = temp[0] - temp[1];

    //Setting the longitude or latitude point
    if (longitudeValue){
      longit = centrePoint;
    }
    else {
      lat = centrePoint;
    }

    // Copy temp[] to arr[]
//    arr = temp.clone();
    //Temp is the final array
  }



  /**
   * This method is used to find the longitude and the latitude of all the points.
   * @param arr Array of points
   * @param n length of the array?
   * @return
   */
//  private ArrayList findMinAndMaxPoints(int[] arr, int n) {
//    // Prints max at first position, min at second position
//    // second max at third position, second min at fourth
//    // position and so on.
//    ArrayList MinPoints = new ArrayList();
//
//    // Auxiliary array to hold modified array
//    int temp[] = new int[n];
//
//    // Indexes of smallest and largest elements
//    // from remaining array.
//    int small = 0, large = n-1;
//
//    // To indicate whether we need to copy remaining
//    // largest or remaining smallest at next position
//    boolean flag = true;
//
//    // Store result in temp[]
//    for (int i=0; i<n; i++)
//    {
//      if (flag)
//        temp[i] = arr[large--];
//      else
//        temp[i] = arr[small++];
//
//      flag = !flag;
//    }
//    arr = temp.clone();
//
//    //Setting the min and max points so we can get the right area on the map
//
//
//    return MinPoints;
//  }

  /**
   * KEEP THIS IN. THIS IS NOT YET IMPLEMENTED, HOWEVER IT IS VERY IMPORTANT FOR THE NEXT STAGE.
   * This method calculates the angle in which the dots should be plotted on the graph and how far
   * away they should be from the center of teh graph.
   * @param lat1 double
   * @param long1 double
   * @param lat2 double
   * @param long2 double
   * @return double
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

  /**
   * Creates a map.
   *
   * @return A buffered image of the map.
   * @throws IOException Throws
   */
  public static BufferedImage createMap() throws IOException {
    URL url = createUrl();
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
