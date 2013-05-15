/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/14/2013
 * Project 2
*/

/**
 * Hashtable implements DataCounter using separate chaining.
 * The constructor takes a Comparator<? super E> "function object" 
 * so that items of type E can be compared, and a Hasher "function 
 * object" so that items of type E return a proper integer to hash.
 * Each node associates a count with an E.
 */

public class Hashtable<E> implements DataCounter<E> {
	private Comparator<? super E> comparator;
	private Hasher<E> hasher;
	private LinkedNode[] array;
	private int size;
	
	/**
	 * Given a Comparator c and a Hasher h, constructs a new Hashtable object.
	 * Takes a Comparator and a Hasher as arguments.
	 */
	public Hashtable(Comparator<? super E> c, Hasher<E> h) {
		comparator = c;
		hasher = h;
		array = (LinkedNode[]) new Hashtable.LinkedNode[37]; //arbitrary prime number
		size = 0;
	}
	
	/**
	 * Given a key of type E, increments that key's count in the hashtable.
	 * If the load factor of the hashtable is greater than .5, resizes to  
	 * about double the size. 
	 */
	@Override
	public void incCount(E data) {
		double loadFactor = size/array.length;
		if(loadFactor >= .5) {
			array = increaseTable(array);
		}
		int index = Math.abs(hasher.hash(data) % array.length);
		boolean dataExists = false;
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

	/**
	 * Returns an int of the current number of elements in the hash table
	 */
	@Override
	public int getSize() {
		return size;
	}

	
	/**
	 * Given a key of type E, returns the corresponding int count for 
	 * that key. Returns 0 if the element is not in the list.
	 */
	@Override
	public int getCount(E data) {
		int index = Math.abs(hasher.hash(data) % array.length);
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

	/**
	 * Returns a SimpleIterator of type DataCount<E>. Iterates
	 * over the elements. 
	 */
	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			public LinkedNode current = array[0];
			public int bucket = 0;
			public int numSeen = 0;
			
			/**
			 * Returns the next element in the hastable. Throws 
			 * NoSuchElementException if the hashtable does not have a next.
			 */
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

			/**
			 * Returns true if the hashtable has a next element, otherwise
			 * returns false.
			 */
			@Override
			public boolean hasNext() {
				return (numSeen != size);
			}
			
		};
	}
	
	
	
	/**
	 * Given a hashtable, creates a new hashtable of about double the size.
	 * Copies over all existing elements into their proper index using a hash 
	 * function. Returns the new hashtable (as an array of linked lists). 
	 */
	private LinkedNode[] increaseTable(LinkedNode[] array) {
		//use array.length*2 + 1 because we want our hashtable to be a prime number or 
		//at least have as few factors as possible
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

}
