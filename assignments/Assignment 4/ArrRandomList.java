import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;   
/**
 * ArrayRandomizedList.java.
 * 
 * @author   Tae Myles (twm0025@auburn.edu)
 * @version  2019-03-01
 */
public class ArrRandomList<T> implements RandomizedList<T> {
   private T[] elements;
   private int size;
   private static final int DEFAULT_CAPACITY = 10;
   
   @SuppressWarnings("unchecked")
   public ArrRandomList() {
      elements = (T[]) new Object[DEFAULT_CAPACITY];
      size = 0;
   }
   
   public Iterator<T> iterator() {
      return new itr(elements, size);
   }
   public boolean isFull() {
      return size == elements.length;
   }
   
   public boolean isEmpty() {
      return size == 0;
   }
   
   public int size() {
      return size;
   }
   
   /**
    * Adds the specified element to this list. If the element is null, this
    * method throws an IllegalArgumentException.
    */
   public void add(T element) {
      if (element == null) {
         throw new IllegalArgumentException("Element is null.");
      }
      
      if (isFull()) {
         resize(elements.length * 2);
      }
      elements[size] = element;
      size++;
   }
   /**
    * Selects and removes an element selected uniformly at random from the
    * elements currently in the list. If the list is empty this method returns
    * null.
    */
   public T remove() {
      if (this.isEmpty()) {
         return null;
      }
      Random roll = new Random();
      int rolledNum = roll.nextInt(size);
      T remove = elements[rolledNum];
      elements[rolledNum] = elements[size() - 1];
      elements[size() - 1] = null;
      size--;
         
      if (size > 0 && size < elements.length / 4) {
         resize(elements.length / 2);
      }
      return remove;
   }
   
   /**
    * Selects but does not remove an element selected uniformly at random from
    * the elements currently in the list. If the list is empty this method
    * return null.
    */
   public T sample() {
      if (this.isEmpty()) {
         return null;
      }
      
      Random roll = new Random();
      int rolledNum = roll.nextInt(size);
      return elements[rolledNum];
   }
   
   public void resize(int length) {
      T[] obj = (T[]) new Object[DEFAULT_CAPACITY];
      for (int i = 0; i < size(); i++) {
         obj[i] = elements[i];
      }
   }
   
    // Iterator class
   private class itr implements Iterator<T> {
      private T[] arr;
      private int val;
      private int pos;
      
      public itr(T[] arrIn, int valIn) {
         arr = arrIn;
         val = valIn;
         pos = valIn - 1;
      }
   
      public boolean hasNext() {
         return (pos >= 0);
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
   
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException("No such element.");
         }
         
         Random roll = new Random();
         int rolledNum = roll.nextInt(pos + 1);
         T next = arr[rolledNum];
         arr[rolledNum] = arr[pos];
         arr[pos] = next;
         pos--;
         
         
         
         return next;
      }
   }
}