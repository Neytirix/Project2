/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/14/2013
 * Project 2
*/

/**
 * AVLTree implements the DataCounter interface and extends 
 * BinarySearchTree. The constructor takes a Comparator<? super E> 
 * "function object" so that items of type E can be compared.  
 * Each tree node associates a count with an E.
 */
public class AVLTree<E> extends BinarySearchTree<E> implements DataCounter<E> {

	/**
	 * Given a Comparator c, creates an empty AVLTree.
	 * Takes a Comparator as an argument.
	 */
	public AVLTree(Comparator<? super E> c) {
		super(c);
	}

	/**
	 * Given a key data of type E, increments that key's count in the 
	 * AVLTree.
	 */
	public void incCount(E data) {
		overallRoot = addNode(data, overallRoot);
	}
	
	/**
	 * Given a key data of type E and a Binary Search Tree, add's 
	 * an AVLNode to the BST with its data field equal to the data
	 * passed and a count of 1. If the AVL Node already exists in the tree,
	 * simply increments the node's count. Returns a new AVLTree with the
	 * added/updated node. 
	 */
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
	
	/**
	 * Given an AVLNode checks the structure property and performs
	 * rotations if necessary to keep the tree balanced. Returns 
	 * a balanced AVLTree at the node passed.
	 */
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
	
	/**
	 * Given a BSTNode returns its height as an int
	 */
	public int height(BSTNode node) {
		if(node == null) {
			return -1;
		}
		return ((AVLNode)node).height;
	}
	
	
	/**
	 * Given an AVLNode current, performs a case 1 single rotation.
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
	 * Given an AVLNode current, performs a case 4 single rotation.
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
	 * Given an AVLNode current, performs a case 2 double rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode doubleRotateLeft(AVLNode current){
		current.left = rotateRightChild((AVLNode) current.left);
		return rotateLeftChild(current);
	}
	
	/**
	 * Given an AVLNode current, performs a case 3 double rotation.
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
