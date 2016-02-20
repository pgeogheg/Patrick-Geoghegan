
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 *  This class contains the methods of Doubly Linked List.
 *
 *  @author  
 *  @version 12/10/14 11:16:54
 */


/**
 * Class DoublyLinkedList: implements a *generic* Doubly Linked List.
 * @param <T> This is a type parameter. T is used as a class name in the
 * definition of this class.
 *
 * When creating a new DoublyLinkedList, T should be instantiated with an
 * actual class name that extends the class Comparable.
 * Such classes include String and Integer.
 *
 * For example to create a new DoublyLinkedList class containing String data: 
 *    DoublyLinkedList<String> myStringList = new DoublyLinkedList<String>();
 *
 * The class implements the Iterable<T> interface, allowing to use iterators
 * over the data stored in the doubly-linked list. This is already implemented.
 *
 * The class offers a toString() method which returns a comma-separated sting of
 * all elements in the data structure.
 * 
 * This is a bare minimum class you would need to completely implement.
 * You can add additional methods to support your code. Each method will need
 * to be tested by your jUnit tests -- for simplicity in jUnit testing
 * introduce only public methods.
 */
class DoublyLinkedList<T extends Comparable<T>> implements Iterable<T>
{
	int nodeCount=0;
    /**
     * private class DLLNode: implements a *generic* Doubly Linked List node.
     */
    private class DLLNode
    {
        public T data;
        public DLLNode next;
        public DLLNode prev;
    
        /**
         * Constructor
         * @param theData : data of type T, to be stored in the node
         * @param prevNode : the previous Node in the Doubly Linked List
         * @param nextNode : the next Node in the Doubly Linked List
         * @return DLLNode
         */
        public DLLNode( T theData, DLLNode prevNode, DLLNode nextNode) 
        {
          data = theData;
          prev = prevNode;
          next = nextNode;

        }
    }

    // the DLLNode class is defined at the end of this file.
    // Fields head and tail point to the first and last nodes of the list.
    //
    private DLLNode head, tail;

    /**
     * Constructor
     * @return DoublyLinkedList
     */
    public DoublyLinkedList() 
    {
      head = null; tail = null;
    }

    /**
     * Tests if the doubly linked list is empty
     * @return true if list is empty, and false otherwise
     *
     * Worst-case asymptotic runtime cost: Theta(1)
     *
     * Justification:
     *  If isEmpty, it will compare head and null then return true. These operations are constant so the asymptotic runtime is 1.
     */
    public boolean isEmpty()
    {
      if(head==null&&tail==null){
    	  return true;
      }
      return false;
    }

    /**
     * Inserts an element at the beginning of the doubly linked list
     * @param data : The new data of class T that needs to be added to the list
     * @return none
     *
     * Worst-case asymptotic runtime cost: Theta(1)
     *
     * Justification:
     *  In all cases the operations are constant so the asymptotic runtime is 1
     */
    public void insertFirst( T data ) 
    {
    	 DLLNode newNode = new DLLNode(data,null,null);
    	 if(isEmpty()){
    		 head=newNode;
    		 tail=newNode;
    	 }
    	 else {
    		 head.prev= newNode;
         	newNode.next= head;
         	head=newNode;
         	
    	 }
    	 nodeCount++;
    	 
    }

    /**
     * Inserts an element at the end of the doubly linked list
     * @param data : The new data of class T that needs to be added to the list
     * @return none
     *
     * Worst-case asymptotic runtime cost: Theta(1)
     *
     * Justification:
     *  In all cases the operations are constant so the asymptotic runtime is 1
     */
    public void insertLast( T data ) 
    {
    	 DLLNode newNode = new DLLNode(data,null,null);
    	 if(isEmpty())
    		 {
    		 head=newNode;
    		 }
    	 else {
    		 tail.next= newNode;
         	newNode.prev= tail;
         
    	 }
    	 tail=newNode;
    	 nodeCount++;
    }

    /**
     * Inserts an element in the doubly linked list
     * @param pos : The integer location at which the new data should be
     *      inserted in the list. We assume that the first position in the list
     *      is 0 (zero). If pos is less than 0 then add to the head of the list.
     *      If pos is greater or equal to the size of the list then add the
     *      element at the end of the list.
     * @param data : The new data of class T that needs to be added to the list
     * @return none
     *
     * Worst-case asymptotic runtime cost: Theta(n) 
     *
     * Justification:
     *  if pos>0 and pos<nodeCount, it will reach a for loop that could run up to N times.
     */
    public void insertBefore( int pos, T data ) 
    {
    	T newData=data;
    	if(pos<=0)
    	{
    		insertFirst(newData);
    	}
    	else if(pos>=nodeCount)
    	{
    		insertLast(newData);
    	}
    	
    	else
    	{
    		DLLNode newNode = new DLLNode(newData,null,null);
    		DLLNode tempNode = new DLLNode(data,null,null);
    		tempNode= head;
    		for(int i=0;i<pos;i++)
    		{
    			tempNode=tempNode.next;
    			
    		}
    		tempNode.prev.next=newNode;
    		newNode.prev=tempNode.prev;
    		
    		tempNode.prev=newNode;
			newNode.next=tempNode;
			nodeCount++;
    	}
    }

    
    /**
     * @return the data at the beginning of the list, if the list is non-empty, and null otherwise.
     *
     * Worst-case asymptotic runtime cost: Theta(1)
     *
     * Justification:
     *  In all cases the operations are constant so the asymptotic runtime is 1
     */
    public T getFirst()
    {
      if(isEmpty()){
    	  return null;
      }
      return head.data;
    }

    /**
     * @return the data at the of the list, if the list is non-empty, and null otherwise.
     *
     * Worst-case asymptotic runtime cost: Theta(1)
     *
     * Justification:
     *  In all cases the operations are constant so the asymptotic runtime is 1
     */
    public T getLast()
    {
        if(isEmpty()){
      	  return null;
        }
        return tail.data;
    }

    /**
     * Returns the data stored at a particular position
     * @param pos : the position
     * @return the data at pos, if pos is within the bounds of the list, and null otherwise.
     *
     * Worst-case asymptotic runtime cost: Theta(n)
     *
     * Justification: If pos>0 and pos<nodecount it will reach a for loop and run N times.
     *  
     *
     * Worst-case precise runtime cost: 7 + n + (n-1)
     *
     * Justification:
     *  In the worst cast scenario pos is >0 and <nodeCount-1, meaning it will pass over 3 if statements and and else statement first. 
     *  Meaning there are 4 operations of cost 1. then it will create tempNode, and make tempNode = head. This another 2 operations of cost 1. It will
     *  then run the for loop N times, and the operation inside the for loop N-1 times. Finally it wil return tempNode.data which is another cost of 1.
     *  In total there are 7 single operations costing 1, which means there is a constant value of 7. That plus the for loop of N and its operation of N-1 
     *  is equal to 7 + n + (n-1).
     *
     */
    public T get( int pos ) 
    {
    	if(pos==0) // cost 1
    	{
    		return (getFirst());
    	}
    	else if(pos==nodeCount-1) //cost 1
    	{
    		return(getLast());
    	}
    	if(pos<0||pos>nodeCount-1) // cost 1
    	{
    		 throw new NoSuchElementException("No next element in the list");
    	}
    	
    	else // cost 1
    	{
    		DLLNode tempNode = new DLLNode(null,null,null);// cost 1
    		tempNode= head; // cost 1
    		for(int i=0;i<pos;i++) //cost n
    		{
    			tempNode=tempNode.next; // cost n-1
    			
    		}
    		return tempNode.data; //cost 1
    	}
    	
    }


    /**
     * Deletes an element at the beginning of the doubly linked list
     * @return true : on successful deletion, false : list has not been modified. 
     *
     * Worst-case asymptotic runtime cost: Theta(1)
     *
     * Justification:
     *   In all cases the operations are constant so the asymptotic runtime is 1
     */
    public boolean deleteFirst() 
    {
	 if(isEmpty()){
		 return false;
	 }
	 else if(nodeCount==1)
	 {
		 head=null;tail=null;
		 nodeCount--;
		 return true;
	 }
	 else {
		 head= head.next;
		 nodeCount--;
		 return true;
	 }

    }

    /**
     * Deletes an element at the end of the doubly linked list
     * @param data : The new data of class T that needs to be added to the list
     * @return true : on successful deletion, false : list has not been modified. 
     *
     * Worst-case asymptotic runtime cost: Theta(1)
     *
     * Justification:
     *   In all cases the operations are constant so the asymptotic runtime is 1
     */
    public boolean deleteLast() 
    {
    	 if(isEmpty()){
    		 return false;
    	 }
    	 if(nodeCount==1)
    	 {
    		 head=null;tail=null;
    		 nodeCount--;
    		 return true;
    	 }
    	 else {
    		 tail= tail.prev;
    		 nodeCount--;
    		 tail.next=null;
    		 return true;
    	 }

    }

    /**
     * Deletes the element of the list at position pos.
     * First element in the list has position 0. If pos points outside the
     * elements of the list then no modification happens to the list.
     * @param pos : the position in the list to be deleted.
     * @return true : on successful deletion, false : list has not been modified. 
     *
     * Worst-case asymptotic runtime cost:Theta(n)
     *
     * Justification:
     *  If pos>0 and pos<nodecount it will reach a for loop and run N times.
     */
    public boolean deleteAt( int pos ) 
    {
      
    	if(pos==0)
    	{
    		return (deleteFirst());
    	}
    	else if(pos==nodeCount-1)
    	{
    		return(deleteLast());
    	}
    	
    	else
    	{
    		DLLNode tempNode = new DLLNode(null,null,null);
    		tempNode= head;
    		for(int i=0;i<pos;i++)
    		{
    			tempNode=tempNode.next;
    			
    		}
    		tempNode.next.prev=tempNode.prev;
    		tempNode.prev.next=tempNode.next;
    		tempNode=null;
    		return true;
    	}
    }


    /**
     * @return a string with the elements of the list as a comma-separated
     * list, from beginning to end
     *
     * Worst-case asymptotic runtime cost:   Theta(n)
     *
     * Justification:
     *  We know from the Java documentation that StringBuilder's append() method runs in Theta(1) asymptotic time.
     *  We assume all other method calls here (e.g., the iterator methods above, and the toString method) will execute in Theta(1) time.
     *  Thus, every one iteration of the for-loop will have cost Theta(1).
     *  Suppose the doubly-linked list has 'n' elements.
     *  The for-loop will always iterate over all n elements of the list, and therefore the total cost of this method will be n*Theta(1) = Theta(n).
     */
    public String toString() 
    {
      StringBuilder s = new StringBuilder();
      boolean isFirst = true; 

      // iterate over the list, starting from the head
      for (T i : this)
      {
        if (!isFirst)
        {
          s.append(",");
        } else {
          isFirst = false;
        }
        s.append(i.toString());
      }

      return s.toString();
    }



    /**
     * Returns an iterator over the DLL. The iterator supports the hasNext() and next() methods  but not the remove() method.
     * @return an object implementing the Iterator interface
     */

    public Iterator<T> iterator() { return new DLLIterator(); }

    private class DLLIterator implements Iterator<T>
    {
        private DLLNode current = head;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException("remove() is not supported"); }
        public T next()
        {
            if (current == null)
                throw new NoSuchElementException("No next element in the list");
            T item = current.data;
            current = current.next;
            return item;
        }
    }



    /**
     * Returns a list iterator over the DLL. The iterator supports forwards and backwards iteration over the list but no modification operations.
     * @return an object implementing the ListIterator interface
     */

    public ListIterator<T> listIterator() { return new DLLListIterator(); }

    private class DLLListIterator implements ListIterator<T>
    {
    	private DLLNode current = head;
    	private int index=0;
        /**
         * Returns true if this list iterator has more elements when
         * traversing the list in the forward direction. (In other words,
         * returns true if next() would return an element rather
         * than throwing an exception.)
         *
         * @return true if the list iterator has more elements when
         *         traversing the list in the forward direction
         */
        public boolean hasNext()
        {
        	if (current != null)
        	{
        		return true;
        	}
          return false;
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         * This method may be called repeatedly to iterate through the list,
         * or intermixed with calls to previous() to go back and forth.
         * (Note that alternating calls to next() and previous()
         * will return the same element repeatedly.)
         *
         * @return the next element in the list
         * @throws NoSuchElementException if the iteration has no next element
         */
        public T next()
        {
          if(hasNext())
          {
        
        	  T data=current.data;
        	  current=current.next;
        	  index++;
        	  return data;
        	  
          }
          throw new NoSuchElementException("no next element in the list");
        }

        /**
         * Returns true if this list iterator has more elements when
         * traversing the list in the reverse direction.  (In other words,
         * returns true if previous() would return an element
         * rather than throwing an exception.)
         *
         * @return true if the list iterator has more elements when
         *         traversing the list in the reverse direction
         */
        public boolean hasPrevious()
        {
        	if (current==null||current.prev==null)
        	{
        		return false;
        	}
          return true;
        }

        /**
         * Returns the previous element in the list and moves the cursor
         * position backwards.  This method may be called repeatedly to
         * iterate through the list backwards, or intermixed with calls to
         * next() to go back and forth.  (Note that alternating calls
         * to next() and previous() will return the same
         * element repeatedly.)
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if the iteration has no previous
         *         element
         */
        public T previous()
        {
        	   if(hasPrevious())
               {
             
             	  T data=current.prev.data;
             	  current=current.prev;
             	  index--;
             	  return data;
             	  
               }
               throw new NoSuchElementException("no previous element in the list");
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to next(). (Returns list size if the list
         * iterator is at the end of the list.)
         *
         * @return the index of the element that would be returned by a
         *         subsequent call to next(), or list size if the list
         *         iterator is at the end of the list
         */
        public int nextIndex()
        {
        	if(current==null)
        	{
        		return index;
        	}
        	if(current==tail)
        	{
        		return index+1;
        	}
          return index;
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to previous(). (Returns -1 if the list
         * iterator is at the beginning of the list.)
         *
         * @return the index of the element that would be returned by a
         *         subsequent call to previous(), or -1 if the list
         *         iterator is at the beginning of the list
         */
        public int previousIndex()
        {
        	if(current!=head)
        	{
        		return index-1;
        	}
          return -1;
        }

       public void remove() { throw new UnsupportedOperationException("remove() operation is not supported."); }
       public void set(T e) { throw new UnsupportedOperationException("set() operation is not supported."); }
       public void add(T e) { throw new UnsupportedOperationException("set() operation is not supported."); }

    }

}