package nz.ac.vuw.engr301.group15.montecarlo;

import eu.jacquet80.minigeo.MapWindow;
import eu.jacquet80.minigeo.POI;
import eu.jacquet80.minigeo.Point;
import eu.jacquet80.minigeo.Segment;

public class OpenStreetView{
  public static void main(String[] args){
    BufferedReader r = new BufferedReader(new FileReader("france.poly"));
    String line;
    Point cur, prec = null;
    int readCount = 0;
    int errCount = 0;
    while((line = r.readLine()) != null) {
      readCount++;
      Matcher m = POINT.matcher(line);
      if(m.matches()) {
        double lon = Double.parseDouble(m.group(1));
        double lat = Double.parseDouble(m.group(2));
        cur = new Point(lat, lon);
        if(prec != null) window.addSegment(new Segment(prec, cur, lon<0 ? Color.BLUE : Color.RED));
        prec = cur;
      } else errCount++;
    }

    window.addPOI(new POI(new Point(48.8567, 2.3508), "Paris"));

    System.out.println("Read " + readCount + " lines; ignored " + errCount);

    window.setVisible(true);
  }



}