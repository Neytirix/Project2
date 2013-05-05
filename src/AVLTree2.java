
public class AVLTree2<E> extends BinarySearchTree<E> implements DataCounter<E> {

	public AVLTree2(Comparator<? super E> c) {
		super(c);
	}

	public void incCount(E data) {
		addNode(data, overallRoot);
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
			return addNode(data, currentNode.left);
		} else {
			//traverse right (current is less than value passed)
			return addNode(data, currentNode.right);
		}
		return balance(currentNode);
	}
	
	public AVLNode balance(AVLNode node) {
		if(node == null) {
			return node;
		}
		if (height(node.left) - height(node.right) > 1) {
			if(height(node.left.left) >= height(node.left.right)) {
				rotateLeftChild(node);
			} else {
				doubleRotateLeft(node);
			}			
		} else if (height(node.right) - height(node.left) > 1){
			if(height(node.right) - height(node.left) > 1) {
				rotateRightChild(node);
			} else {
				doubleRotateRight(node);
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
	private AVLNode rotateLeftChild(BSTNode current) {
		AVLNode imbalance =  (AVLNode) current;
		AVLNode leftChild = (AVLNode) current.left;
		leftChild.right = imbalance;
		imbalance.height = 1 + Math.max(height(imbalance.right), height(imbalance.left));
		leftChild.height = 1 + Math.max(height(leftChild.right), height(leftChild.left));
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
		imbalance.height = 1 + Math.max(height(imbalance.right), height(imbalance.left));
		rightChild.height = 1 + Math.max(height(rightChild.right), height(rightChild.left));
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
