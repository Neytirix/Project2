/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/1/2013
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
	 * Given a Comparator c, constructs an AVLTree object
	 * Takes a Comparator as an argument.
	 */
	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	/**
	 * Given data of type E, increments its count in the AVLTree.
	 */
		@Override
	public void incCount(E data) { 
		if (overallRoot == null) {
			overallRoot = new AVLNode(data);
			((AVLNode) overallRoot).height = 0;
		} else {
			((AVLNode) overallRoot).height = addNode(data, overallRoot);
		}		
	}
	
	/**
	 * Given an AVLNode imbalanceNode, balances the subtree at that node
	 * to preserve the structure property.
	 */
	private void balanceChildren(AVLNode imbalanceNode) {
		int leftHeight;
		int rightHeight;
		if(imbalanceNode.right == null) {
			rightHeight = 0;
		} else {
			rightHeight = ((AVLNode) imbalanceNode.right).height;
		}
		if(imbalanceNode.left == null) {
			leftHeight = 0;
		} else {
			leftHeight = ((AVLNode) imbalanceNode.left).height;
		}
		if(leftHeight - rightHeight > 1) {
			if(imbalanceNode.left.right == null) {
				rightHeight = 0;
			} else {
				rightHeight = ((AVLNode) imbalanceNode.left.right).height;
			}
			if(imbalanceNode.left.left == null) {
				leftHeight = 0;
			} else {
				leftHeight = ((AVLNode) imbalanceNode.left.left).height;
			}
			if((leftHeight - rightHeight) > 1) {
				rotateLeftChild(imbalanceNode);
			} 
			if((rightHeight - leftHeight) > 1) {
				doubleRotateLeft(imbalanceNode);
			}
		} 
		if((rightHeight - leftHeight) > 1) {
			if(imbalanceNode.right.left == null) {
				rightHeight = 0;
			} else {
				rightHeight = ((AVLNode) imbalanceNode.right.left).height;
			}
			if(imbalanceNode.right.left == null) {
				leftHeight = 0;
			} else {
				leftHeight = ((AVLNode) imbalanceNode.right.left).height;
			}
			if((leftHeight - rightHeight) > 1) {
				doubleRotateRight(imbalanceNode);
			} 
			if((rightHeight - leftHeight) > 1) {
				rotateRightChild(imbalanceNode);
			}
		}
	}
	
	/**
	 * Given data of type E and a BSTNode node, adds the node
	 * in the proper location preserving the BST property.
	 */
	private int addNode(E data, BSTNode node) {
		AVLNode currentNode = (AVLNode) node;
		int lastHeight;
		if (comparator.compare(data, currentNode.data) == 0) {
			currentNode.count++;
			return 0;
		} else if (comparator.compare(data, currentNode.data) < 0) {
			//traverse left (current is greater than value passed)
			if (currentNode.left == null) {
				currentNode.left = new AVLNode(data);
				
				((AVLNode) currentNode.left).height = 0;
				return 0;
			}
			lastHeight = addNode(data, currentNode.left);
		} else {
			//traverse right (current is less than value passed)
			if (currentNode.right == null) {
				currentNode.right = new AVLNode(data);
				((AVLNode) currentNode.right).height = 0;
				return 0;
			}
			lastHeight = addNode(data, currentNode.right);
		}
		currentNode.height = lastHeight+1;
		int leftHeight;
		int rightHeight;
		if(currentNode.right == null) {
			rightHeight = 0;
		} else {
			rightHeight = ((AVLNode) currentNode.right).height;
		}
		if(currentNode.left == null) {
			leftHeight = 0;
		} else {
			leftHeight = ((AVLNode) currentNode.left).height;
		}
		while(Math.abs(leftHeight - rightHeight) > 1) {
			balanceChildren(currentNode);
			
		}

		
//		currentNode.height = height(currentNode);
		return currentNode.height;
	}

	/**
	 * Given a BSTNode current, performs a case 1 single rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode rotateLeftChild(BSTNode current) {
		AVLNode imbalance =  (AVLNode) current;
		AVLNode leftChild = (AVLNode) current.left;
		leftChild.right = imbalance;
		leftChild.height = 1 + Math.max(height(leftChild.right), height(leftChild.left));
		imbalance.height = 1 + Math.max(height(imbalance.right), height(imbalance.left));
		return imbalance;
	}
	
	/**
	 * Given a BSTNode current, performs a case 4 single rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode rotateRightChild(BSTNode current) {
		AVLNode imbalance =  (AVLNode) current;
		AVLNode rightChild = (AVLNode) current.right;
		rightChild.left = imbalance;
		rightChild.height = 1 + Math.max(height(rightChild.right), height(rightChild.left));
		imbalance.height = 1 + Math.max(height(imbalance.right), height(imbalance.left));
		return imbalance;
	}
	
	/**
	 * Given a BSTNode current, performs a case 2 double rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode doubleRotateLeft(BSTNode current){
		AVLNode imbalance = (AVLNode) current;
		imbalance.left = rotateRightChild(imbalance.left);
		return rotateLeftChild(imbalance);
	}
	
	/**
	 * Given a BSTNode current, performs a case 3 double rotation.
	 * Returns a balanced subtree at the node passed.
	 */
	private AVLNode doubleRotateRight(BSTNode current){
		AVLNode imbalance = (AVLNode) current;
		imbalance.right = rotateLeftChild(imbalance.right);
		return rotateRightChild(imbalance);
	}
	
	
	/**
	 * Given a BSTNode root, calculates the height of the root.
	 * Throws IllegalStateException if the height fields store
	 * incorrect values or if the tree is unbalanced. 
	 */
	private int height(BSTNode root) {
		AVLNode node = (AVLNode) root;		
		if (node == null) 
			return -1;
		int leftHeight = height((AVLNode) node.left);
		int rightHeight = height((AVLNode) node.right);
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
		height(overallRoot);
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
