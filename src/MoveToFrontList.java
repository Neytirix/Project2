
public class MoveToFrontList<E> implements DataCounter<E> {
	Comparator<? super E> comparator; //public private? 
	public LinkedNode front;
	int size; //public private?
	
	public MoveToFrontList(Comparator<? super E> c) {
		comparator = c;
		front = null;
		size = 0;
	}
	
	@Override
	public void incCount(E data) {
		boolean dataExists = true;
		LinkedNode current = front;
		if(front == null) { //case 1: linked list is empty, sets front node equal to value passed with a count of 1
			front = new LinkedNode(data, 1);
			size++;
		} else if(comparator.compare(current.data, data) == 0) { //case 2: the first node is the value passed --> increments its count by 1
			current.count += 1;	
		} else { //case 3: searches entire list for value passed
			while(current.next != null) {
				if(comparator.compare(current.next.data, data) == 0) {
					current.next.count += 1;
					//moves node to front		
					LinkedNode temp = current.next;					
					current.next = current.next.next;
					temp.next = front;
					front = temp;
				}
				current = current.next;
			}
			dataExists = false;
		}
		if (!dataExists) {
			current = new LinkedNode(data, 1, front);
			current.next = front;
			front = current;
			size++;
		}
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getCount(E data) {
		//too similar to incCount?
		//what if there is a case where linked list is empty?
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
	
	
	/*
	 * Helper class for linked list nodes storing generic types
	 */
	private class LinkedNode{
		private E data;
		private int count;
		private LinkedNode next;
		
		public LinkedNode(E data, int count) {
			this.data = data;
			this.count = count;
			this.next = null;
		}
		
		/*
		 * Creates a LinkedNode object with element d of type E and next
		 */
	    public LinkedNode(E data, int count, LinkedNode next) {
	        this.data = data;
	        this.count = count;
	        this.next = next;
	    }
	}
}
