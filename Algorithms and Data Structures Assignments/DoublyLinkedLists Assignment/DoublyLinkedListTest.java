import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for Doubly Linked List
 *
 *  @author  
 *  @version 2014.01.29
 */
@RunWith(JUnit4.class)
public class DoublyLinkedListTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new DoublyLinkedList<Integer>();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check if insert works at the beginning
     */
    @Test
    public void testInsertFirst()
    {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertFirst(1);
        assertEquals( "Checking insertion at beginning", "1", testDLL.toString() );
        testDLL.insertFirst(2);
        assertEquals( "Checking insertion at beginning", "2,1", testDLL.toString() );
        testDLL.insertFirst(3);        
        assertEquals( "Checking insertion at beginning", "3,2,1", testDLL.toString() );
    }

    // ----------------------------------------------------------
    /**
     * Check if the insert works at the end
     */
    @Test
    public void testInsertLast()
    {
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertLast(1);
        assertEquals( "Checking insertion at end", "1", testDLL.toString() );
        testDLL.insertLast(2);
        assertEquals( "Checking insertion at end", "1,2", testDLL.toString() );
        testDLL.insertLast(3);        
        assertEquals( "Checking insertion at end", "1,2,3", testDLL.toString() );
        assertSame( "Checking insertion at end", 3, testDLL.getLast());
    }

    // ----------------------------------------------------------
    /**
     * Check if the insert works in the middle
     */
    @Test
    public void testInsertBefore()
    {
        // test non-empty list
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertLast(1);
        testDLL.insertLast(2);
        testDLL.insertLast(3);

        testDLL.insertBefore(0,4);
        assertEquals( "Checking insertion at 0", "4,1,2,3", testDLL.toString() );
        testDLL.insertBefore(1,5);
        assertEquals( "Checking insertion at 1", "4,5,1,2,3", testDLL.toString() );
        testDLL.insertBefore(2,6);        
        assertEquals( "Checking insertion at 2", "4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(-1,7);        
        assertEquals( "Checking insertion at -1", "7,4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(7,8);  
        assertSame("cunt",8,testDLL.nodeCount);
        assertEquals( "Checking insertion at 2", "7,4,5,6,1,2,3,8", testDLL.toString() );
        testDLL.insertBefore(700,9);        
        assertEquals( "Checking insertion at 700", "7,4,5,6,1,2,3,8,9", testDLL.toString() );

        // test empty list
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);        
        assertEquals( "Checking insertion at 0", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(10,1);        
        assertEquals( "Checking insertion at 10", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(-10,1);        
        assertEquals( "Checking insertion at -10", "1", testDLL.toString() );
     }

    // TODO: add more tests here. Each line of code in DoublyLinkedList.java should
    // be executed at least once from at least one test.




	@Test
	public void testDoublyLinkedList() {
		DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
		assertEquals( "checking if list made,", "", testDLL.toString() );
	}

	@Test
	public void testIsEmpty() {
	    DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.isEmpty();
        assertSame( "Checking if empty is", true, testDLL.isEmpty() );
	    testDLL.insertFirst(10);
        testDLL.isEmpty();
        assertSame( "Checking if empty is ", false, testDLL.isEmpty() );
    
	}


	@Test
	public void testGetFirst() {
		 DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
	        testDLL.getFirst();
	        assertEquals( "Checking first node is ", null, testDLL.getFirst() ); 
		    testDLL.insertFirst(10);
	        testDLL.getFirst();
	        assertSame( "Checking first node is ", 10, testDLL.getFirst() );
	        testDLL.insertFirst(11);
	        testDLL.getFirst();
	        assertSame( "Checking first node is", 11, testDLL.getFirst() );
	
	}

	@Test
	public void testGetLast() {
		 DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
	        testDLL.getLast();
	        assertEquals( "Checking last node is", null, testDLL.getFirst() ); 
		    testDLL.insertFirst(10);
	        testDLL.getLast();
	        assertSame( "Checking last node is", 10, testDLL.getFirst() );
	        testDLL.insertLast(13);
	        testDLL.getLast();
	        assertSame( "Checking last node is", 13, testDLL.getLast() );
	}

	@Test
	public void testGet() {
		DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        assertSame( "Checking node at pos 0 is", null, testDLL.get(0) ); 
	    testDLL.insertFirst(10);
	    assertSame( "Checking node at pos 0 is", 10, testDLL.get(0) ); 
	    testDLL.insertFirst(12);
	    assertSame( "Checking node at pos 0 is", 12, testDLL.get(0) );
	    testDLL.insertFirst(10);
	    testDLL.insertLast(10);
	    assertEquals("checking delete first node", "10,12,10,10", testDLL.toString());
	    assertSame( "Checking node at pos 2 is", 10, testDLL.get(2) );
	    boolean noElement = false;
	    try{
			testDLL.get(8);
		}catch (NoSuchElementException e){
			noElement=true;
		}
		assertSame("check exception",true, noElement);
		 noElement = false;
		    try{
				testDLL.get(-2);
			}catch (NoSuchElementException e){
				noElement=true;
			}
			assertSame("check exception",true, noElement);
		
	}

	@Test
	public void testDeleteFirst() {
		DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
		assertSame("checking delete first node", false, testDLL.deleteFirst());
	    testDLL.insertLast(10);
	    assertSame("checking delete first node",true, testDLL.deleteFirst());
	  
	    testDLL.insertFirst(12);
	    assertEquals("checking delete first node", "12", testDLL.toString());
	    
	    testDLL.insertFirst(10);
	    assertEquals("checking delete first node", "10,12", testDLL.toString());

	    assertSame("checking delete first node", true, testDLL.deleteFirst());
	    assertEquals("checking delete first node", "12", testDLL.toString());
	}

	@Test
	public void testDeleteLast() {
		DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
		assertSame("checking delete last node", false, testDLL.deleteLast());
	    testDLL.insertFirst(10);
	    assertSame("checking delete last node",true, testDLL.deleteLast());
	    assertEquals("checking delete first node", "", testDLL.toString());
	    testDLL.insertLast(12);
	    testDLL.insertFirst(10);
	    assertSame("nodcount",2,testDLL.nodeCount);
	    assertEquals("checking delete first node", "10,12", testDLL.toString());
	    assertSame("checking delete Last node", true, testDLL.deleteLast());
	    assertEquals("checking delete first node", "10", testDLL.toString());
	}

	@Test
	public void testDeleteAt() {
		DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
		assertSame("checking delete node at pos 0", false, testDLL.deleteAt(0));
	    testDLL.insertFirst(10);
	    assertSame("checking delete node at pos 0",true, testDLL.deleteAt(0));
	    assertEquals("checking string", "", testDLL.toString());
	    testDLL.insertLast(12);
	    testDLL.insertFirst(10);
	    assertEquals("checking string", "10,12", testDLL.toString());
	    assertSame("checking delete node at pos 1", true, testDLL.deleteAt(1));
	    assertEquals("checking string", "10", testDLL.toString());
	    testDLL.insertFirst(9);
	    testDLL.insertFirst(8);
	    testDLL.insertFirst(7);
	    assertEquals("checking string", "7,8,9,10", testDLL.toString());
	    assertSame("checking delete node at pos 2", true, testDLL.deleteAt(2));
	    assertEquals("checking delete node at pos 0", "7,8,10", testDLL.toString());
	    
	}

	@Test
	public void testToString() {
		    DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
		    testDLL.toString();
	        assertEquals( "Checking insertion at beginning", "", testDLL.toString() ); 
		    testDLL.insertFirst(10);
	        testDLL.toString();
	        assertEquals( "Checking insertion at beginning", "10", testDLL.toString() );
	
	}

	@Test
	public void testIterator() {
		DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
		Iterator<Integer> iterator = testDLL.iterator();
		assertSame("checking hasNext on emptyList",false, iterator.hasNext());
		
		boolean isNoElException=false;
		try{
			iterator.next();
		}catch (NoSuchElementException n){
			isNoElException=true;
		}
		assertSame("check exception",true, isNoElException);
		
		
		boolean isUnsException=false;
		try{
			iterator.remove();
		}catch (UnsupportedOperationException e){
			isUnsException=true;
		}
		assertSame("check exception",true, isUnsException);
	
		
		DoublyLinkedList<Integer> testDLL2 = new DoublyLinkedList<Integer>();
		testDLL2.insertFirst(3);
		testDLL2.insertFirst(2);
		Iterator<Integer> iterator2 = testDLL2.iterator();
		assertSame("checking hasNext",true, iterator2.hasNext());
		assertSame("checking next",2, iterator2.next());
		assertSame("checking hasNext",true, iterator2.hasNext());
		assertSame("checking next",3, iterator2.next());
		
		
		

		
	
	}
	@Test
	public void testListIterator() {
		DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
		ListIterator<Integer> listIterator = testDLL.listIterator();
		boolean isNoElException1=false;
		try{
			listIterator.previous();
		}catch (NoSuchElementException e){
			isNoElException1=true;
		}
		assertSame("check exception",true, isNoElException1);
		
		boolean isNoElException2=false;
		try{
			listIterator.next();
		}catch (NoSuchElementException e){
			isNoElException2=true;
		}
		assertSame("check exception",true, isNoElException2);
		 
		
		assertSame("checking hasNext on emptyList",false, listIterator.hasNext());
		assertSame("checking prev index on 1 element",-1, listIterator.previousIndex());
		assertSame("checking next index on 1 element",0, listIterator.nextIndex());
		assertSame("checking hasPrevious on emptyList",false, listIterator.hasPrevious());
		testDLL.insertFirst(3);
		assertSame("checking next index on 1 element",0, listIterator.nextIndex());
		assertSame("checking prev index on 1 element",-1, listIterator.previousIndex());
		
		
		
		
		DoublyLinkedList<Integer> testDLL2 = new DoublyLinkedList<Integer>();
		testDLL2.insertFirst(3);
		testDLL2.insertFirst(2);
		ListIterator<Integer> listIterator2 = testDLL2.listIterator();
		assertSame("checking hasNext",true, listIterator2.hasNext());
		assertSame("checking next",2, listIterator2.next());	
		assertSame("checking hasNext",true, listIterator2.hasNext());
		assertSame("checking previous",true, listIterator2.hasPrevious());
 		assertSame("checking next index",2, listIterator2.nextIndex());
		assertSame("checking prev index",0, listIterator2.previousIndex());
		assertSame("checking previous",2, listIterator2.previous());
		
		boolean isUnsException=false;
		try{
			listIterator2.remove();
		}catch (UnsupportedOperationException e){
			isUnsException=true;
		}
		assertSame("check exception",true, isUnsException);
		
		isUnsException=false;
		try{
			listIterator2.set(5);
		}catch (UnsupportedOperationException e){
			isUnsException=true;
		}
		assertSame("check exception",true, isUnsException);
		
		isUnsException=false;
		try{
			listIterator2.add(4);
		}catch (UnsupportedOperationException e){
			isUnsException=true;
		}
		assertSame("check exception",true, isUnsException);
	}


}