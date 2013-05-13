import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;


import org.junit.Before;
import org.junit.Test;


public class TestQuickSort {
	private static final int TIMEOUT = 2000;
	private Comparator<Integer> comp;
	
	@Before
	public void setUp() throws Exception {
		comp = new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return (Integer) e1 - (Integer) e2;
			}
		};
	}
	

	private void sort(Integer[] array) {
		WordCount.quickSort(array, 0, array.length, comp);
	}
	
	@Test(timeout = TIMEOUT)
	public void emptyTest() {
		Integer[] array = new Integer[0];
		sort(array);
		assertEquals("Empty Array", 0, array.length);
	}
	
	@Test(timeout = TIMEOUT)
	public void singleTest() {
		Integer[] array = new Integer[1];
		array[0] = 1;
		sort(array);
		assertEquals("One Element", new Integer(1), array[0]);
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleNoSortTest() {
		Integer[] array = new Integer[]{1,3,4,5};
		sort(array);
		assertArrayEquals("Pre sorted", new Integer[]{1,3,4,5}, array);
	}
	
	@Test(timeout = TIMEOUT)
	public void twoSortTest() {
		Integer[] array = new Integer[]{3,1};
		sort(array);
		assertArrayEquals("Two Elements", new Integer[]{1,3}, array);
	}
	
	@Test(timeout = TIMEOUT)
	public void duplicateTest() {
		Integer[] array = new Integer[]{3,3};
		sort(array);
		assertArrayEquals("Duplicate Elements", new Integer[]{3,3}, array);
	}
	
	@Test(timeout = TIMEOUT)
	public void duplicateSortTest() {
		Integer[] array = new Integer[]{1,3,3,1};
		sort(array);
		assertArrayEquals("Duplicate Sort Elements", new Integer[]{1,1,3,3}, array);
	}
	
	@Test(timeout = TIMEOUT)
	public void multipleSortTest() {
		Integer[] array = new Integer[]{3,1, 9, 4};
		sort(array);
		assertArrayEquals("Four Elements", new Integer[]{1,3,4,9}, array);
	}

	@Test(timeout = TIMEOUT)
	public void hundredSortTest() {
		Integer[] array = new Integer[100];
		Integer[] expected = new Integer[100];
		for(int i = 0; i < 100; i++) {
			array[i] = 99-i;
			expected[i] = i;
		}
		sort(array);
		assertArrayEquals("Hundred Elements", expected, array);
	}

	
}
