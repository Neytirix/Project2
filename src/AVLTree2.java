public class AVLTree2<E> extends BinarySearchTree<E> implements DataCounter<E> {

	public AVLTree2(Comparator<? super E> c) {
		super(c);
	}

	public void incCount(E data) {
		overallRoot = addNode(data, overallRoot);
	}
	
	
	public AVLNode addNode(E data, BSTNode node) {
		AVLNode currentNode = (AVLNode) node;
		if (currentNode == null) {
			currentNode = new AVLNode(data);
			return currentNode;
		} else if (comparator.compare(data, currentNode.data) == 0) {
			currentNode.count++;
		} else if (comparator.compare(data, currentNode.data) < 0) {
			//traverse left (current is greater than value passed)
			currentNode.left = addNode(data, currentNode.left);
		} else {
			//traverse right (current is less than value passed)
			currentNode.right = addNode(data, currentNode.right);
		}
		return balance(currentNode);
	}
	
	public AVLNode balance(AVLNode node) {
		if(node == null) {
			return node;
		}
		if (height(node.left) - height(node.right) > 1) {
			if(height(node.left.left) >= height(node.left.right)) {
				node = rotateLeftChild(node);
			} else {
				node = doubleRotateLeft(node);
			}			
		} else if (height(node.right) - height(node.left) > 1){
			if(height(node.right.right) >= height(node.right.left)) {
				node = rotateRightChild(node);
			} else {
				node = doubleRotateRight(node);
			}
		}
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}
	
	public int height(BSTNode node) {
		if(node == null) {
			return -1;
		}
		return ((AVLNode)node).height;
	}
	
	
	/**
	 * Given a BSTNode current, performs a case 1 single rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode rotateLeftChild(AVLNode current) {
		AVLNode temp = (AVLNode) current.left;
		current.left = temp.right;
		temp.right = current;
		current.height = 1 + Math.max(height(current.left), height(current.right));
		temp.height = 1 + Math.max(height(temp.left), current.height);
		return temp;
	}
	
	/**
	 * Given a BSTNode current, performs a case 4 single rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode rotateRightChild(AVLNode current) {
		AVLNode temp = (AVLNode) current.right;
		current.right = temp.left;
		temp.left = current;
		current.height = 1 + Math.max(height(current.right), height(current.left));
		temp.height = 1 + Math.max(height(temp.right), current.height);
		return temp;
	}
	
	/**
	 * Given a BSTNode current, performs a case 2 double rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode doubleRotateLeft(AVLNode current){
		current.left = rotateRightChild((AVLNode) current.left);
		return rotateLeftChild(current);
	}
	
	/**
	 * Given a BSTNode current, performs a case 3 double rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode doubleRotateRight(BSTNode current){
		current.right = rotateLeftChild((AVLNode) current.right);
		return rotateRightChild((AVLNode) current);
	}
	
		
	
	
	/**
	 * Given a BSTNode root, calculates the height of the root.
	 * Throws IllegalStateException if the height fields store
	 * incorrect values or if the tree is unbalanced. 
	 */
	private int calcHeight(BSTNode root) {
		AVLNode node = (AVLNode) root;		
		if (node == null) 
			return -1;
		int leftHeight = calcHeight((AVLNode) node.left);
		int rightHeight = calcHeight((AVLNode) node.right);
		if (node.height != Math.max(leftHeight, rightHeight) + 1) {
			throw new IllegalStateException("Height fields are incorrect");
		}
		if (Math.abs(leftHeight - rightHeight) > 1) {
			throw new IllegalStateException("Tree is unbalanced");
		}
		return node.height;
	}
	
	/**
	 * Verifies the structure property of the AVL tree.
	 * Throws IllegalStateException if the height fields store
	 * incorrect values or if the tree is unbalanced. 
	 */
	public void verifyHeight() {
		calcHeight(overallRoot);
	}
	
	
	
	
	/**
	 * Helper class for AVL nodes storing generic types
	 */
	private class AVLNode extends BSTNode {

		public int height;
		
		/**
		 * Creates an AVLNode with element d of type E, setting count
		 * equal to 1 and height equal to 0
		 */
		public AVLNode(E d) {
			super(d);
			height = 0;
		}	
	}
}
