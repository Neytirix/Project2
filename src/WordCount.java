/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/1/2013
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
	 * Given an array of command line arguments (data type, sort type, text file),
	 * prints each unique word and its corresponding count in the text file in 
	 * descending order. Prints error statements when an incorrect number of 
	 * arguments is input or they are input in an incorrect order.
	 */
    private static void countWords(String[] argsArray) {
    	DataCounter<String> counter = new BinarySearchTree<String>(new StringComparator());
		String file = argsArray[0];
		if (argsArray.length == 3) {
		    file = argsArray[2];
			if(argsArray[0].equals("-b")) {
				counter = new BinarySearchTree<String>(new StringComparator());
			} else if (argsArray[0].equals("-a")) {
				counter = new AVLTree2<String>(new StringComparator());
			} else if (argsArray[0].equals("-m")) {
				counter = new MoveToFrontList<String>(new StringComparator());
			} else {
				System.out.println("incorrect argument");
				System.exit(1);
			}
    	} else if (argsArray.length != 1) {
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
        if (argsArray.length == 1 || argsArray[1].equals("-is")) {
           	insertionSort(counts, new DataCountStringComparator());
        } else if (argsArray[1].equals("-hs")) {
        	heapSort(counts, new DataCountStringComparator());
        } else {
        	System.out.println("incorrect number of arguments");
        	System.exit(1);
        }
        for (DataCount<String> c : counts)
            System.out.println(c.count + " \t" + c.data);
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
       	for (int i = array.length - 1; i >= 0; i--) {
       		array[i] = heap.deleteMin();
       	}
    }
    
    public static void main(String[] args) {
    	countWords(args);
    }
}
