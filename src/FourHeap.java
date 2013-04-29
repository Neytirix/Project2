
public class FourHeap<E> implements PriorityQueue<E>{
	Comparator<? super E> comparator; //public private?
	private E[] arrayHeap;
	int size; //public private?
	
	
	public FourHeap(Comparator<? super E> c) {
		comparator = c;
		arrayHeap = (E[]) new Object[10];
	}
	
	@Override
	public void insert(Object item) {
		
	}

	@Override
	public E findMin() {
		if (!isEmpty()) {
			//throw exception
		}
		return arrayHeap[0]; //??
	}

	@Override
	public E deleteMin() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

}
