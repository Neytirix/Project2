/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/14/2013
 * Project 2
*/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestMoveToFrontList {
	
	private static final int TIMEOUT = 2000;
	MoveToFrontList<Integer> intMover;
	
	@Before
	public void setUp() throws Exception {
		intMover = new MoveToFrontList<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return e1 - e2;
			};
		});
	}

	@Test(timeout = TIMEOUT) 
	public void sizeEmptyTest() {
		assertEquals("Empty",0,intMover.getSize());
	}
	
	@Test (timeout = TIMEOUT)
	public void singleIncTest() {
		intMover.incCount(0);
		assertNotNull(intMover);
	}
	
	@Test (timeout = TIMEOUT)
	public void hundredIncTest() {
		for(int x = 0; x < 100; x++)
			intMover.incCount(0);
		assertNotNull(intMover);
	}

	@Test (timeout = TIMEOUT)
	public void altIncTest() {
		intMover.incCount(0);
		intMover.incCount(1);
		intMover.incCount(0);
		assertNotNull(intMover);
	}
	
	@Test (timeout = TIMEOUT)
	public void multipleAltIncTest() {
		for(int x = 0; x<100; x++) {
			intMover.incCount(0);
			intMover.incCount(1);
		}
		assertNotNull(intMover);
	}
	
	@Test (timeout = TIMEOUT)
	public void multipleIncTest() {
		for(int x = 0; x<100; x++) {
			intMover.incCount(0);
			intMover.incCount(1);
			intMover.incCount(2);
			intMover.incCount(3);
		}
		assertNotNull(intMover);
	}
	
	public void negativeIncTest() {
		intMover.incCount(-1);
		
	}
	
	@Test (timeout = TIMEOUT)
	public void singleSizeTest() {
		intMover.incCount(0);
		assertEquals("Single insert size",1,intMover.getSize());
	}
	
	@Test (timeout = TIMEOUT)
	public void doubleSizeTest() {
		intMover.incCount(0);
		intMover.incCount(1);
		assertEquals("Double insert size",2,intMover.getSize());
	}
	
	@Test (timeout = TIMEOUT)
	public void singleCountTest() {
		intMover.incCount(0);
		assertEquals("Count the only zero",1,intMover.getCount(0));
	}
	
	@Test (timeout = TIMEOUT)
	public void doubleCountTest() {
		intMover.incCount(0);
		intMover.incCount(0);
		assertEquals("Count the two zeroes",2,intMover.getCount(0));
	}
	
	@Test (timeout = TIMEOUT)
	public void randomCountTest() {
		int rand = (int) Math.floor(Math.random() * 100);
		for(int x = 0; x < rand; x++) {
			intMover.incCount(0);
		}
		assertEquals("Count the randomized number of inserts",rand,intMover.getCount(0));
	}
	
	@Test (timeout = TIMEOUT)
	public void twoIncCountTest() {
		intMover.incCount(0);
		intMover.incCount(1);
		assertEquals("Count the only zero when another number is inserted", 1 ,intMover.getCount(0));
	}
	
	@Test (timeout = TIMEOUT)
	public void hundredIncCountTest() {
		for(int x = 0; x < 100; x++) {
			intMover.incCount(0);
		}
		assertEquals("Count 100 zeroes!", 100 ,intMover.getCount(0));
	}	
	
	
}
