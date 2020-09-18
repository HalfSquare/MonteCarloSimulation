package nz.ac.vuw.engr301.group15.montecarlo;

import java.net.MalformedURLException;
import java.net.URL;

public class Map{
  String API_Key = "KrGZcmPxmu4tNuWChwMteQNlNADrcByh";
  int zoom = 9;
  double cenLeft = 13.567893;
  double cenRight = 46.112341;
  String format = "jpg";
  String layer = "basic";
  String style = "main";
  long width = 1305;
  long height = 748;
  String view = "Unified";
  String language = "en-GB";

  /**
   * Method which formulates the URL to be sent to the REST API
   * http://api.tomtom.com/map/1/staticimage?
   * key=Your_API_Key&
   * zoom=17&
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
  public URL createURL() throws MalformedURLException {
    String urlString = "http://api.tomtom.com/map/1/staticimage?" +
            "key=" + API_Key + "&" +
            "zoom=" + zoom + "&" +
            "center=" + cenLeft + "," + cenRight +
            "format=" + format + "&" +
            "layer=" + layer + "&" +
            "style=" + style + "&" +
            "width=" + width + "&" +
            "height=" + height + "&" +
            "view=" + view + "&" +
            "language=" + language;

    return new URL(urlString);
  }

  public void createMap(){

  }
}

