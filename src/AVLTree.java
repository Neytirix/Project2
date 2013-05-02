/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/1/2013
 * Project 2
*/

public class AVLTree<E> extends BinarySearchTree<E> implements DataCounter<E> {

	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	@Override
	public void incCount(E data) { 
		/*
		super.incCount(data);
		AVLNode root = (AVLNode) overallRoot;
		root.height = calculateHeight(root);
		//check for rotations?
		*/
		
		
		if (overallRoot == null) {
			overallRoot = new AVLNode(data);
		}
		((AVLNode) overallRoot).height = addNode(data, overallRoot);
		//AVLNode lastInsertedNode = find(data, overallRoot);
		 //checkBalanced(overallRoot);
		
		
	}
	
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
			//balance (case 1 or 2)
		} else {
			//traverse right (current is less than value passed)
			if (currentNode.right == null) {
				currentNode.right = new AVLNode(data);
				((AVLNode) currentNode.right).height = 0;
				return 0;
			}
			lastHeight = addNode(data, currentNode.right);
			//balance (case 3 or 4)
		}
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
		currentNode.height = lastHeight + 1;
		return lastHeight;
	}

	private AVLNode rotateLeftChild(BSTNode current) {
		AVLNode imbalance =  (AVLNode) current;
		AVLNode leftChild = (AVLNode) current.left;
		leftChild.right = imbalance;
		imbalance.height = 1 + Math.max(height(imbalance.right), height(imbalance.left));
		leftChild.height = 1 + Math.max(height(leftChild.right), height(leftChild.left));
		return imbalance;
	}
	
	
	private AVLNode rotateRightChild(BSTNode current) {
		AVLNode imbalance =  (AVLNode) current;
		AVLNode rightChild = (AVLNode) current.right;
		rightChild.left = imbalance;
		imbalance.height = 1 + Math.max(height(imbalance.right), height(imbalance.left));
		rightChild.height = 1 + Math.max(height(rightChild.right), height(rightChild.left));
		return imbalance;
	}
	
	
	private AVLNode doubleRotateLeft(BSTNode current){
		AVLNode imbalance = (AVLNode) current;
		imbalance.left = rotateRightChild(imbalance.left);
		return rotateLeftChild(imbalance);
	}
	
	private AVLNode doubleRotateRight(BSTNode current){
		AVLNode imbalance = (AVLNode) current;
		imbalance.right = rotateLeftChild(imbalance.right);
		return rotateRightChild(imbalance);
	}
	
	private int height(BSTNode root) {
		AVLNode current = (AVLNode) root;
		if (root == null) {
			return -1;
		}
		AVLNode left = (AVLNode) root.left;
		AVLNode right = (AVLNode) root.right;
		left.height = height(left);
		right.height = height(right);
		return 1 + Math.max(left.height,right.height);
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

	
	private class AVLNode extends BSTNode {

		public int height;
		
		public AVLNode(E d) {
			super(d);
			height = 0;
		}	
	}

}
