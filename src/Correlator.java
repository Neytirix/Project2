/* Name: Alexandra (Sasha) Babayan & Brian Park
 * Date: 5/14/2013
 * Project 2
*/

import java.io.IOException;

/**
 * Correlator takes two files and prints a number representing
 * the square of the Euclidean distance between the two vectors in the 
 * space of shared words in the documents.
 * This number is one measurement of the likelihood that the files were 
 * written by the same author.
 *
 */
public class Correlator {
	
	/**
	 * The main method takes an argument of a string array.
	 * The first element of the array selects a data structure
	 * to help analyze the correlation between two documents.
	 * The second and third element are the names of the files to be compared.
	 * The correlation number is printed.
	 */
	public static void main(String[] args) {
		DataCounter<String> counter1 = new BinarySearchTree<String>(new StringComparator());
		
		DataCounter<String> counter2 = new BinarySearchTree<String>(new StringComparator());
		if (args.length != 3) {
			System.out.println("Usage: [ -b | -a | -m | -h ] <filename1> <filename2>");
		}
		
		if(args[0].equals("-b")) {
			counter1 = new BinarySearchTree<String>(new StringComparator());
			counter2 = new BinarySearchTree<String>(new StringComparator());
		} else if (args[0].equals("-a")) {
			counter1 = new AVLTree<String>(new StringComparator());
			counter2 = new AVLTree<String>(new StringComparator());
		} else if (args[0].equals("-m")) {
			counter1 = new MoveToFrontList<String>(new StringComparator());
			counter2 = new MoveToFrontList<String>(new StringComparator());
		} else if (args[0].equals("-h")) {
			counter1 = new Hashtable<String>(new StringComparator(), new StringHasher());
			counter2 = new Hashtable<String>(new StringComparator(), new StringHasher());
		} else {
			System.out.println("Incorrect argument. Usage: [ -b | -a | -m | -h ] ");
			System.exit(1);
		}
		int sum1 = parseAndCount(args[1],counter1);
		int sum2 = parseAndCount(args[2],counter2);
		if(counter1.getSize() > counter2.getSize()) {
			System.out.println(compare(counter2.getIterator(),counter1,sum2, sum1));
		} else {
			System.out.println(compare(counter1.getIterator(),counter2,sum1, sum2));
		}
	}
	
	
	/** 
	 * A private method that parses each file, counting the frequency of words
	 * and keeping track of the total number of words.
	 * @param file, String of filename to be parsed
	 * @param counter, data structure to hold counts
	 * @return sum, representing the total number of words in the file
	 */
	private static int parseAndCount(String file, DataCounter<String> counter) {
		int sum = 0;
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				counter.incCount(word);
				sum++;
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
		return sum;
	}
	
	/**
	 * This method calculates the correlation in a running sum.
	 * @param iterator, SimpleIterator iterating over the data structure (the file with less distinct words)
	 * @param counter, the "other" data structure (the file with more distinct words)
	 * @param iterSum, total number of words in the file with less distinct words
	 * @param counterSum, total number of words in the "other" file
	 * @return correlation, a double.
	 */
	private static double compare(SimpleIterator<DataCount<String>> iterator, DataCounter<String> counter, int iterSum, int counterSum){
		double corr = 0;
		while(iterator.hasNext()) {
			DataCount<String>  current = iterator.next();
			double i = (double) current.count;
			double freq = i/iterSum;
			if(freq < .01 && freq > .0001) {
				double iCounter= (double) counter.getCount(current.data);
				double freqCounter = iCounter/counterSum;
				if(freqCounter < .01 && freqCounter > .0001) {
					corr += Math.pow((freq - freqCounter), 2);
				}
			}
		}
		return corr;
	}
	
}
