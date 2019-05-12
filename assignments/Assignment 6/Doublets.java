import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Tae Myles (twm0025@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2019-03-29
 */
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   /////////////////////////////////////////////////////////////////////////////
   // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
   // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
   // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
   // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
   // table with chaining).
   /////////////////////////////////////////////////////////////////////////////
   TreeSet<String> lexicon;
   List<String> EMPTY_LADDER = new ArrayList<>();
   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
         //////////////////////////////////////
         // INSTANTIATE lexicon OBJECT HERE  //
         //////////////////////////////////////
         lexicon = new TreeSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            lexicon.add(str.toLowerCase());
            /////////////////////////////////////////////////////////////
            // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
            /////////////////////////////////////////////////////////////
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////

   /**
    * Returns the Hamming distance between two strings, str1 and str2. The
    * Hamming distance between two strings of equal length is defined as the
    * number of positions at which the corresponding symbols are different. The
    * Hamming distance is undefined if the strings have different length, and
    * this method returns -1 in that case. See the following link for
    * reference: https://en.wikipedia.org/wiki/Hamming_distance
    *
    * @param  str1 the first string
    * @param  str2 the second string
    * @return      the Hamming distance between str1 and str2 if they are the
    *                  same length, -1 otherwise
    */
   public int getHammingDistance(String str1, String str2) {
      int counter = 0;
   
      if (str1.length() != str2.length()) {
         return -1; 
      }
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            counter++;
         }
      }
      return counter;
   }
   
  /**
   * Returns a minimum-length word ladder from start to end. If multiple
   * minimum-length word ladders exist, no guarantee is made regarding which
   * one is returned. If no word ladder exists, this method returns an empty
   * list.
   *
   * Breadth-first search must be used in all implementing classes.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a minimum length word ladder from start to end
   */
   public List<String> getMinLadder(String start, String end) {
      List<String> ladder = new ArrayList<String>();
      
      if (start.equals(end)) {
         ladder.add(start);
         return ladder;
      }
      else if (start.length() != end.length()) {
         return ladder;
      }
      else if (!isWord(start) || !isWord(end)) {
         return ladder;
      }
      TreeSet<String> found = new TreeSet<String>();
      Deque<Node> q = new ArrayDeque<Node>();
      
      q.addLast(new Node (start, null));
      found.add(start);
      
      while (!q.isEmpty()) {
         Node a = q.removeFirst();
         
         for (String firstNeighbor : getNeighbors(a.w)) {
            if (!found.contains(firstNeighbor)) {
               found.add(firstNeighbor);
               q.addLast(new Node(firstNeighbor, a));
            }
            if (firstNeighbor.equals(end)) {
               Node h = q.removeLast();
               ladder.add(h);
               
               while (h.parent != null) {
                  ladder.add(0, h.w);
                  h = a.parent;
               }
            }
         }
      }
      
   }
   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   
   public List<String> getNeighbors(String word) {
      List<String> arr = new ArrayList<String>();
      TreeSet<String> set = new TreeSet<String>();
       
      if (word == null)
         return EMPTY_LADDER;
      
      for (String fill : lexicon) {
         if (getHammingDistance(word, fill) == 1)
            arr.add(fill);
      }
      
      return arr;
   }
   
   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   public int getWordCount() {
      int counter = lexicon.size();
      return counter;
   }
   
   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   public boolean isWord(String str) {
      if (lexicon.contains(str)) {
         return true;
      }
   
      return false;
   }
   
   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   public boolean isWordLadder(List<String> sequence) { 
      for (int i = 0; i < sequence.size() - 1; i++) {
         if (getHammingDistance(sequence.get(i), sequence.get(i + 1)) != 1) {
            return false;
         }
      }
      for (int i = 0; i < sequence.size(); i++) {
         if (!lexicon.contains(sequence.get(i))) {
            return false;
         }
      }
      if (sequence.isEmpty()) {
         return false;
      } 
     
      return true;  
   }
   
   private class Node {
      public String w;
      public Node parent;
   
      public Node(String s, Node n){
         w = s;
         parent = n;
      }
      @Override
      public String toString() {
         return w;
      }
      
   }

}