import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Tae Myles (you@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 2/21/19
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename. 
    */
   public Extractor(String filename) {
      try {
         Scanner scan = new Scanner(new File(filename));
         int length = scan.nextInt();
         points = new Point[length];
         
         for (int i = 0; i < length; i++) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            points[i] = new Point(x, y);
         }
      } 
      catch (Exception e) {
         System.err.println("No files found");
      }
   }
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesBrute() {
      lines = new TreeSet<Line>();
      for (int i = 0; i < points.length; i++) {
         for (int j = i + 1; j < points.length; j++) {
            for (int k = j + 1; k < points.length; k++) {
               for (int l = k + 1; l < points.length; l++) {
                  double slope = points[i].slopeTo(points[j]);
                  if (slope == points[i].slopeTo(points[k]) 
                        && slope == points[i].slopeTo(points[l])) {
                     TreeSet<Point> temp = new TreeSet<Point>();
                     temp.add(points[i]);
                     temp.add(points[j]);
                     temp.add(points[k]);
                     temp.add(points[l]);
                        
                     if (temp.size() > 3) {
                        lines.add(new Line(temp));
                     }
                    
                  }
               }
            }
         }       
      }
      return lines;
   }

  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();
      Point[] arrSort = Arrays.copyOf(points, points.length);
      //Arrays.sort(points);
      Line g = new Line();
      boolean addedPoint = true;
      
      for (int i = 0; i < points.length; i++) { 
         Arrays.sort(arrSort, points[i].slopeOrder);
         int counter = 0;
         
         for (int j = 1; j < points.length; j++) {
            g.add(arrSort[0]);
            addedPoint = g.add(arrSort[j]);
         
         
            if (!addedPoint) {
               if (g.length() >= 4) {
               
                  lines.add(g);
               }
               g = new Line();
               g.add(arrSort[j]);
            }
              
            
         }
        
      }
      return lines;
   }
}
