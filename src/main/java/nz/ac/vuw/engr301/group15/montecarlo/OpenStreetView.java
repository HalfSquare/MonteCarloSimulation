package nz.ac.vuw.engr301.group15.montecarlo;

import eu.jacquet80.minigeo.MapWindow;
import eu.jacquet80.minigeo.POI;
import eu.jacquet80.minigeo.Point;
import eu.jacquet80.minigeo.Segment;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenStreetView{
  private static Pattern POINT = Pattern.compile("^.*?(-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)$");

  public static void main(String[] args) throws IOException {
    MapWindow window = new MapWindow();

    BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\Justina\\Desktop\\2020\\Semester 1\\ENGR301\\group-15\\src\\main\\java\\nz\\ac\\vuw\\engr301\\group15\\montecarlo\\france.txt"));
    String line;
    Point cur, prec = null;
    int readCount = 0;
    int errCount = 0;
    double latitide = 67.52388;
    double longitude = 21.87598;

//    window.addSegment();
//    while((line = r.readLine()) != null) {
//      readCount++;
//      Matcher m = POINT.matcher(line);
//      if(m.matches()) {
//        double lon = Double.parseDouble(m.group(1));
//        double lat = Double.parseDouble(m.group(2));
//        cur = new Point(lat, lon);
//        if(prec != null) window.addSegment(new Segment(prec, cur, lon<0 ? Color.BLUE : Color.RED));
//        prec = cur;
//      } else errCount++;
//    }

    window.addPOI(new POI(new Point(latitide, longitude), "Yo?"));

    System.out.println("Read " + readCount + " lines; ignored " + errCount);

    window.setVisible(true);
  }



}