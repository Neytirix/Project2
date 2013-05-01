import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestFourHeap {
	private static final int TIMEOUT = 2000;
	FourHeap<Integer> heap;


	@Before
	public void setUp() throws Exception {
		heap = new FourHeap<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return e1 - e2;
			};
		});
	}

	@Test(timeout = TIMEOUT) 
	public void emptyTest() {
		assertTrue("Initially empty",heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT) 
	public void insertRemoveEmptyTest() {
		heap.insert(0);
		heap.deleteMin();
		assertTrue("Sole value deleted",heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT) 
	public void altInsertDeleteEmptyTest() {
		for(int x = 0; x<100;x++) {
			heap.insert(x);
			heap.deleteMin();
		}
		assertTrue("Alternating insert",heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT) 
	public void notEmptyTest() {
		heap.insert(0);
		assertFalse("0 is inserted",heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT) 
	public void notEmptyTest2() {
		heap.insert(1);
		assertFalse("1 is inserted",heap.isEmpty());
	}
	
	@Test(timeout = TIMEOUT) 
	public void twoInsertNotEmptyTest() {
		heap.insert(0);
		heap.insert(1);
		assertFalse("2 inserts",heap.isEmpty());
	}
	

	@Test(timeout = TIMEOUT) 
	public void insertTest() {
		heap.insert(1);
		assertNotNull("Simple insert",heap);
	}
	
	@Test(timeout = TIMEOUT) 
	public void hundredInsertTest() {
		for(int x = 0; x<100;x++) {
			heap.insert(x);
		}
		assertNotNull("Hundred insert",heap);
	}
	
	@Test(timeout = TIMEOUT) 
	public void altInsertDeleteTest() {
		for(int x = 0; x<100;x++) {
			heap.insert(x);
			heap.deleteMin();
		}
		assertNotNull("Alternating insert",heap);
	}

	
	
	@Test(timeout = TIMEOUT) 
	public void findTest() {
		heap.insert(1);
		int expected = heap.findMin();
		assertEquals("Simple find",1, expected);
	}
	
	@Test(timeout = TIMEOUT) 
	public void negativeFindTest() {
		heap.insert(-20);
		int expected = heap.findMin();
		assertEquals("Negative find",-20, expected);
	}
	
	@Test(timeout = TIMEOUT) 
	public void twoInsertFindTest() {
		heap.insert(0);
		heap.insert(1);
		int expected = heap.findMin();
		assertEquals("2 inserts, find",0, expected);
	}
	
	@Test(timeout = TIMEOUT) 
	public void threeInsertNegativeFindTest() {
		heap.insert(-20);
		heap.insert(-10);
		heap.insert(10);
		int expected = heap.findMin();
		assertEquals("3 inserts with two negatives, find",-20, expected);
	}
	
	
	@Test(timeout = TIMEOUT) 
	public void duplicateFindTest() {
		heap.insert(1);
		heap.insert(1);
		heap.insert(1);
		heap.insert(1);
		int expected = heap.findMin();
		assertEquals("Duplicate insert, find",1, expected);
	}
	
	@Test(timeout = TIMEOUT) 
	public void duplicateFindTest2() {
		heap.insert(4);
		heap.insert(1);
		heap.insert(2);
		heap.insert(1);
		heap.insert(2);
		int expected = heap.findMin();
		assertEquals("Duplicate with others, find",1, expected);
	}

	@Test(timeout = TIMEOUT) 
	public void duplicateFindTest3() {
		heap.insert(4);
		heap.insert(2);
		heap.insert(2);
		heap.insert(1);
		heap.insert(1);
		int expected = heap.findMin();
		assertEquals("Duplicate with others, find",1, expected);
	}
	
	@Test(timeout = TIMEOUT) 
	public void duplicateFindTest4() {
		heap.insert(7);
		heap.insert(2);
		heap.insert(4);
		heap.insert(4);
		heap.insert(1);
		int expected = heap.findMin();
		assertEquals("Duplicate with others, find",1, expected);
	}
	
	@Test(timeout = TIMEOUT) 
	public void hundredFindTest() {
		for(int x = 0; x<100;x++) {
			heap.insert(x);
			int z = heap.findMin();
			assertEquals("Min remains the same after multiple calls to find",0, z);
		}
	}
	
	@Test(timeout = TIMEOUT) 
	public void removeTest() {
		heap.insert(1);
		int expected = heap.deleteMin();
		assertEquals("Simple delete",1, expected);
	}
	
	@Test(timeout = TIMEOUT) 
	public void hundredRemoveTest() {
		for(int x = 0; x<100;x++) {
			heap.insert(x);
		}
		for(int x = 0; x<100;x++) {
			int expected = heap.deleteMin();
			assertEquals("Repeated deletes",x, expected);
		}
	}
	

	
}
