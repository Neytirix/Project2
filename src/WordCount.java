/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/14/2013
 * Project 2
*/

import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	/**
	 * Takes a DataCounter object of type E, counter, and constructs 
	 * and returns an array of DataCount objects containing each unique word.
	 */
	public static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
		DataCount<E>[] DataCounterArray = (DataCount<E>[]) new DataCount[counter.getSize()];
		SimpleIterator<DataCount<E>> iterator = counter.getIterator();
		int i = 0;
		while(iterator.hasNext()) {
			DataCounterArray[i] = iterator.next();
			i++;
		}
		return DataCounterArray;
	}
	
	/**
	 * Given an array of command line arguments (data type, sort type, (number), 
	 * text file), prints each unique word and its corresponding count in the text 
	 * file in descending order. If "-k (number)" is given as input, only prints 
	 * the top K most frequent words. Prints error statements when an incorrect number 
	 * of arguments is given as input or they are input in an incorrect order. 
	 */
    private static void countWords(String[] argsArray) {
    	DataCounter<String> counter = null;
		String file = null;
		if (argsArray.length == 3 || argsArray.length == 4) {
			if(argsArray.length == 3) {
				 file = argsArray[2];
			} else {
				file = argsArray[3];
			}
			if(argsArray[0].equals("-b")) {
				counter = new BinarySearchTree<String>(new StringComparator());
			} else if (argsArray[0].equals("-a")) {
				counter = new AVLTree<String>(new StringComparator());
			} else if (argsArray[0].equals("-m")) {
				counter = new MoveToFrontList<String>(new StringComparator());
			} else if (argsArray[0].equals("-h")) {
				counter = new Hashtable<String>(new StringComparator(), new StringHasher()); 
			} else {
				System.out.println("incorrect argument");
				System.exit(1);
			}
    	} else {
    		System.out.println("incorrect number of arguments");
    		System.exit(1);
    	}
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        } catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }

        DataCount<String>[] counts = getCountsArray(counter);

        if (argsArray[1].equals("-k")) {
        	topKWords(argsArray[2], counts, new TopKComparator());
    	} else {
	        if (argsArray[1].equals("-is")) {
	           	insertionSort(counts, new DataCountStringComparator());
	        } else if (argsArray[1].equals("-hs")) {
	        	heapSort(counts, new DataCountStringComparator());
	        } else if (argsArray[1].equals("-os")) {
	        	quickSort(counts, 0, counts.length, new DataCountStringComparator());
	        } else {
	        	System.out.println("Incorrect arguments");
	        	System.exit(1);
	        }
	        for (DataCount<String> c : counts) {
	            System.out.println(c.count + " \t" + c.data);
	       	}
    	}
 
        
    }
    
    /**
     * Given a String number, converts that string into an int k and prints
     * the top k most frequently occurring words in the array of words passed. 
     * Uses the comparator passed to determine which words occur more frequently.
     */
    private static <E> void topKWords(String number, E[] words, Comparator<E> comparator) {
    	int k = Integer.parseInt(number);
    	PriorityQueue<E> heap = new FourHeap<E>(comparator);
    	int size = 0;
    	for(int i = 0; i < words.length; i++) {
    		heap.insert(words[i]);
    		size++;
    		if(size > k) {
    			heap.deleteMin();
    			size--;
    		}
    	}
    	DataCount<String>[] array =  new DataCount[size];
    	for(int i = array.length - 1; i >= 0; i--){
    		array[i] = (DataCount<String>) heap.deleteMin();
    	}
    	for (DataCount<String> c : array) {
            System.out.println(c.count + " \t" + c.data);
       	}
    }
    
    
    /**
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * 
     * This code uses insertion sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
     * 
     * @param counts array to be sorted.
	 * @param comparator for comparing elements.
     */
    public static <E> void insertionSort(E[] array, Comparator<E> comparator) {
        for (int i = 1; i < array.length; i++) {
            E x = array[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (comparator.compare(x,array[j]) >= 0) {
                    break;
                }
                array[j + 1] = array[j];
            }
            array[j + 1] = x;
        }
    }
    
    /**
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * 
     * This code uses heap sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
     * 
     * @param counts array to be sorted.
	 * @param comparator for comparing elements.
     */
    public static <E> void heapSort(E[] array, Comparator<E> comparator) {
    	PriorityQueue<E> heap = new FourHeap<E>(comparator);
       	for (int i = 0; i < array.length; i++) {
       		heap.insert(array[i]);
       	}
       	for(int i = 0; i < array.length; i++) {
       	//for (int i = array.length - 1; i >= 0; i--) {
       		array[i] = heap.deleteMin();
       	}
    }
    /**
     * Given a generic array, an int low, and int hi, picks a pivot in the 
     * array and partitions through the array from low to hi and modifies it.
     * All values less than the pivot will occur to the left of the pivot, 
     * all values greater than the pivot will occur to the right of the pivot.
     * Uses the comparator to determine which values are greater or less than 
     * others. Returns the index of the pivot as an int in the modified array
     * after the partitioning. 
     */    
    private static <E> int partition(E[] array, int low, int hi, Comparator<E> comparator) {
    	int left = low;
    	int right = hi-1;
    	int pivIndex = (low+hi)/2;
    	E pivot = array[pivIndex];
    	E temp = array[low];
    	array[low] = pivot;
    	array[pivIndex] = temp;  
    	while (left < right) {
    		if (comparator.compare(array[right],pivot) > 0) {
    			right--;
    		}
    		else if (comparator.compare(array[left],pivot) <= 0) {
    			left++;
    		} else {
    			E swap = array[left];
    			array[left] = array[right];
    			array[right] = swap;
    		}
    	}
    	array[low] = array[left];
    	array[left] = pivot;
    	return left;

    } 

    /**
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be ordered according to the comparator.
     * 
     * This code uses quick sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
     * 
     * @param counts array to be sorted.
	 * @param comparator for comparing elements.
     */
    public static <E> void quickSort(E[] array, int low, int hi, Comparator<E> comparator) {
    	if(hi-low > 1) {
    		int pivot = partition(array, low, hi, comparator);
	    	quickSort(array, low, pivot, comparator);
	    	quickSort(array, pivot+1, hi, comparator);
    	}
    } 
    
    
    public static void main(String[] args) {
    	long before = System.currentTimeMillis();
    	countWords(args);
    	long after = System.currentTimeMillis();
    	System.out.println(after-before);
    }
}
