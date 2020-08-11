package nz.ac.vuw.engr301.group15.gui;

import net.sf.openrocket.simulation.SimulationStatus;
import java.util.ArrayList;
import java.util.Arrays;

public class ClusterFactory {

  public static void findClusters(ArrayList<SimulationStatus> points) {
    double[][] distances = findDistances(points);

    System.out.println("distances");
    for (double[] d :
            distances) {
      System.out.println(Arrays.toString(d));
    }

    System.out.println("\nshortest distance");
    IndexedPair<SimulationStatus> shortestDistance = shortestDistance(distances, points);
    System.out.printf("Point 1: %d, Point 2: %d\n", shortestDistance.ai, shortestDistance.bi);
    System.out.printf("Distance 1: %.20f, Distance 2: %.20f\n", distances[shortestDistance.ai][shortestDistance.bi], distances[shortestDistance.ai][shortestDistance.bi]);
    System.out.printf("Lat:%.20f\nLong:%.20f\n", shortestDistance.a.getRocketWorldPosition().getLatitudeDeg(), shortestDistance.a.getRocketWorldPosition().getLongitudeDeg());
    System.out.printf("Lat:%.20f\nLong:%.20f\n", shortestDistance.b.getRocketWorldPosition().getLatitudeDeg(), shortestDistance.b.getRocketWorldPosition().getLongitudeDeg());


  }

  private static double[][] findDistances(ArrayList<SimulationStatus> points) {
    double[][] distances = new double[points.size()][points.size()];
    for (int i = 0; i < points.size(); i++) {
      SimulationStatus point = points.get(i);
      double[] d = new double[points.size()];
      for (int j = 0; j < points.size(); j++) {
        if (i == j) d[j] = Double.MAX_VALUE;
        else {
          d[j] = distance(point, points.get(j));
        }
      }
      distances[i] = d;
    }
    return distances;
  }

  private static double distance(SimulationStatus first, SimulationStatus second) {
    //https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
    int R = 6371; // Radius of the earth in km

    double lat1 = first.getRocketWorldPosition().getLatitudeRad();
    double long1 = first.getRocketWorldPosition().getLongitudeRad();
    double lat2 = second.getRocketWorldPosition().getLatitudeRad();
    double long2 = second.getRocketWorldPosition().getLongitudeRad();

    double dLat = Math.abs(lat2 - lat1);
    double dLon = Math.abs(long2 - long1);

    // Haversine formula
    double a =
            Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(lat1) * Math.cos(lat2) *
            Math.sin(dLon/2) * Math.sin(dLon/2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    return R * c;
  }

  public static void main(String[] args) {

  }

  private static IndexedPair<SimulationStatus> shortestDistance(double[][] distances, ArrayList<SimulationStatus> points) {
    IndexedPair<SimulationStatus> shortest = new IndexedPair<>(points.get(0), 0, points.get(1), 1);
    for (int i = 0; i < distances.length; i++) {
      double[] arr = distances[i];
      int minIndex = 0;
      for (int j = 0; j < arr.length; j++) {
        if (arr[j] < arr[minIndex]) {
//          System.out.printf("%f is less than %f\n", arr[j], arr[minIndex]);
          minIndex = j;
        }
      }

      if (arr[minIndex] < distances[shortest.ai][shortest.bi]) {
        shortest = new IndexedPair<>(points.get(i), i, points.get(minIndex), minIndex);
      }
    }

    return shortest;
  }

  private static class IndexedPair<T>{
    final T a;
    final int ai;
    final T b;
    final int bi;

    IndexedPair(T a, int ai, T b, int bi) {
      this.a = a;
      this.ai = ai;
      this.b = b;
      this.bi = bi;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      IndexedPair<?> indexedPair = (IndexedPair<?>) o;

      return (ai == indexedPair.ai && bi == indexedPair.bi) || (ai == indexedPair.bi && bi == indexedPair.ai);
    }

    @Override
    public int hashCode() {
      int result = a.hashCode();
      result = 31 * result + b.hashCode();
      return result;
    }
  }
}
