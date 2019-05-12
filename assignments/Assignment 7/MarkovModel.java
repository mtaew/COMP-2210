import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Tae Myles (twm0025@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2019-04-20
 **/
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   String start;
   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      int k = K;
      int a = 0;
      int b = 0;
      
      start = sourceText.substring(0 , k);
      while (a + k <= sourceText.length()) {
         String kg = sourceText.substring (a, a + k);
         String str = "";
         if (!model.containsKey(kg)) {
            int j = k;
            
            while (b + j < sourceText.length()) {
               String g = sourceText.substring (b, b + j);
               if (kg.equals(g)) {
                  str += sourceText.substring(b + j, b + j + 1);
               }
               if (b + k >= sourceText.length()) {
                  str += '\u0000';
               }
               else {
                  b++;
               }
            }
            model.put(kg, str);
         }
         b = 0;
         a++;
      }
   
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return start;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      int modelSize = model.size();
      int i = 0;
      Random rand = new Random();
      int next = rand.nextInt(modelSize);
      
      for (String j : model.keySet()) {
         if (next == i) {
            return j;
         }
         else {
            i++;
         }
      }
      return null;
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
      Random roll = new Random();
      String str = "";
      int i = 0;
      
      for(String j: model.keySet()) {
         if (j.equals(kgram)) {
            str = model.get(kgram);
            int len = str.length();
            if (len >= 1) {
               i = roll.nextInt(len) + 1;
            }
         }
      }
      
      int tot = i - 1;
      if (!str.equals("")) {
         return str.charAt(tot);
      }
      return '\u0000';
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
