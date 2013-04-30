import java.util.NoSuchElementException;

public class FourHeap<E> implements PriorityQueue<E>{
	private Comparator<? super E> comparator; 
	private E[] arrayHeap;
	private int size; 
	
	
	public FourHeap(Comparator<? super E> c) {
		comparator = c;
		arrayHeap = (E[]) new Object[10];
		size = 0;
	}
	
	@Override
	public void insert(E item) {
		if (arrayHeap.length == size){
			arrayHeap = doubleArray(arrayHeap);
		}
		size++;
		arrayHeap[size-1]= item;
		percolateUp(size-1);
	}

	@Override
	public E findMin() {
		if (!isEmpty()) {
			throw new NoSuchElementException();
		}
		return arrayHeap[0]; 
	}

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

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	private E[] doubleArray(E[] original) {
		E[] newArray = (E[])new Object[original.length * 2];
		for(int i = 0; i < original.length; i++) {
			newArray[i] = original[i];
		}
		return newArray;
	} 
	
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
