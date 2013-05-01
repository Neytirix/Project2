/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/1/2013
 * Project 2
*/

/**
 * 
 */
import java.util.NoSuchElementException;

public class FourHeap<E> implements PriorityQueue<E>{
	private Comparator<? super E> comparator; 
	private E[] arrayHeap;
	private int size; 
	
	/**
	 * Given a Comparator c, constructs a FourHeap object
	 */
	public FourHeap(Comparator<? super E> c) {
		comparator = c;
		arrayHeap = (E[]) new Object[10];
		size = 0;
	}
	
	/**
	 * Given an item of type E, adds it to the four heap in the proper location
	 */
	@Override
	public void insert(E item) {
		if (arrayHeap.length == size){
			arrayHeap = doubleArray(arrayHeap);
		}
		size++;
		arrayHeap[size-1]= item;
		percolateUp(size-1);
	}

	/**
	 * Finds and returns the minimum value in the four heap. Throws
	 * NoSuchElementException is findMin is called on an empty heap.
	 */
	@Override
	public E findMin() {
		if (!isEmpty()) {
			throw new NoSuchElementException();
		}
		return arrayHeap[0]; 
	}

	/**
	 * Deletes and returns the minimum value in the four heap, repairs the
	 * hole by replacing it with the new minimum value. Throws 
	 * NoSuchElementException if deleteMin is called on an empty heap.
	 */
	@Override
	public E deleteMin() {
		if (!isEmpty()) {
			throw new NoSuchElementException();
		}
		E min = arrayHeap[0];
		arrayHeap[0] = arrayHeap[size];
		percolateDown(0);
		size--;
		return min;
	}

	/**
	 * Returns true if the the four heap is empty, otherwise returns false.
	 */
	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * Doubles the length of the generic array passed and copies over all 
	 * existing elements into the new array, preserving the order. Returns
	 * the new generic array. 
	 */
	private E[] doubleArray(E[] original) {
		E[] newArray = (E[])new Object[original.length * 2];
		for(int i = 0; i < original.length; i++) {
			newArray[i] = original[i];
		}
		return newArray;
	} 
	
	/**
	 * Given an int index in the heap, percolates the element up into its 
	 * proper location, preserving the heap order property.
	 */
	private void percolateUp(int index) {
		boolean done = false;
		while(!done && index > 0){
			int parent = (int) Math.floor((index-1)/4);
			if(comparator.compare(arrayHeap[index], arrayHeap[parent]) < 0) {
				E temp = arrayHeap[index];
				arrayHeap[index] = arrayHeap[parent];
				arrayHeap[parent] = temp;
				index = parent;
			} else {
				done = true;
			}
		}
	}
	
	/**
	 *Given an int index in the heap, percolates the element down into its 
	 * proper location, preserving the heap order property.
	 */
	private void percolateDown(int index) {
		//children = (4*index) + 1, (4*index) +2, (4*index) +3, (4*index) +4
		boolean done = false;
		while(!done) {		
			int i = (index*4)+1;
			int min = index;
			while(i <= size-1 && i <= (index*4) + 4 ) {
				if(comparator.compare(arrayHeap[i], arrayHeap[min]) < 0){
					min = i;
				}
				i++;
			}
			if(comparator.compare(arrayHeap[index], arrayHeap[min]) > 0) {
				E minimumValue = arrayHeap[min];
				arrayHeap[min] = arrayHeap[index];
				arrayHeap[index] = minimumValue;
				index = min;
			} else {
				done = true ;
			}
		}
	}
	
}
