import java.util.Iterator;
import java.util.NoSuchElementException; 
/**
 * DubEndedList.java. 
 *
 * @author   Tae Myles (twm0025@auburn.edu)
 * @version  2019-03-01
 */
public class DubEndedList<T> implements DoubleEndedList<T> {
   private int size;
   private Node front;
   private Node end;
   
   public DubEndedList() {
      front = null;
      size = 0;
   }
   
   public int size() {
      return size;
   }
   
   public boolean isEmpty() {
      return size == 0;
   }
   
   private class Node {
      private T element;
      private Node next;
   
      public Node(T ele) {
         element = ele;
      }
   
      public Node(T ele, Node nd) {
         next = nd;
         element = ele;
      }
   
   }
   /**
    * Adds element to the front of the list. If element is null,
    * this method throws an IllegalArgumentException.
    */
   public void addFirst(T element) {
      if (element == null) {
         throw new IllegalArgumentException("element is null.");
      }
      
      Node nd = new Node(element);
      
      if (front == null) {
      
         front = nd;
         end = nd;
      }
      else {
         nd.next = front;
         front = nd;
      }
      size++;
   }
   
   /**
    * Adds element to the end of the list. If element is null,
    * this method throws an IllegalArgumentException.
    */
   public void addLast(T element) {
      if (element == null) {
         throw new IllegalArgumentException("element is null");
      }
   
      Node nd = new Node(element);
      nd.element = element;
      
      if (front == null) {
         front = nd;
         end = nd;
      }
      else {
         end.next = nd;
         end = nd;
      }
      size++;
   }
  
      
   /**
    * Delete and return the element at the front of the list.
    * If the list is empty, this method returns null.
    */
   public T removeFirst() {
      if (isEmpty()) {
         return null;
      }
      T remove = front.element;
      front = front.next;
      size--;
      return remove;
   }
   
   /**
    * Delete and return the element at the end of the list.
    * If the list is empty, this method returns null.
    */
   public T removeLast() {
      if (isEmpty()) {
         return null;
      }
      else if (size == 1) {
         T remove = front.element;
         front = null;
         end = null;
         size--;
         return remove;
      }
      else {
         Node nd = front;
      
         while (nd.next.next != null) {
            nd = nd.next;
         }
         T remove = nd.next.element;
         nd.next = null;
         end = nd;
         size--;
         return remove;
      }
      
   }
   public Iterator<T> iterator() {
      return new itr();
   }
   
   private class itr implements Iterator<T> {
   
      private Node pos = front;
      public boolean hasNext() {
         return pos != null;
      }
   
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         
         T result = pos.element;
         pos = pos.next;
         return result;
      }
   
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}