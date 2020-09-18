package nz.ac.vuw.engr301.group15.montecarlo;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class Map extends JPanel{
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

    System.out.println("URL IS: " + urlString );
    return new URL(urlString);
  }

  public static BufferedImage createMap() throws IOException {
    URL url = createURL();
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    BufferedImage image = ImageIO.read(url);
    if(image != null) {
      return image;
    } else {
      throw new RuntimeException("Image is null");
    }
  }


//  public Map(){
//    try {
//      image = (BufferedImage) createMap();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  public static void main(String[] args){
    try {
      createMap();
//      Map m = new Map();
//      JFrame f = new JFrame();
////      f.add(m);
//      f.setSize(400,400);
//      f.setVisible(true);
    } catch (IOException e) {
      System.out.println("Didn't work");
      e.printStackTrace();
    }
  }
}
