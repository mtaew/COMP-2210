import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Tae Myles (TWM0025@tigermail.auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  1/18/19
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) {
   
      //Checks if a is null or empty.
      if (a == null) { 
         throw new IllegalArgumentException("Array is null");
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException("Array is empty");
      }
      
      //Iterates the array and checks for the lowest value.
      int min = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] < min) {
            min = a[i];
         }
      }
      return min;
   }

   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) {
   
      //Checks if a is null or empty.
      if (a == null) { 
         throw new IllegalArgumentException("Array is null");
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException("Array is empty");
      }
      
      //Iterates the array and checks for the highest value.
      int max = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] > max) {
            max = a[i];
         }
      }
      return max;
   }
   
   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) {
   
      //Checks if a is null or empty.
      if (a == null) { 
         throw new IllegalArgumentException("Array is null");
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException("Array is empty");
      }
      
      int dups = 0;
      int[] sorted = Arrays.copyOf(a, a.length);
      Arrays.sort(sorted);
      
      for (int i = 0; i < a.length - 1; i++) {
         if (a[i] == a[i + 1]) {
            dups++;
         }
      }
      //Setting bounds
      int newNum = a.length - dups;
      int[] newSorted = new int[newNum];
      int newBound = newSorted.length - 1;
      int sortBound = sorted.length - 1;
      newSorted[newBound] = sorted[sortBound];
   
      int j = 0;
      for (int i = 0; i < a.length - 1; i++) {
         if (sorted[i] != sorted[i + 1]) {
            newSorted[j] = sorted[i];
            j++;
         }
      }
      
      newSorted[j] = sorted[a.length - 1];
      j++;
      
      if (k < 1) {
         throw new IllegalArgumentException("k cant be less than 1");
      }
      if (k > j) {
         throw new IllegalArgumentException("distinct value cannot be bigger than k");
      }
      if (k > newSorted.length) {
         throw new IllegalArgumentException("K cant be bigger");
      }
      
      int value = newSorted[k - 1];
      return value;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) {
   
      //Checks if a is null or empty.
      if (a == null) { 
         throw new IllegalArgumentException("Array is null");
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException("Array is empty");
      }
      
      int dups = 0;
      int[] sorted = Arrays.copyOf(a, a.length);
      Arrays.sort(sorted);
      
      int[] newSorted = new int[a.length];
   
      int j = 0;
      for (int i = 0; i < a.length - 1; i++) {
         if (sorted[i] != sorted[i + 1]) {
            newSorted[j] = sorted[i];
            j++;
         }
      }
      
      newSorted[j] = sorted[a.length - 1];
      j++;
      
      
      if (k < 1) {
         throw new IllegalArgumentException("k cant be less than 1");
      }
      if (k > j) {
         throw new IllegalArgumentException("distinct value cannot be bigger than k");
      }
      if (k > newSorted.length) {
         throw new IllegalArgumentException("K cant be bigger");
      }
      
      int[] b = Arrays.copyOf(newSorted, j);
      
      
      int value = newSorted[b.length - k];
      return value;
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) {
   
      //Checks if a is null or empty.
      if (a == null) { 
         throw new IllegalArgumentException("Array is null");
      }
      if (a.length == 0) {
         throw new IllegalArgumentException("Array is empty");
      }
      int in = 0;
      int rIn = 0;
      
      for (int i = 0; i < a.length; i++) {
         if ((a[i] >= low) && (a[i] <= high)) {
            rIn++;
         }
      }
      int[] totR = new int[rIn];
      if (rIn == 0) {
         int[] dump = {};
         return dump;
      }
      else {
         for (int i = 0; i < a.length; i++) {
            if ((a[i] >= low) && (a[i] <= high)) {
               totR[in] = a[i];
               in++;
            }
         }
      }
      return totR;
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) {
   
      //Checks if a is null or empty.
      if (a == null) { 
         throw new IllegalArgumentException("Array is null");
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException("Array is empty");
      }
      for (int i = 0; i < a.length; i++) {
         if (key <= a[i]) {
            break;
         }
         if (i == a.length - 1) {
            throw new IllegalArgumentException("Key is larger than any values in the array.");
         }
      } 
      
      int num = 0;
      
      for (int i = 0; i < a.length; i++) {
         num += a[i];
      }
      
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key && a[i] <= num) {
            num = a[i];
         }
      }
      return num;
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
   
      //Checks if a is null or empty.
      if (a == null) { 
         throw new IllegalArgumentException("Array is null");
      }
      
      if (a.length == 0) {
         throw new IllegalArgumentException("Array is empty");
      }
      for (int i = 0; i < a.length; i++) {
         if (key >= a[i]) {
            break;
         }
         if (i == a.length - 1) {
            throw new IllegalArgumentException("Key is larger than any values in the array.");
         }
      } 
      int num = 0;
      
      for (int i = 0; i < a.length; i++) {
         num -= a[i];
      }
      
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key && a[i] >= num) {
            num = a[i];
         }
      }
      return num;
   }

}
