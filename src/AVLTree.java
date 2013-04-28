
public class AVLTree<E> extends BinarySearchTree<E> implements DataCounter<E> {

	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	@Override
	public void incCount(E data) { //how much of this method can i copy from the super?
		if (overallRoot == null) {
			overallRoot = new AVLNode(data);
		}
		AVLNode currentNode = (AVLNode) overallRoot;
		while(true) {
			if (comparator.compare(data, currentNode.data) == 0) {
				currentNode.count++;
				return;
			} else if (comparator.compare(data, currentNode.data) < 0) {
				//traverse left (current is greater than value passed)
				if (currentNode.left == null) {
					currentNode.left = new AVLNode(data);
					return;
				}
				currentNode = (AVLNode) currentNode.left;
				//balance (case 1 or 2)
			} else {
				//traverse right
				if (currentNode.right == null) {
					currentNode.right = new AVLNode(data);
					return;
				}
				currentNode = (AVLNode) currentNode.right;
				//balance (case 3 or 4)
				
			}
		}
		
		
	}

	
	/*
	@Override
	public int getSize() {
		return super.getSize();
	} 

	@Override
	public int getCount(E data) {
		return super.getCount(data);
	} 

	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return super.getIterator();
	}*/

	
	public class AVLNode extends BSTNode {

		//need to add height?
		
		public AVLNode(E d) {
			super(d);
		}	
	}

}
