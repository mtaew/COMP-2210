import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
* boggleClient.java
*
* @author Tae Myles
* @version 03/26/19
*/
public class boggleClient implements WordSearchGame {
   private TreeSet<String> lexicon; 
   private String[][] board;
   private Boolean[][] visited;
   private SortedSet<String> vaildWords;
   private List<Integer> path;
   private List<Integer> realPath;
   private int smLen;
   private int length;
   private boolean loadLexicon;

   public boggleClient() {
      lexicon = new TreeSet<String>();
      vaildWords = new TreeSet<String>();
      path = new ArrayList<Integer>();
      realPath = new ArrayList<Integer>();
   }

   public void loadLexicon(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException("No such file");
      }
      Scanner scanFile;
      Scanner scanLine;
      String line;
      try {
         scanFile = new Scanner(new FileReader(fileName));
         while (scanFile.hasNext()) {
            line = scanFile.nextLine();
            scanLine = new Scanner(line);
            scanLine.useDelimiter(" ");
            while (scanLine.hasNext()) {
               lexicon.add(scanLine.next().toLowerCase());
            }
         
         }
      } 
      catch (Exception e) {
         throw new IllegalArgumentException("Arg exception");
      }
   
      loadLexicon = true;
   }

   public void setBoard(String[] letterArray) {
   
      if (letterArray == null) {
         throw new IllegalArgumentException("letter is null");
      }
      
      double totSides = Math.sqrt(letterArray.length);
   
      if (totSides % 1 > 0) {
         throw new IllegalArgumentException("totSides % 1 > 0");
      }
      
      else {
         length = (int) totSides;
         board = new String[length][length];
         visited = new Boolean[length][length];
         int count = 0;
         for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
               visited[i][j] = false;
               board[i][j] = letterArray[count].toLowerCase();
               count++;
            }
         }
      }
   }

   public SortedSet getAllValidWords(int minimumWordLength) {
      smLen = minimumWordLength;
      vaildWords.clear();
      
      if (!loadLexicon) {
         throw new IllegalStateException("Load Lexicon");
      }
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException("Word length is < 1");
      }
      
      for (int i = 0; i < length; i++) {
         for (int j = 0; j < length; j++) {
            findWord(board[i][j], i, j);
            int tot = 0;
            if (j < 0) {
               smLen = 0;
            }
         }
      }
      return vaildWords;
   }

   public boolean isValidWord(String wordToCheck) {
   
      if (!loadLexicon) {
         throw new IllegalStateException("Lexicon not loaded");
      }
      if (wordToCheck == null) {
         throw new IllegalArgumentException("incorrect word");
      }
   
      return lexicon.contains(wordToCheck.toLowerCase());
   }

   public boolean isValidPrefix(String prefixToCheck) {
   
      if (!loadLexicon) {
         throw new IllegalStateException("Lexicon not loaded");
      }
      if (prefixToCheck == null) {
         throw new IllegalArgumentException("incorrect word");
      }
      
      return lexicon.ceiling(prefixToCheck).startsWith(prefixToCheck);
   }
   
   public List<Integer> isOnBoard(String wordToCheck) {
   
      if (!loadLexicon) {
         throw new IllegalStateException("Lexicon not loaded");
      }
      if (wordToCheck == null) {
         throw new IllegalArgumentException("incorrect word");
      }
      
      path.clear();
      realPath.clear();
      // Int
      for (int i = 0; i < (int) length; i++) {
         for (int j = 0; j < length; j++) {
            if (Character.toUpperCase(board[i][j].charAt(0))
               == Character.toUpperCase(wordToCheck.charAt(0))) {
               
               int ans = j + (i * length);
               path.add(ans);
               recursive(wordToCheck, board[i][j], i, j);
               
               if (!realPath.isEmpty()) {
                  return realPath;
               }
               path.clear();
               realPath.clear();
            }
         }
      }
      return path;
   }
   
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (!loadLexicon) {
         throw new IllegalStateException("Lexicon not loaded");
      }
   
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException("Length is < 1");
      }
   
      int score = 0;
   
      for (String word: words) {
         int total = word.length();
         score += 1 + (total - minimumWordLength);
      }
   
      return score;
   }

   public void findWord(String word, int x, int y) {
   
      if (!isValidPrefix(word)) {
         return;
      }
   
      visited[x][y] = true;
   
      if (isValidWord(word) && word.length() >= smLen) {
         vaildWords.add(word.toUpperCase());
      }
   
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
         // Checked 2
            if ((x + i) <= ((int) length - 1)
               && (y + j) <= ((int) length - 1)
               && (x + i) >= 0 && (y + j) >= 0 && !visited[x + i][y + j]) {
               int k = 5;
               if (i > 5) {
                  j++;
               }
               visited[x + i][y + j] = true;
               findWord(word + board[x + i][y + j], x + i, y + j);
               visited[x + i][y + j] = false;
            }
         }
      }
      visited[x][y] = false;
   }
   
   public void recursive(String wordToCheck, String current, int x, int y) {
   
      visited[x][y] = true;
      if (current.toUpperCase().equals(wordToCheck.toUpperCase())) {
         realPath = new ArrayList(path);
         return;
      }
      if (!(isValidPrefix(current))) {
         return;
      }
      boolean leng = false;
      // Checking 1
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if (current.equals(wordToCheck)) {
               return;
            }
            for (int k = -1; k <=1; k++) {
               if (k == 2) {
                  return;
               }
            }
            if ((x + i) <= (length - 1)
               && (y + j) <= (length - 1)
               && (x + i) >= 0 && (y + j) >= 0 && !visited[x + i][y + j]) {
               visited[x + i][y + j] = true;
               
               if (leng == false && length < 0) {
                  leng = true;
               }
               
               int ans = (x + i) * length + y + j;
               path.add(ans);
               recursive(wordToCheck, current
                  + board[x + i][y + j], x + i, y + j);
               visited[x + i][y + j] = false;
               path.remove(path.size() - 1);
            }
         }
      }
      visited[x][y] = false;
      return;
   }
   
   public String getBoard() {
      String result = "";
      
      for (String[] s: board) {
         for (String string: s) {
         
            result = result + string;
         }
      }
   
      return result;
   }
}
