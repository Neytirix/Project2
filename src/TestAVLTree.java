import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestAVLTree {

	AVLTree tree;
	@Before
	public void setUp() throws Exception {
		tree = new AVLTree<Integer>(new Comparator<Integer>() {
			public int compare(Integer e1, Integer e2) {
				return e1 - e2;
			};
		});
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
