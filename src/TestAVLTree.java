import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class TestAVLTree {
	private static final int TIMEOUT = 2000;
	AVLTree tree;
	@Before
	public void setUp() throws Exception {
		tree = new AVLTree<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return e1 - e2;
			};
		});
	}
	
	@Test(timeout = TIMEOUT)
	public void treeHasSize0WhenConstructed(){
		assertEquals("Constructed a tree and checked its size", 
			     0, tree.getSize());
	}
	
	private void addAndTestSize(int[] nums, int unique){
		for(int i = 0; i < nums.length; i++)
			tree.incCount(nums[i]);
		assertEquals("Added list of numbers " + intArrayToString(nums) + " to tree", 
			     unique, tree.getSize());
	}
	
	@Test(timeout = TIMEOUT) 
	public void treeHasSize1AfterAdding5(){
		addAndTestSize(new int[]{5}, 1);
	}
	
	@Test(timeout = TIMEOUT)
	public void treeHasSize1AfterAddingRepeat5s(){
		addAndTestSize(new int[]{5,5}, 1);
	}

	@Test(timeout = TIMEOUT)
	public void treeHasSize5AfterAdding0To4(){
		addAndTestSize(new int[]{0,1,2,3,4}, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void treeHasSize5AfterAddingRepeat0To4s(){
		addAndTestSize(new int[]{0,0,1,1,2,2,3,3,4,4}, 5);
	}
	
	@Test(timeout = TIMEOUT)
	public void treeHasSize5AfterAddingRepeat0To4InSequence(){
		addAndTestSize(new int[]{0,1,2,3,4,0,1,2,3,4}, 5);
	}

	private void addAndGetCount(int[] nums, int getThis, int expected){
		for(int i = 0; i < nums.length; i++)
			tree.incCount(nums[i]);
		int actual = tree.getCount(getThis);
		assertEquals("Added " + intArrayToString(nums) + " and got count of " + getThis, 
				expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void treeHas7Of9(){
		addAndGetCount(new int[]{9, 9, 9, 9, 9, 9, 9}, 9, 7);
	}
	
	@Test(timeout = TIMEOUT)
	public void treeHas3Of5(){
		addAndGetCount(new int[]{0, 5, -1, 5, 1, 5, 2}, 5, 3);
	}
	
	@Test(timeout = TIMEOUT)
	public void iteratorGetsAllDataFromTree(){
		int[] startArray = {7, -5, -5, -6, 6, 10, -9, 4, 8, 6};
		String startArrayToString = intArrayToString(startArray);
		for(int i = 0; i < startArray.length; i++)
			tree.incCount(startArray[i]);
		// the expected array has no duplicates and is sorted
		int[] expected = {-9, -6, -5, 4, 6, 7, 8, 10};
		
		// construct the actual array returned by the iterator
		SimpleIterator<DataCount<Integer>> iter = tree.getIterator();
		int[] actual = new int[expected.length];
		int i = 0;
		while(iter.hasNext())
			actual[i++] = iter.next().data;
		Arrays.sort(actual);
		assertArrayEquals("Added " + startArrayToString + " and used iterator", 
				expected, actual);
	}
	
	@Test(timeout = TIMEOUT, expected = java.util.NoSuchElementException.class)
	public void iteratorThrows(){
		// Start by adding an item, because we get NullPointerException if 
		// we try to create an iterator from an empty tree. 
		tree.incCount(21); 
		SimpleIterator<DataCount<Integer>> iter = tree.getIterator();
		iter.next(); // OK (should return 21)
		iter.next(); // Exception!
	}
	/* The above is equivalent to doing something like
	 * try{ ...; iter.next(); fail("should have crashed!") }
	 * catch(NoSuchElementException e){ }
	 */
	
	// pre: nums is not null
	// post: returns a String representation of nums 
	// in the form of {1,2,3,4}
	private String intArrayToString(int[] nums){
		if(nums.length == 0)
			return "{}";
		String s = "{" + nums[0];
		for(int i = 1; i < nums.length; i++)
			s += "," + nums[i];
		s += "}";
		return s;
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
