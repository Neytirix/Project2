/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/1/2013
 * Project 2
*/

/**
 * 
 */

public class MoveToFrontList<E> implements DataCounter<E> {
	private Comparator<? super E> comparator; 
	private LinkedNode front;
	private int size;  //current number of elements in the list
	
	/**
	 * Given a Comparator c, constructs a MoveToFrontList object ??
	 */
	public MoveToFrontList(Comparator<? super E> c) {
		comparator = c;
		front = null;
		size = 0;
	}
	
	/**
	 * Given a key of type E, increments that key's count in the list. 
	 * Moves the passed element to the front of list after incrementing
	 * its count.
	 */
	@Override
	public void incCount(E data) {
		boolean dataExists = false;
		LinkedNode current = front;
		if(front == null) { //case 1: linked list is empty, sets front node
							//equal to value passed with a count of 1
			front = new LinkedNode(data, 1);
			size++;
			dataExists = true;
		} else if(comparator.compare(current.data, data) == 0) { //case 2: the 
											 // first node is the value passed 
			current.count += 1;	
			dataExists = true;
		} else { //case 3: searches entire list for value passed
			while(current != null && current.next != null) {
				if(comparator.compare(current.next.data, data) == 0) {
					current.next.count += 1;
					//moves node to front		
					LinkedNode temp = current.next;					
					current.next = current.next.next;
					temp.next = front;
					front = temp;
					dataExists = true;
				}
				current = current.next;
			}
			
		}
		if (!dataExists) { //case 4: element is not in list, adds it to front
			current = new LinkedNode(data, 1, front);
			current.next = front;
			front = current;
			size++;
		}
	}

	/**
	 * Returns an int of the current number of elements in the list
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * Given a key of type E, returns the corresponding int count for 
	 * that key. Returns 0 if the element is not in the list. Moves
	 * the passed element to the front of the list after accessing its count. 
	 */
	@Override
	public int getCount(E data) {
		LinkedNode current = front;
		int count = 0;
		if (comparator.compare(current.data, data) == 0) {
			count = current.count;
		} else {
			while(current.next != null) {
				if(comparator.compare(current.next.data, data) == 0) {
					count = current.next.count;
					//moves node to front		
					LinkedNode temp = current.next;					
					current.next = current.next.next; 
					temp.next = front;
					front = temp;
				}
			}
		}
		return count;
	}

	/**
	 * 
	 */
	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			public LinkedNode current = front;
			
			@Override
			public DataCount<E> next() {
				if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
				}
        		DataCount<E> dCount = new DataCount<E>(current.data, current.count);
				current = current.next;
				return dCount;
			}

			@Override
			public boolean hasNext() {
				return (current != null); 
			}
			
			
		};
	}
	
	
	/**
	 * Helper class for linked list nodes storing generic types
	 */
	private class LinkedNode{
		private E data;
		private int count;
		private LinkedNode next;
		
		/**
		 * Creates a LinkedNode object with element d of type E and an 
		 * int count
		 */
		public LinkedNode(E data, int count) {
			this.data = data;
			this.count = count;
			this.next = null;
		}
		
		/**
		 * Creates a LinkedNode object with element d of type E, an int count,
		 * and a next node
		 */
	    public LinkedNode(E data, int count, LinkedNode next) {
	        this.data = data;
	        this.count = count;
	        this.next = next;
	    }
	}
}
