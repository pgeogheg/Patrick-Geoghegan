
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
 *  Test class for Doubly Linked List
 *
 *  @version 3.0 26/11/14 16:53:05
 *
 *  @author  TODO
 */

@RunWith(JUnit4.class)


public class BSTTest
{
	
@Test  
public void height() {
	BST<Integer, Integer> bst = new BST<Integer, Integer>();
	     
	assertSame("Checking the height", -1, bst.height());
    bst.put(7, 7);   //        _7_
    assertSame("Checking the height", 0, bst.height());
    bst.put(8, 8);   //      /     \
    bst.put(3, 3);   //    _3_      8
    bst.put(1, 1);   //  /     \
    bst.put(2, 2);   // 1       6
    bst.put(6, 6);   //  \     /
    bst.put(4, 4);   //   2   4
    bst.put(5, 5);   //        \
                     //         5

    assertSame("Checking the height", 4, bst.height());
	}

@Test 
public void printKeysInOrder() {
	BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
    assertEquals("Checking the height", "()", bst1.printKeysInOrder());
    bst1.put(7, 7);   //        _7_
    assertEquals("Checking the height", "(()7())", bst1.printKeysInOrder());
    bst1.put(8, 8);   //      /     \
    bst1.put(3, 3);   //    _3_      8
    bst1.put(1, 1);   //  /     \
    bst1.put(2, 2);   // 1       6
    bst1.put(6, 6);   //  \     /
    bst1.put(4, 4);   //   2   4
    bst1.put(5, 5);   //        \
    				  //         5
    assertEquals("Checking the height", "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst1.printKeysInOrder());
	}
  



@Test 
public void get() {
	BST<Integer, Integer> bst1 = new BST<Integer, Integer>();

    bst1.put(7, 7);   //        _7_
    bst1.put(8, 8);   //      /     \
    bst1.put(3, 3);   //    _3_      8
    bst1.put(1, 1);   //  /     \
    bst1.put(2, 2);   // 1       6
    bst1.put(6, 6);   //  \     /
    bst1.put(4, 4);   //   2   4
    bst1.put(5, 5);   //        \
    				  //         5
    assertSame("Checking the height", 7, bst1.get(7));
    assertSame("Checking the height", 5, bst1.get(5));
	}
  

@Test 
public void contains() {
	BST<Integer, Integer> bst1 = new BST<Integer, Integer>();

    bst1.put(7, 7);   //        _7_
    bst1.put(8, 8);   //      /     \
    bst1.put(3, 3);   //    _3_      8
    bst1.put(1, 1);   //  /     \
    bst1.put(2, 2);   // 1       6
    bst1.put(6, 6);   //  \     /
    bst1.put(4, 4);   //   2   4
    bst1.put(5, 5);   //        \
    				  //         5
    assertSame("Checking the height", true, bst1.contains(7));
	}
  
 



@Test 
public void median() {
	BST<Integer, Integer> bst1 = new BST<Integer, Integer>();
	
	assertSame("get median ", null, bst1.median());
	     
    bst1.put(7, 7);   //        _7_
    assertSame("get median ", 7, bst1.median());
    bst1.put(8, 8);   //      /     \
    bst1.put(3, 3);   //    _3_      8
    bst1.put(1, 1);   //  /     \
    bst1.put(2, 2);   // 1       6
    bst1.put(6, 6);   //  \     /
    bst1.put(4, 4);   //   2   4
    bst1.put(5, 5);   //        \
    				  //         5

    assertSame("get median ", 4, bst1.median());
	}

 @Test
 public void testPrettyPrint() {
     BST<Integer, Integer> bst = new BST<Integer, Integer>();
     assertEquals("Checking pretty printing of empty tree",
             "-null", bst.prettyPrintKeys());
      
                          //  -7
                          //   |-3
                          //   | |-1
                          //   | | |-null
     bst.put(7, 7);       //   | |  -2
     bst.put(8, 8);       //   | |   |-null
     bst.put(3, 3);       //   | |    -null
     bst.put(1, 1);       //   |  -6
     bst.put(2, 2);       //   |   |-4
     bst.put(6, 6);       //   |   | |-null
     bst.put(4, 4);       //   |   |  -5
     bst.put(5, 5);       //   |   |   |-null
                          //   |   |    -null
                          //   |    -null
                          //    -8
                          //     |-null
                          //      -null
     
     String result = 
      "-7\n" +
      " |-3\n" + 
      " | |-1\n" +
      " | | |-null\n" + 
      " | |  -2\n" +
      " | |   |-null\n" +
      " | |    -null\n" +
      " |  -6\n" +
      " |   |-4\n" +
      " |   | |-null\n" +
      " |   |  -5\n" +
      " |   |   |-null\n" +
      " |   |    -null\n" +
      " |    -null\n" +
      "  -8\n" +
      "   |-null\n" +
      "    -null\n";
     assertEquals("Checking pretty printing of non-empty tree", result, bst.prettyPrintKeys());
     }

  
     /** <p>Test {@link BST#delete(Comparable)}.</p> */
     @Test
     public void testDelete() {
         BST<Integer, Integer> bst = new BST<Integer, Integer>();
         bst.delete(1);
         assertEquals("Deleting from empty tree", "()", bst.printKeysInOrder());
         
         bst.put(7, 7);   //        _7_
         bst.put(8, 8);   //      /     \
         bst.put(3, 3);   //    _3_      8
         bst.put(1, 1);   //  /     \
         bst.put(2, 2);   // 1       6
         bst.put(6, 6);   //  \     /
         bst.put(4, 4);   //   2   4
         bst.put(5, 5);   //        \
                          //         5
         
         assertEquals("Checking order of constructed tree",
                 "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
         
         bst.delete(9);
         assertEquals("Deleting non-existent key",
                 "(((()1(()2()))3((()4(()5()))6()))7(()8()))", bst.printKeysInOrder());
 
         bst.delete(8);
         assertEquals("Deleting leaf", "(((()1(()2()))3((()4(()5()))6()))7())", bst.printKeysInOrder());
 
         bst.delete(6);
         assertEquals("Deleting node with single child",
                 "(((()1(()2()))3(()4(()5())))7())", bst.printKeysInOrder());
 
         bst.delete(3);
         assertEquals("Deleting node with two children",
                 "(((()1())2(()4(()5())))7())", bst.printKeysInOrder());
     }
     
}
