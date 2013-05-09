public class Hashtable<E> implements DataCounter<E> {
	private Comparator<? super E> comparator;
	private Hasher<E> hasher;
	private LinkedNode[] array;
	private int size;
	
	
	public Hashtable(Comparator<? super E> c, Hasher<E> h) {
		comparator = c;
		hasher = h;
		array = (LinkedNode[]) new Hashtable.LinkedNode[37]; //what should starting table size be since we want 
																//prime number, is this arbitrary?
		size = 0;
	}
	
	@Override
	public void incCount(E data) {
		double loadFactor = size/array.length;
		if(loadFactor >= .5) {
			array = increaseTable(array);
		}
		int index = hasher.hash(data) % array.length;
		boolean dataExists = false;
		//LinkedNode front = array[index];
		if(array[index] == null) { 
			//case 1: linked list is empty, sets front node equal to value passed 
			//with a count of 1
			array[index] = new LinkedNode(data, 1);
			size++;
			dataExists = true;
		} else { 
			//case 2: searches entire list for value passed
			LinkedNode current = array[index];
			while(current != null && !dataExists) { 
				if(comparator.compare(current.data, data) == 0) {
					current.count += 1;
					dataExists = true;
				}
				current = current.next;
			}
		}
		if (!dataExists) { 
			//case 3: element is not in list, adds it to front
			LinkedNode current = new LinkedNode(data, 1, array[index]);
			current.next = array[index];
			array[index] = current;
			size++;
		}

	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getCount(E data) {
		int index = hasher.hash(data) % array.length;
		LinkedNode current = array[index];
		int count = 0;
		while(current != null) {
			if(comparator.compare(current.data, data) == 0) {
				count = current.count;
			}
			current = current.next;	
		}
		return count;		
	}

	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			public LinkedNode current = array[0];
			public int bucket = 0;
			public int numSeen = 0;
			
			//finds first element in hashtable
			
			@Override
			public DataCount<E> next() {
				if(!hasNext()) {
        			throw new java.util.NoSuchElementException();
				}
				DataCount<E> dCount = null;
				if (current == null) {
					//current = front in a new bucket (look through buckets to find new front);
					for(int i = bucket+1; i < array.length; i++){
						if(array[i] != null) {
							bucket = i;
							current = array[bucket];
							dCount = new DataCount<E>(current.data, current.count);
							current = current.next;
							break;
						}
					}
				} else {
					dCount = new DataCount<E>(current.data, current.count);
					current = current.next;
				}
				numSeen++;
				return dCount;
			}

			@Override
			public boolean hasNext() {
				if(numSeen == size) {
					return false;
				}
				return true;
			}
			
		};
	}
	
	
	
	
	private LinkedNode[] increaseTable(LinkedNode[] array) {
		LinkedNode[] newArray = (LinkedNode[]) new Hashtable.LinkedNode[(array.length*2)+1];
		for(int i = 0; i <array.length; i++) {
			LinkedNode front = array[i];
			while(front != null) {
				int index = hasher.hash(front.data) % newArray.length;
				LinkedNode temp = front;
				front = front.next;
				temp.next = newArray[index];
				newArray[index] = temp; 
			}
		}		
		return newArray;
		
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
	
	
	
	
	//really similar to move to front list but can't access count field when growing table 
	//(unless i call incCount that many times which is inefficient) 
	//but my code right now is basically copying move to frontList
	
	

}
