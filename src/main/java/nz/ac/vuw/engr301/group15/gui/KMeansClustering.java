package nz.ac.vuw.engr301.group15.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.evaluation.ClusteringEvaluator;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

public class KMeansClustering {
  //  /**
  //   * An example of using the kmeans.
  //   *
  //   * @param args args
  //   */
  //  public static void main(String[] args) {
  //    KMeansClustering km = new KMeansClustering();
  //    String exampleData = "C:\\Users\\Max\\Documents\\Vic\\ENGR301\\group-15\\points.csv";
  //
  //    km.calculateClusters(exampleData, 2);
  //  }

  /**
   * Calculate the clusters.
   *
   * @param path csv file
   * @return a set of the centers of the clusters
   */
  public static Set<LatLongBean> calculateClusters(String path, int clusters) {
    // Start spark session
    SparkSession spark = SparkSession
            .builder()
            .appName("Java Spark SQL Example")
            .config("spark.master", "local")
            .getOrCreate();

    // Create the CSV schema
    StructType cvsSchema = new StructType()
            .add("Latitude", "double")
            .add("Longitude", "double");

    // Load data
    Dataset<Row> loadedCsv = spark
            .read()
            .option("mode", "DROPMALFORMED")
            .schema(cvsSchema)
            .csv(path);

    // Convert into k-means readable points
    List<LatLongBean> data2 = new ArrayList<>();
    for (Iterator<Row> it = loadedCsv.toLocalIterator(); it.hasNext(); ) {
      Row row = it.next();
      LatLongBean bean = new LatLongBean(
              Double.parseDouble(row.get(0).toString()),
              Double.parseDouble(row.get(1).toString())
      );
      data2.add(bean);
    }
    Dataset<LatLongBean> dataset = spark.createDataset(data2, Encoders.bean(LatLongBean.class));



    // Train a k-means model
    KMeans kmeans = new KMeans()
            .setK(clusters)
            .setSeed(1L)
            .setFeaturesCol("value");
    KMeansModel model = kmeans.fit(dataset);


    // Make predictions
    Dataset<Row> predictions = model.transform(dataset);


    // Evaluate clustering by computing Silhouette score
    ClusteringEvaluator evaluator = new ClusteringEvaluator();


    double silhouette = evaluator
            .setFeaturesCol("value")
            .evaluate(predictions);
    System.out.println("Silhouette with squared euclidean distance = " + silhouette);


    // Shows the result.
    Vector[] centers = model.clusterCenters();
    System.out.println(model.summary().predictions().toString());
    System.out.println("Cluster Centers: ");

    Set<LatLongBean> centersSet = new HashSet<>();

    for (Vector center: centers) {
      centersSet.add(new LatLongBean(center.toArray()[1], center.toArray()[0]));
      System.out.printf("Lat: %.20f, Long: %.20f\n", center.toArray()[0], center.toArray()[1]);
    }

    return centersSet;
  }
}
